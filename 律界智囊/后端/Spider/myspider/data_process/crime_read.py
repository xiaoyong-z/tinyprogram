from pymongo import MongoClient
client = MongoClient(host="127.0.0.1", port=27017)
crimes = []

with open("./crime.txt", mode="r", encoding="utf-8") as f:
    reads = f.readlines()
    for i in reads:
        crimes.append(i.replace("\n", ""))


collection = client["law"]["data"]
collection1 = client["law"]["retrive"]
for i in crimes:
    h1 = collection.find_one({"accu_name":i+"罪"})
    if h1 is not None:
        collection1.insert_one(dict(h1))
