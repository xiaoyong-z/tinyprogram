from DataMining.DataHelperFunction import data_helper
import pickle
import os
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from conf.getconfig import getConfig

config = getConfig()
os.chdir(config.get("path", "project_path1"))
num_words = int(config.get("model", "num_words"))
maxlen = int(config.get("model", "maxlen"))

helper = data_helper()
data = helper.read_data(config.get("data", "train_raw_data"))
fact_raw = helper.extract_data(data, "fact")
print("step5 : Accu")
accusations = helper.extract_data(data, 'accusation')
accusations_one_hot = helper.create_labels(accusations, name='accusation')
with open(config.get("data", "train_accu"), mode="wb") as f:
    pickle.dump(accusations_one_hot, f)
print(accusations_one_hot[:10])
print("step5 : Accu finished ")

print("step6 : articles")
relevant_articles = helper.extract_data(data, 'relevant_articles')
relevant_articles_one_hot = helper.create_labels(relevant_articles, name='relevant_articles')
with open(config.get("data", "train_article"), mode="wb") as f:
    pickle.dump(relevant_articles_one_hot, f)
print(relevant_articles_one_hot[:10])
print("step6 : articles finished ")


print("step7 : imprisonment")
imprisonment = helper.extract_data(data, 'imprisonment')
imprisonment_one_hot = helper.create_labels(imprisonment, name='imprisonment')
with open(config.get("data", "train_impr"), mode="wb") as f:
    pickle.dump(imprisonment_one_hot, f)
print(imprisonment_one_hot[:10])
print("step7 : imprisonment finished")
