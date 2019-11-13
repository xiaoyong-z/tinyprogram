from sklearn.model_selection import train_test_split
from tensorflow.keras.layers import Input, Embedding, Bidirectional, LSTM, GlobalMaxPool1D, GlobalAveragePooling1D, concatenate, \
    Dense, Activation
from tensorflow.keras.models import Model
from tensorflow.keras.layers import Dropout
import time
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from math import log
import tensorflow as tf
import configparser
import os
import pickle
#获取config配置文件
def getConfig():
    config = configparser.ConfigParser()
    path = os.path.split(os.path.realpath(__file__))[0] + '/model.conf'
    print(path)
    config.read(path)
    return config

print('start', time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()))
print('accusation')

config = getConfig()
os.chdir(config.get("path", "project_path2"))
num_words = int(config.get("model", "num_words"))
maxlen = int(config.get("model", "maxlen"))
kernel_size = int(config.get("model", "kernel_size"))
DIM = int(config.get("model", "dimension"))
batch_size = int(config.get("model", "batch_size"))

with open("DataMining/class_2_imp.dict",'rb') as f:
    class_imp = pickle.load(f)
# fact数据集
with open(config.get("data", "train_fact"), mode="rb") as f:
    fact = pickle.load(f)

with open('Data/Train/train_impr.pkl', mode="rb") as f:
    labels = pickle.load(f)

with open('Data/Train/train_impr2.pkl', mode="rb") as f:
    labels2 = pickle.load(f)

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


fact = np.array(fact)
labels = np.array(labels).reshape(len(labels), -1)
labels2 = np.array(labels2).reshape(len(labels2), -1)
fact_train, fact_test = train_test_split(fact, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))
labels_train, labels_test = train_test_split(labels, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))
labels_train2, labels_test2 = train_test_split(labels2, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))


# fact_train, fact_test = train_test_split(fact, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))
# labels_train, labels_test = train_test_split(labels, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))

model = tf.keras.models.load_model('ModelSave/Impr/epochs_10.h5')
layer_model = Model(inputs=model.input, outputs=model.layers[3].output)
model.summary()
y = model.predict(fact_test)

print('f1:')
# 只取最高置信度的准确率
s4 = distance_score(y_pred=y, y_true=labels_test2)
print(s4)