from tensorflow.keras.models import Model
import numpy as np
import tensorflow as tf
import configparser
import os
import pickle
from DataMining.DataHelperFunction import data_helper
#获取config配置文件
def getConfig():
    config = configparser.ConfigParser()
    path = os.path.split(os.path.realpath(__file__))[0] + '/model.conf'
    print(path)
    config.read(path)
    return config

config = getConfig()
os.chdir(config.get("path", "project_path2"))
from collections import defaultdict
a2i = defaultdict(lambda: len(a2i))
with open("Data/accusation", mode="r", encoding="utf-8") as f:
    accu = f.readlines()
s = [a2i[i.replace('\n', '')] for i in accu]
i2a = {}
for i, j in a2i.items():
    i2a[j] = i
helper = data_helper()
data = helper.read_data(config.get("data", "train_raw_data"))
fact_raw = helper.extract_data(data, "fact")
accusations = helper.extract_data(data, 'accusation')
impr = helper.extract_data(data, "imprisonment")
accusations = [[a2i[j] for j in i] for i in accusations]
i_map = {}
i_count = {}
sum = 0
for i in range(202):
    i_map[i] = []
    i_count[i] = 0
for index, i in enumerate(accusations):
    for j in i:
        if(i_count[j] < 2000):
            i_map[j].append(index)
            i_count[j] += 1

for i in i_count.items():
    print(i)

# fact数据集
with open(config.get("data", "train_fact"), mode="rb") as f:
    fact = pickle.load(f)
fact = np.array(fact)
model = tf.keras.models.load_model('ModelSave/Accu/epochs_7.h5')
layer_model = Model(inputs=model.input, outputs=model.layers[-3].output)
model.summary()

for i in i_map.items():
    fact_batch = layer_model.predict(fact[i[1]])
    data_batch = []
    for j in i[1]:
        data_batch.append(data[j])
    with open('Data/Test/simi_' + str(i[0]) + '_.pkl', mode='wb') as f:
        pickle.dump(fact_batch, f)
    with open('Data/Test/fact_' + str(i[0]) + '_.pkl', mode='wb') as f:
        pickle.dump(data_batch, f)
