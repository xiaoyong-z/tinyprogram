from DataMining.DataHelperFunction import data_helper
import pickle
import os
from conf.getconfig import getConfig


config = getConfig()
os.chdir(config.get("path", "project_path2"))
num_words = int(config.get("model", "num_words"))
maxlen = int(config.get("model", "maxlen"))
from collections import defaultdict
a2i = defaultdict(lambda: len(a2i))
with open("Data/accusation", mode="r") as f:
    accu = f.readlines()
s = [a2i[i.replace('\n', '')] for i in accu]
i2a = {}
for i, j in a2i.items():
    i2a[j] = i
helper = data_helper()
data = helper.read_data(config.get("data", "train_raw_data"))
fact_raw = helper.extract_data(data, "fact")
print("step5 : Accu")
accusations = helper.extract_data(data, 'accusation')
accusations = [a2i[i[0]] for i in accusations]
with open("Data/fact_id", mode="wb") as f:
    pickle.dump(accusations, f)
print("step5 : Accu finished ")