from DataMining.DataHelperFunction import data_helper
import pickle
import os
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from conf.getconfig import getConfig
import numpy as np
config = getConfig()
os.chdir(config.get("path", "project_path1"))

helper = data_helper()
data = helper.read_data(config.get("data", "train_raw_data"))
import pickle
with open("DataMining/imp_class.dict",'rb') as f:
    imp_class=pickle.load(f)

def imp2class(x, imp_dict):
    for key in imp_dict.keys():
        s = key[1:-1].split(",")
        start = int(s[0])
        end = int(s[1])
        # print(start,end,imp_dict[key])
        if x > start - 1 and x < end + 1:
            return int(imp_dict[key])

def transform(imprisonment):
    impr = np.zeros(len(imprisonment))
    for index, i in enumerate(imprisonment):
        impr[index] = imp2class(i, imp_class)
    return impr

print("step7 : imprisonment")
imprisonment = helper.extract_data(data, 'imprisonment')
imprisonment_one_hot = transform(imprisonment)
with open(config.get("data", "train_impr"), mode="wb") as f:
    pickle.dump(imprisonment_one_hot, f)
with open(config.get("data", "train_impr2"), mode="wb") as f:
    pickle.dump(imprisonment, f)
print(imprisonment_one_hot[:10])
print("step7 : imprisonment finished")