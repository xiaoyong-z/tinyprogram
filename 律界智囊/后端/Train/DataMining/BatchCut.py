from DataMining.DataHelperFunction import data_helper
import pickle
import os
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences
from conf.getconfig import getConfig

config = getConfig()
os.chdir(config.get("path", "project_path1"))

# helper = data_helper()
# data = helper.read_data(config.get("data", "train_raw_data"))
# print(len(data))
# for i in range(18):
#     texts=helper.extract_data(data, "fact")[i*100000:(i*100000 + 100000)]
#     big_fact_cut = helper.participle(texts, word_len=2)
#     with open('Data/Cache/big_fact_cut_%d_%d.pkl' % (i*100000, i*100000 + 100000), mode='wb') as f:
#         pickle.dump(big_fact_cut, f)
#     print('finish big_fact_cut_%d_%d' % (i*100000, i*100000 + 100000))

# train tokenizer
tokenizer_fact = Tokenizer(num_words=80000)
for i in range(18):
    print('start big_fact_cut_%d_%d' % (i * 100000, i * 100000 + 100000))
    with open('Data/Cache/big_fact_cut_%d_%d.pkl' % (i * 100000, i * 100000 + 100000), mode='rb') as f:
        big_fact_cut = pickle.load(f)
    texts_cut_len = len(big_fact_cut)
    n = 0
    # 分批训练
    while n < texts_cut_len:
        tokenizer_fact.fit_on_texts(texts=big_fact_cut[n:n + 10000])
        n += 10000
        if n < texts_cut_len:
            print('tokenizer finish fit %d samples' % n)
        else:
            print('tokenizer finish fit %d samples' % texts_cut_len)
    print('finish big_fact_cut_%d_%d' % (i * 100000, i * 100000 + 100000))

with open('Data/Cache/tokenizer_fact_%d.pkl' % (80000), mode='wb') as f:
    pickle.dump(tokenizer_fact, f)