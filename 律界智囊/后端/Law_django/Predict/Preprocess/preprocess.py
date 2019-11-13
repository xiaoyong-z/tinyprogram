from Predict.Preprocess.DataHelperFunction import data_helper
import os
import pickle
from Predict.Conf.getconfig import getConfig
import numpy as np


def prep(fact_raw):
    config = getConfig()
    maxlen = int(config.get("model", "maxlen"))

    helper = data_helper()
    fact_cut = helper.participle(fact_raw, word_len=2)
    with open(config.get("path", "tokenizer_path"), mode='rb') as f:
        tokenizer_fact=pickle.load(f)
    fact_sequences = tokenizer_fact.texts_to_sequences(texts=fact_cut)
    fact_sequences = [i[0] for i in fact_sequences if len(i) > 0]
    fact_pad_seq = np.zeros((1, maxlen), dtype=int)
    for index, i in enumerate(fact_sequences):
        fact_pad_seq[0][index] = i
    return fact_pad_seq
