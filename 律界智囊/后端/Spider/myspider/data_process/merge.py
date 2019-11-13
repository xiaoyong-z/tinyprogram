from pymongo import MongoClient
client = MongoClient(host="127.0.0.1", port=27017)
crimes = []


collection = client["law"]["result"]
collection1 = client["law"]["retrive"]
collection2 = client["law"]["final"]
dict_save = {}
count = 0
for i in collection1.find():
    h = collection.find_one({"accusation":i['accu_name'][:-1]})
    i['accu_fact'] = h['accu_fact']
    i['accu_law'] = h['accu_law']
    collection2.insert(i)




