from pymongo import MongoClient
from text_summary.MMR.mmr_xiaomi_test import *
import pickle
import random
from random import choice
host = "127.0.0.1"
def mongo_new_classify2():
    client = MongoClient(host)
    collection = client["law"]["new_classify2"]
    classifys = []
    for i in collection.find():
        classifys.append(i)
    client.close()
    return classifys

def mongo_classify(belong_to):
    client = MongoClient(host)
    collection = client["law"]["classify"]
    belong_tos = []
    for i in collection.find({"accu_belong_to":belong_to}):
        i['_id'] = None
        belong_tos.append(i)
    client.close()
    return belong_tos

def mongo_classify2():
    client = MongoClient(host)
    collection = client["law"]["classify"]
    belong_tos = []
    for i in collection.find():
        i['_id'] = None
        belong_tos.append(i)
    client.close()
    return belong_tos

def mongo_final(crime):
    client = MongoClient(host)
    collection = client["law"]["final"]
    belong_tos = []
    for i in collection.find({"accu_name":crime}):
        for j in i['accu_fact']:
            random_number = random.random()
            j = MMR(j)
            if(len(j["fact"]) > 50):
                j["fact2"] = j["fact"][:50]
            else:
                j["fact2"] = j["fact"]
            if(random_number > 0.5):
                j["label"] = random.sample(i["accu_law"], 2)
            else:
                j["label"] = [choice(i["accu_law"])]
        i['_id'] = None
        belong_tos.append(i)
    client.close()
    return belong_tos

def mongo_tiaoli2_classify_sum():
    client = MongoClient(host)
    collection = client["law"]["tiaoli2_classify_sum"]
    classifys = []
    for i in collection.find():
        classifys.append(i)
    client.close()
    return classifys

def mongo_tiaoli2_classify(belong_to):
    client = MongoClient(host)
    collection = client["law"]["tiaoli2_classify"]
    belong_tos = []
    for i in collection.find({"belong_to":belong_to}):
        i['_id'] = None
        belong_tos.append(i)
    client.close()
    return belong_tos

def mongo_tiaoli2_classify2():
    client = MongoClient(host)
    collection = client["law"]["tiaoli2_classify"]
    belong_tos = []
    for i in collection.find():
        i['_id'] = None
        belong_tos.append(i)
    client.close()
    return belong_tos

def mongo_tiaoli2(chinese_name):
    client = MongoClient(host)
    collection = client["law"]["tiaoli2"]
    belong_tos = []
    for i in collection.find({"chinese_name":chinese_name}):
        i['_id'] = None
        belong_tos.append(i)
    client.close()
    return belong_tos

def mongo_baike_classify():
    client = MongoClient(host)
    collection = client["law"]["baike_classify"]
    collection2 = client["law"]["baike"]
    belong_tos = []
    with open("mongo/id2num.pickle", "rb") as f:
        id2num = pickle.load(f)
    for i in collection.find().sort('publishDate',direction=-1):
        article_id = collection2.find({'_id':i['_id']})[0]['articleId']
        i['_id'] = None
        i['index'] = id2num[article_id]
        belong_tos.append(i)
    belong_tos = belong_tos
    client.close()
    return belong_tos

def mongo_baike(title):
    client = MongoClient(host)
    collection = client["law"]["baike"]
    belong_tos = []
    with open("mongo/id2num.pickle", "rb") as f:
        id2num = pickle.load(f)
    for i in collection.find({"articleTitle":title}):
        i['_id'] = None
        i['index'] = id2num[i['articleId']]
        belong_tos.append(i)
    client.close()
    return belong_tos

def mongo_login(username, password):
    client = MongoClient(host)
    collection = client["law"]["user"]
    belong_tos = []
    for i in collection.find({"userName": username}):
        belong_tos.append(i)
    result = {}
    if(len(belong_tos) == 0):
        result['status'] = 2
        result['msg'] = "用户或密码错误"
        result['result'] = None
    elif(belong_tos[0]['userPsw'] != password):
        result['status'] = 2
        result['msg'] = "用户或密码错误"
        result['result'] = None
    else:
        result['status'] = 1
        result['msg'] = "登录成功"
        result['result'] = {}
        result['result']['userName'] = belong_tos[0]['userName']
        result['result']['history'] = belong_tos[0]['history']
    client.close()
    return result

def mongo_register(username, password):
    client = MongoClient(host)
    collection = client["law"]["user"]
    belong_tos = []
    for i in collection.find({"userName": username}):
        belong_tos.append(i)
    register_item = {}
    if(len(belong_tos) == 0):
        register_item['status'] = 1
        register_item['msg'] = "注册成功"
        register_item['result'] = {}
        register_item['result']['userName'] = username
        register_item['result']['history'] = []
        register_insert = {}
        register_insert['userName'] = username
        register_insert['userPsw'] = password
        register_insert['history'] = []
        collection.insert_one(register_insert)
    else:
        register_item['status'] = 2
        register_item['msg'] = "用户已存在"
        register_item['result'] = None

    client.close()
    return register_item

def mongo_delete_history(username):
    client = MongoClient(host)
    collection = client["law"]["user"]
    result = {}
    update_history = {"history": []}
    collection.update({"userName": username}, {"$set": update_history})
    result['status'] = 1
    result['msg'] = '删除成功'
    result['result'] = {}
    result['result']['userName'] = username
    result['result']['history'] = []
    client.close()
    return result

def mongo_add_history(username, history):
    client = MongoClient(host)
    collection = client["law"]["user"]
    collection2 = client["law"]["baike"]
    user = list(collection.find({"userName": username}))[0]
    with open("mongo/num2id.pickle", "rb") as f:
        num2id = pickle.load(f)
    history_id = num2id[history]
    history = collection2.find_one({"articleId":history_id})
    history["_id"] = None
    user['history'].append(history)
    new_history = user['history']
    collection.update({"userName": username}, {"$set":{"history": new_history}})
    result = {}
    result['status'] = 1
    result['msg'] = '添加成功'
    result['result'] = {}
    result['result']['userName'] = username
    result['result']['history'] = new_history
    client.close()
    return result

def mongo_xingfa(xingfa):
    client = MongoClient(host)
    collection = client["law"]["xingfa"]
    result = []
    for i in collection.find({"id":int(xingfa)}):
        i['_id'] = None
        result.append(i)
    client.close()
    return result


def mongo_anlifind(xingfa):
    client = MongoClient(host)
    collection = client["law"]["classify"]
    result = []
    for j in xingfa:
        for i in collection.find():
            if(i.__contains__("accu_name")):
                if(i["accu_name"].__contains__(j)):
                    result.append(i["accu_name"][:-1])
    collection2 = client["law"]["anli"]
    anli_set = []
    for i in collection2.find():
        accus = i["meta"]["accusation"]
        if(len(accus) <= len(xingfa)):
            for j in accus:
                if j in result:
                    if (len(i["fact"]) > 50):
                        i["fact2"] = i["fact"][:50]
                    else:
                        i["fact2"] = i["fact"]
                    i["_id"] = None
                    anli_set.append(i)
    anli_set = sorted(anli_set, key=lambda k: len(k['meta']['accusation']), reverse=True)
    client.close()
    return anli_set[:30]

def mongo_anlilist():
    client = MongoClient(host)
    collection2 = client["law"]["anli"]
    anli_set = []
    for i in collection2.find():
        if (len(i["fact"]) > 50):
            i["fact2"] = i["fact"][:50]
        else:
            i["fact2"] = i["fact"]
        i["_id"] = None
        anli_set.append(i)
    anli_set = random.sample(anli_set, 30)
    client.close()
    return anli_set