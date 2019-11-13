from pymongo import MongoClient
from collections import defaultdict
client = MongoClient(host="127.0.0.1", port=27017)
crimes = []


collection = client["law"]["extract"]
collection1 = client["law"]["result"]
dict_count = {}
dict_save = {}
dict_law = {}
dict_law_count = defaultdict(int)
for i in collection.find():
    if not dict_count.__contains__(i['accu']):
        dict_count[i['accu']] = 0
        dict_save[i['accu']] = [{"fact":i['fact'],"prison":i['prison'],"punish_of_money":i['punish_of_money']}]
        dict_law[i['accu']] = set(i['law'])
    elif dict_count[i['accu']] < 5:
        dict_save[i['accu']].append({"fact":i['fact'],"prison":i['prison'],"punish_of_money":i['punish_of_money']})
        dict_count[i['accu']] += 1
        for h in i['law']:
            dict_law[i['accu']].add(h)
    else:
        for h in i['law']:
            dict_law[i['accu']].add(h)
    for h in i['law']:
        dict_law_count[i['accu'] +'^' + str(h)] += 1

for j in dict_save.keys():
    dict = {}
    dict['accusation'] = j
    dict['accu_fact'] = dict_save[j]
    dict_law_choose = {}
    for m in dict_law_count.keys():
        if(m.split('^')[0] == j):
            dict_law_choose[m.split('^')[1]] = dict_law_count[m]
    laws = []
    for k, m in sorted(dict_law_choose.items(), key=lambda d: d[1], reverse=True)[:3]:
        laws.append(k)
    dict['accu_law'] = laws
    collection1.insert(dict)


