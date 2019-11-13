from pymongo import MongoClient
host = "127.0.0.1"
client = MongoClient(host)
collection1 = client["law"]["user"]
user_dict = {}
user_dict["userName"] = "panhongyang"
user_dict["userPsw"] = "123"
user_dict["history"] = []
collection1.insert_one(user_dict)