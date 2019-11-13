from sklearn.model_selection import train_test_split
from sklearn.metrics import f1_score
from tensorflow.python.keras.utils.vis_utils import model_to_dot

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

with open(config.get("data", "train_accu"), mode="rb") as f:
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
count = 0
indexes = set()
count_index = [0] * 202
for (index, label_count) in enumerate(np.sum(labels, axis=1)):
    if(label_count > 1):
        count += 1
        indexes.add(index)
    else:
        label_index = [i for i, x in enumerate(labels[index]) if x == 1][0]
        if(count_index[label_index] < 100):
            indexes.add(index)
            count_index[label_index] += 1

with open("accu_index.txt", mode="wb") as f:
    pickle.dump(indexes, f)
