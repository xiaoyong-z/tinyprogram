# -*- coding: utf-8 -*-
import scrapy


class LawParseSpider(scrapy.Spider):
    name = 'law_parse'
    allowed_domains = ['pkulaw.cn']
    start_urls = ['http://pkulaw.cn/']

    def parse(self, response):
        pass
