from sklearn.model_selection import train_test_split

import time
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from math import log
import configparser
import os
import pickle
import tensorflow as tf
#获取config配置文件
def getConfig():
    config = configparser.ConfigParser()
    path = os.path.split(os.path.realpath(__file__))[0] + '/model.conf'
    print(path)
    config.read(path)
    return config

def intsi(a):
    return int(a+0.5)

def distance_score(y_true, y_pred):
    '''
    mission 3
    :param y_true:
    :param y_pred:
    :return:
    '''
    result = 0
    n = len(y_true)
    for i in range(n):
        if(y_pred[i][0] <= 0):
            y_pred[i][0] = 0
        if(y_pred[i][0] >= 24):
            y_pred[i][0] = 24
        if (y_true[i][0] == -2) or (class_imp[intsi(y_pred[i][0])] == -2):
            if (class_imp[intsi(y_pred[i][0])] == -2) and (y_true[i][0] == -2):
                result += 1
        elif (y_true[i][0] == -1) or (class_imp[intsi(y_pred[i][0])] == -1):
            if (class_imp[intsi(y_pred[i][0])] == -1) and (y_true[i][0] == -1):
                result += 1
        else:
            v = abs(log(y_true[i][0] + 1) - log(class_imp[intsi(y_pred[i][0])] + 1))
            if v <= 0.2:
                result += 1
            elif v <= 0.4:
                result += 0.8
            elif v <= 0.6:
                result += 0.6
            elif v <= 0.8:
                result += 0.4
            elif v <= 1.0:
                result += 0.2
            else:
                pass
    return result / n * 100

print('start', time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()))
print('accusation')

config = getConfig()
os.chdir(config.get("path", "project_path2"))
num_words = int(config.get("model", "num_words"))
maxlen = int(config.get("model", "maxlen"))
kernel_size = int(config.get("model", "kernel_size"))
DIM = int(config.get("model", "dimension"))
batch_size = 512

with open("DataMining/class_2_imp.dict",'rb') as f:
    class_imp = pickle.load(f)
# fact数据集
with open(config.get("data", "train_fact"), mode="rb") as f:
    fact = pickle.load(f)

with open('Data/Train/train_impr.pkl', mode="rb") as f:
    labels = pickle.load(f)

with open('Data/Train/train_impr2.pkl', mode="rb") as f:
    labels2 = pickle.load(f)


fact = np.array(fact)
labels = np.array(labels).reshape(len(labels), -1)
labels2 = np.array(labels2).reshape(len(labels2), -1)
fact_train, fact_test = train_test_split(fact, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))
labels_train, labels_test = train_test_split(labels, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))
labels_train2, labels_test2 = train_test_split(labels2, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))

model = tf.keras.models.load_model('ModelSave/Impr/epochs_3.h5')
model.summary()

epochs = int(config.get("model", "epochs"))

def plot_learning_curves(history):
    pd.DataFrame(history).plot(figsize=(8, 5))
    plt.grid(True)
    plt.show()

historys = {'loss':[]}
score_list2 = []
def dict_add(history, historys):
    for i in history.history.items():
        historys[i[0]].append(i[1][0])
for i in range(epochs):
    history = model.fit(x=fact_train, y=labels_train, batch_size=batch_size, epochs=1, verbose=1)
    model.save('ModelSave/Impr/' + 'epochs_%d.h5' % (i + 10))
    dict_add(history, historys)
    y = model.predict(fact_test[:])
    print('%s distance:' % i)
    # 只取最高置信度的准确率
    s = distance_score(y_true=labels_test2, y_pred=y)
    print(s)
    score_list2.append([i, s])
    print(pd.DataFrame(score_list2))
plot_learning_curves(historys)
print('end', time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()))
print('#####################\n')
