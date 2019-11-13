from pymongo import MongoClient
client = MongoClient(host="127.0.0.1", port=27017)
crimes = []

collection = client["law"]["retrive"]
count = 0
for i in collection.find():
    #删去没有概念的那六组
    if(i['accu_gainian'] == None):
        collection.remove(i)