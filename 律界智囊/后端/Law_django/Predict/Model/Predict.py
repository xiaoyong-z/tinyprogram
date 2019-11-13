from tensorflow import keras
import numpy as np
import pickle
from sklearn.metrics.pairwise import cosine_similarity
from Predict.Conf.getconfig import getConfig
import os

config = getConfig()


with open(config.get("path", "accusation_path"), mode="r") as f:
    accu = f.readlines()
    accu = np.array([i.replace("\n", "") for i in accu])

with open(config.get("path", "articles_path"), mode="r") as f:
    articles = f.readlines()
    articles = np.array([i.replace("\n", "") for i in articles])

with open(config.get("path", "class_2_imp_path"),'rb') as f:
    class_imp = pickle.load(f)

def predict2both(predictions):
    one_hots = []
    for prediction in predictions:
        one_hot = np.where(prediction > 0.05, 1.0, 0.0)
        if one_hot.sum() == 0:
            one_hot = np.where(prediction == prediction.max(), 1.0, 0.0)
        one_hots.append(one_hot)
    return np.array(one_hots)

def softmax(x):
    return np.exp(x)/np.sum(np.exp(x),axis=0)

def intsi(a):
    return int(a+0.5)

def similarity(crime, y):
    select_facts = []
    for i in crime:
        with open(config.get("path", "fact_path") + str(i) + "_.pkl", mode="rb") as f:
            fact = np.array(pickle.load(f))
        with open(config.get("path", "simi_path") + str(i) + "_.pkl", mode="rb") as f:
            simi_matrix = pickle.load(f)
        all_matrix = np.r_[simi_matrix, y]
        cos_matrix = cosine_similarity(all_matrix)
        select = list(np.argsort(cos_matrix[-1][:-1])[-3:])
        select.reverse()
        select_fact = fact[np.array(select)]
        select_facts.append(select_fact)
    del cos_matrix
    del all_matrix
    return select_facts

def predict_accu(fact_pad):
    model = keras.models.load_model(config.get("path", "accu_model_path"))
    model2 = keras.models.Model(inputs=model.input, outputs=model.layers[-3].output)
    y = model.predict(fact_pad)
    y2 = model2.predict(fact_pad)
    del model
    del model2
    y_select = predict2both(y)
    y_prob = softmax(y[y_select == 1])
    y_select = np.array([index for index, i in enumerate(y_select[0]) if i == 1])
    simi_data = similarity(y_select, y2)
    return (accu[y_select], y_prob), simi_data

def predict_tiaoli(fact_pad):
    model = keras.models.load_model(config.get("path", "tiaoli_model_path"))
    y = model.predict(fact_pad)
    del model
    y_select = predict2both(y)
    y_prob = softmax(y[y_select == 1])
    y_select = np.array([index for index, i in enumerate(y_select[0]) if i == 1])
    return (articles[y_select], y_prob)

def predict_xingqi(fact_pad):
    model = keras.models.load_model(config.get("path", "impr_model_path"))
 #   model.summary()
    y = model.predict(fact_pad)
    y = y[0][0]
    del model
    if (y <= 0):
        y = 0
    if (y >= 24):
        y = 24
    y = class_imp[intsi(y)]
    death = False
    life_impr = False
    if y == -2:
        death = True
    if y == -1:
        life_impr = True
    return (y, death, life_impr)