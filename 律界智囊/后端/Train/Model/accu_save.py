import pickle
with open("../anliku2", mode="rb") as f:
    select = pickle.load(f)

from pymongo import MongoClient
host = "127.0.0.1"
client = MongoClient(host)
collection = client["law"]["anli"]
count = 0
for i in select:
    i["id"] = count
    count += 1
    collection.insert_one(i)