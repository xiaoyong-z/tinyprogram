from pymongo import MongoClient
client = MongoClient(host="127.0.0.1", port=27017)
crimes = []

collection = client["law"]["train"]
collection1 = client["law"]["extract"]
count = 0


for i in collection.find():
    for j in i['meta']['accusation']:
        temp = {}
        temp['fact'] = i['fact']
        temp['accu'] = j.replace('[', '').replace(']','')
        temp['prison'] = i['meta']['term_of_imprisonment']
        temp['punish_of_money'] = i['meta']['punish_of_money']
        temp['law'] = i['meta']['relevant_articles']
        collection1.insert(temp)