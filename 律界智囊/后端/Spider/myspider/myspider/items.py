# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class MyspiderItem(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    accu_name = scrapy.Field()
    accu_belong_to = scrapy.Field()
    accu_url = scrapy.Field()
    accu_gainian = scrapy.Field()
    accu_goucheng = scrapy.Field()
    accu_rending = scrapy.Field()
    accu_liangxing = scrapy.Field()
    accu_lian = scrapy.Field()
    accu_jieshi = scrapy.Field()
