from sklearn.model_selection import train_test_split
from sklearn.metrics import f1_score

import os
import tensorflow as tf
import keras
import time
import pickle
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

import configparser
import os

#获取config配置文件
def getConfig():
    config = configparser.ConfigParser()
    path = os.path.split(os.path.realpath(__file__))[0] + '/model.conf'
    print(path)
    config.read(path)
    return config
tf.executing_eagerly()
print('start', time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()))
print('accusation')

config = getConfig()
os.chdir(config.get("path", "project_path2"))
num_words = int(config.get("model", "num_words"))
maxlen = int(config.get("model", "maxlen"))
kernel_size = int(config.get("model", "kernel_size"))
DIM = int(config.get("model", "dimension"))
batch_size = int(config.get("model", "batch_size"))

# fact数据集
with open(config.get("data", "train_fact"), mode="rb") as f:
    fact = pickle.load(f)

with open('Data/Train/train_article.pkl', mode="rb") as f:
    labels = pickle.load(f)


def predict2half(predictions):
    return np.where(predictions > 0.5, 1.0, 0.0)


def predict2top(predictions):
    one_hots = []
    for prediction in predictions:
        one_hot = np.where(prediction == prediction.max(), 1.0, 0.0)
        one_hots.append(one_hot)
    return np.array(one_hots)


def predict2both(predictions):
    one_hots = []
    for prediction in predictions:
        one_hot = np.where(prediction > 0.1, 1.0, 0.0)
        if one_hot.sum() == 0:
            one_hot = np.where(prediction == prediction.max(), 1.0, 0.0)
        one_hots.append(one_hot)
    return np.array(one_hots)


def f1_avg(y_pred, y_true):
    '''
    mission 1&2
    :param y_pred:
    :param y_true:
    :return:
    '''
    f1_micro = f1_score(y_pred=y_pred, y_true=y_true, pos_label=1, average='micro')
    f1_macro = f1_score(y_pred=y_pred, y_true=y_true, pos_label=1, average='macro')
    return (f1_micro + f1_macro) / 2

fact = np.array(fact)
labels = np.array(labels)
fact_train, fact_test = train_test_split(fact, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))
labels_train, labels_test = train_test_split(labels, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))


del fact
del labels

model = keras.models.Sequential()
model.add(keras.layers.Embedding(input_dim=num_words + 1,
                     input_length=maxlen,
                     output_dim=DIM,
                     mask_zero=0,
                     name='Embedding'))
model.add(keras.layers.Conv1D(filters=int(config.get("model", "filters")),
                    kernel_size=[kernel_size],
                    strides=int(config.get("model", "strides")),
                    padding='same',
                    activation='relu'))
model.add(keras.layers.GlobalMaxPool1D())
model.add(keras.layers.BatchNormalization())
model.add(keras.layers.Dense(int(config.get("model", "hidden")), activation="relu"))
model.add(keras.layers.Dropout(float(config.get("model", "dropout"))))
model.add(keras.layers.Dense(labels_train.shape[1], activation="sigmoid"))
model.compile(loss='categorical_crossentropy',
              optimizer='adam')
model.summary()
# print(model_to_dot(model, show_shapes=True))

epochs = int(config.get("model", "epochs"))
score_list1 = []
score_list2 = []

def plot_learning_curves(history):
    pd.DataFrame(history).plot(figsize=(8, 5))
    plt.grid(True)
    plt.show()

historys = {'loss':[]}
def dict_add(history, historys):
    for i in history.history.items():
        historys[i[0]].append(i[1][0])
for i in range(epochs):
    history = model.fit(x=fact_train, y=labels_train, batch_size=batch_size, epochs=1, verbose=2)
    model.save('ModelSave/Tiaoli/' + 'epochs_%d.h5' % i)
    dict_add(history, historys)
    y = model.predict(fact_test[:])
    y1 = predict2top(y)
    y2 = predict2half(y)
    y3 = predict2both(y)

    print('%s Accu:' % i)
    # 只取最高置信度的准确率
    s1 = [(labels_test[i] == y1[i]).min() for i in range(len(y1))]
    print(sum(s1) / len(s1))
    # 只取置信度大于0.5的准确率
    s2 = [(labels_test[i] == y2[i]).min() for i in range(len(y1))]
    print(sum(s2) / len(s2))
    # 结合前两个
    s3 = [(labels_test[i] == y3[i]).min() for i in range(len(y1))]
    print(sum(s3) / len(s3))

    print('%s f1:' % i)
    # 只取最高置信度的准确率
    s4 = f1_avg(y_pred=y1, y_true=labels_test)
    print(s4)
    # 只取置信度大于0.5的准确率
    s5 = f1_avg(y_pred=y2, y_true=labels_test)
    print(s5)
    # 结合前两个
    s6 = f1_avg(y_pred=y3, y_true=labels_test)
    print(s6)

    score_list1.append([i,
                        sum(s1) / len(s1),
                        sum(s2) / len(s2),
                        sum(s3) / len(s3)])
    score_list2.append([i, s4, s5, s6])
    print(pd.DataFrame(score_list1))
    print(pd.DataFrame(score_list2))
plot_learning_curves(historys)
print('end', time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()))
print('#####################\n')