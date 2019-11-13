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

with open(config.get("data", "train_accu"), mode="rb") as f:
    labels = pickle.load(f)



fact = np.array(fact)[:100]
labels = np.array(labels).reshape(len(labels), -1)[:100]
fact_train, fact_test = train_test_split(fact, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))
labels_train, labels_test = train_test_split(labels, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))


# fact_train, fact_test = train_test_split(fact, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))
# labels_train, labels_test = train_test_split(labels, test_size=float(config.get("model", "test_size")), random_state=int(config.get("model", "random_state")))

model = tf.keras.models.load_model('ModelSave/Accu/epochs_7.h5')
layer_model = Model(inputs=model.input, outputs=model.layers[-3].output)
model.summary()
y = layer_model.predict(fact_test)

with open('Data/Test/simi_embedding.pkl', mode='wb') as f:
    pickle.dump(y, f)