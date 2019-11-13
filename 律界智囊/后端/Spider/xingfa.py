with open("xingfa2.txt", mode="r") as f:
    a = f.readlines()
k = {}
count = 276
for i in a:
    if not (i.__contains__("qiehang")):
        count = count + 1
        a = i.split("wq3000")
        title = count
        content = a[1]
        k[title] = content
    else:
        k[title] = k[title] + i[7:]

from pymongo import MongoClient
host = "127.0.0.1"
client = MongoClient(host)
collection = client["law"]["xingfa"]
for i in k.items():
    a = {}
    a["id"] = i[0]
    a["content"] = i[1]
    collection.insert_one(a)