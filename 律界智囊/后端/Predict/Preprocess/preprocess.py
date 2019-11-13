from Preprocess.DataHelperFunction import data_helper
import os
import pickle
from Conf.getconfig import getConfig
import numpy as np


def prep(fact_raw):
    config = getConfig()
    os.chdir(config.get("path", "project_path"))
    num_words = int(config.get("model", "num_words"))
    maxlen = int(config.get("model", "maxlen"))

    helper = data_helper()
    fact_cut = helper.participle(fact_raw, word_len=2)
    with open('Preprocess/tokenizer_fact_%d.pkl' % (num_words), mode='rb') as f:
        tokenizer_fact=pickle.load(f)
    fact_sequences = tokenizer_fact.texts_to_sequences(texts=fact_cut)
    fact_sequences = [i[0] for i in fact_sequences if len(i) > 0]
    fact_pad_seq = np.zeros((1, maxlen), dtype=int)
    for index, i in enumerate(fact_sequences):
        fact_pad_seq[0][index] = i
    return fact_pad_seq
