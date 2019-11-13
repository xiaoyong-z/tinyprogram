from pymongo import MongoClient
from collections import defaultdict
import pickle
host = "127.0.0.1"
client = MongoClient(host)
collection = client["law"]["baike"]
id2content = {}

for i in collection.find():
    id2content[i['articleId']] = [i['content']]

with open("id2num.pickle", "rb") as f:
    id2num = pickle.load(f)

num2content = {}
for i in id2num.keys():
    num2content[id2num[i]] = id2content[i]

with open("num2content.pickle", "wb") as f:
    pickle.dump(num2content, f)

