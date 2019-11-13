from pymongo import MongoClient
from collections import defaultdict
import pickle
host = "127.0.0.1"
client = MongoClient(host)
collection = client["law"]["baike"]
id2num = defaultdict(lambda : len(id2num) + 1)

for i in collection.find():
    id2num[i['articleId']]

num2id = {j:i for i, j in id2num.items()}
with open("id2num.pickle", "wb") as f:
    pickle.dump(dict(id2num), f)

with open("num2id.pickle", "wb") as f:
    pickle.dump(num2id, f)
