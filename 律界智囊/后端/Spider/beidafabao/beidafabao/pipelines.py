# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html


from pymongo import MongoClient
class LawPipeline(object):
    def open_spider(self, spider):
        host = "3.16.128.130"
        self.client = MongoClient(host)
        self.client.law.authenticate("user1", "user1password", mechanism='SCRAM-SHA-1')
        self.collection = self.client["law"]["tiaoli2"]

    def close_spider(self, spider):
        self.client.close()

    def process_item(self, item, spider):
        item = dict(item)
        self.collection.insert_one(item)
# #!/bin/bash
# cd /home/tinysean/Law_django && /usr/bin/python3 /home/tinysean/Law_django/manage.py runserver 0.0.0.0:8000
