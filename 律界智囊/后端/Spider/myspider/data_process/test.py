import pymongo
host = "3.16.128.130"
client = pymongo.MongoClient(host)
client.law.authenticate("user1", "user1password", mechanism='SCRAM-SHA-1')
db = client["law"]
collection = db["final"]
for i in collection.find({"accu_belong_to":"渎职罪"}):
    print(i["accu_name"])