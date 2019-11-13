from DataMining.DataHelperFunction import data_helper
import pickle
import os
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences

helper = data_helper()
data = helper.read_data("../../Data/Raw/data_test.json")
fact_raw = helper.extract_data(data, "fact")

#Warn:这里可能导致内存溢出，如果溢出，则要分批次进行分词处理
fact_cut  = helper.participle(fact_raw, word_len=2)#删除长度为1的词，中间可能包括语气词

num_words = 80000
maxlen = 400
tokenizer_fact = Tokenizer(num_words=num_words)
fact_length = len(fact_cut)
#分批次索引化
n = 0
while n < fact_length:
    tokenizer_fact.fit_on_texts(texts=fact_cut[n:n+10000])
    n += 10000


fact_sequences = tokenizer_fact.texts_to_sequences(texts=fact_cut)

fact_length = len(fact_sequences)
n = 0
fact_pad_seq = []
#分批次进行pad_sequences
while n < fact_length:
    fact_pad_seq += list(pad_sequences(fact_sequences[n:n + 10000], maxlen=maxlen,
                                           padding='post', value=0, dtype='int'))
    n += 10000
with open("../../Data/Train/predict_pad_seq.pkl", mode="wb") as f:
    pickle.dump(fact_pad_seq, f)