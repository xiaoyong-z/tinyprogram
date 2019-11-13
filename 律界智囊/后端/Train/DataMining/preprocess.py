from DataMining.DataHelperFunction import data_helper
import pickle
import os
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from conf.getconfig import getConfig

config = getConfig()
os.chdir(config.get("path", "project_path2"))
num_words = int(config.get("model", "num_words"))
maxlen = int(config.get("model", "maxlen"))

helper = data_helper()
data = helper.read_data(config.get("data", "train_raw_data"))
fact_raw = helper.extract_data(data, "fact")

print("step1 : fact_cut")
#Warn:这里可能导致内存溢出，如果溢出，则要分批次进行分词处理
fact_cut = helper.participle(fact_raw, word_len=2)
#删除长度为1的词，中间可能包括语气词
print("step1 : fact_cut finished")

fact_length = len(fact_cut)

with open('Data/Cache/tokenizer_fact_%d.pkl' % (num_words), mode='rb') as f:
    tokenizer_fact=pickle.load(f)

print("step3 : tokenizer fit")
fact_sequences = tokenizer_fact.texts_to_sequences(texts=fact_cut)
print("step3 : tokenizer fit finished")

print("step4 : tokenizer pad")
n = 0
fact_pad_seq = []
#分批次进行pad_sequences
while n < fact_length:
    fact_pad_seq += list(pad_sequences(fact_sequences[n:n + 10000], maxlen=maxlen, padding='post', value=0, dtype='int'))
    n += 10000
with open(config.get("data", "train_fact"), mode="wb") as f:
        pickle.dump(fact_pad_seq, f)
print(fact_pad_seq[:10])
print("step4 : tokenizer pad finished")


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
