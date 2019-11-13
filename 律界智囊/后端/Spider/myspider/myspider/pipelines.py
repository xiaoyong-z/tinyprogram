# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

from pymongo import MongoClient
class MyspiderPipeline(object):
    def open_spider(self, spider):
        self.client = MongoClient(host="127.0.0.1", port=27017)
        self.collection = self.client["law"]["data"]

    def close_spider(self, spider):
        self.client.close()

    def process_item(self, item, spider):
        item = dict(item)
        self.collection.insert_one(item)
