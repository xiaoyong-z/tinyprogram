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

with open('Data/Cache/tokenizer_fact_%d.pkl' % (num_words), mode='rb') as f:
    tokenizer_fact=pickle.load(f)

# texts_to_sequences
for i in range(18):
    print('start big_fact_cut_%d_%d' % (i * 100000, i * 100000 + 100000))
    with open('Data/Cache/big_fact_cut_%d_%d.pkl' % (i * 100000, i * 100000 + 100000), mode='rb') as f:
        big_fact_cut = pickle.load(f)
    # 分批执行 texts_to_sequences
    big_fact_seq = tokenizer_fact.texts_to_sequences(texts=big_fact_cut)
    with open('Data/Cache/fact_seq_%d_%d.pkl' % (i * 100000, i * 100000 + 100000), mode='wb') as f:
        pickle.dump(big_fact_seq, f)
    print('finish big_fact_cut_%d_%d' % (i * 100000, i * 100000 + 100000))

# pad_sequences
for i in range(18):
    print('start big_fact_cut_%d_%d' % (i * 100000, i * 100000 + 100000))
    with open('Data/Cache/fact_seq_%d_%d.pkl' % (i * 100000, i * 100000 + 100000), mode='rb') as f:
        big_fact_seq = pickle.load(f)
    texts_cut_len = len(big_fact_seq)
    n = 0
    fact_pad_seq = []
    # 分批执行pad_sequences
    while n < texts_cut_len:
        fact_pad_seq += list(pad_sequences(big_fact_seq[n:n + 20000], maxlen=maxlen,
                                           padding='post', value=0, dtype='int'))
        n += 20000
        if n < texts_cut_len:
            print('finish pad_sequences %d samples' % n)
        else:
            print('finish pad_sequences %d samples' % texts_cut_len)
    with open('Data/Cache/fact_pad_seq_%d_%d_%d.pkl' % (maxlen, i * 100000, i * 100000 + 100000),
              mode='wb') as f:
        pickle.dump(fact_pad_seq, f)

fact_pad_seq = []
for i in range(18):
    print('start big_fact_cut_%d_%d' % (i * 100000, i * 100000 + 100000))
    with open('Data/Cache/fact_pad_seq_%d_%d_%d.pkl' % (maxlen, i * 100000, i * 100000 + 100000), mode='rb') as f:
        fact_pad_seq += pickle.load(f)
with open(config.get("data", "train_fact"), mode="wb") as f:
    pickle.dump(fact_pad_seq, f)
print(fact_pad_seq[:10])
print(len(fact_pad_seq))
print(len(fact_pad_seq[0]))