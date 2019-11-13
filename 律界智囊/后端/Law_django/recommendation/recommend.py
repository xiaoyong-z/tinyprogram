import pickle
import random

from pymongo import MongoClient
from collections import defaultdict
import numpy as np

def recommend_article(num):
    host = "127.0.0.1"
    with open("recommendation/arg_sort_top5", "rb") as f:
        arg_sort_top5 = pickle.load(f)

    with open("recommendation/num2id.pickle", "rb") as f:
        num2id = pickle.load(f)

    # with open("similar_matrix", "rb") as f:
    #     similar_matrix = pickle.load(f)
    client = MongoClient(host)
    collection = client["law"]["baike"]
    id2num = defaultdict(lambda: len(id2num) + 1)
    articles = []
    if(num > 0):
        nums = arg_sort_top5[num]
        ids = []
        nums = list(reversed(nums[:]))
        for i in nums:
            ids.append(num2id[i])
        for index, id in enumerate(ids):
            article = dict(collection.find_one({'articleId': id}))
            article['_id'] = None
            article['index'] = int(nums[index])
            articles.append(article)
    elif(num == 0):
        num_list = range(1, 2000)
        nums = random.sample(num_list, 5)
        ids = []
        for i in nums:
            ids.append(num2id[i])
        for index, id in enumerate(ids):
            article = dict(collection.find_one({'articleId': id}))
            article['_id'] = None
            article['index'] = int(nums[index])
            articles.append(article)
    client.close()
    return articles


