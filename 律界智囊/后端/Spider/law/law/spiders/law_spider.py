# -*- coding: utf-8 -*-
import scrapy
from lxml import html
from copy import deepcopy

class LawSpiderSpider(scrapy.Spider):
    name = 'law_spider'
    allowed_domains = ['eywedu.com']
    start_urls = ['http://ds.eywedu.com/law/index.htm']

    def cut(self, w):
        w = w.strip().replace(u'\u3000', u'').replace(u'\xa0', u'').\
            replace(u'\ufeff', u'').replace('\r', '').replace('\n','').replace(' ', '')
        return w

    def parse(self, response):
        law_belong_to = html.fromstring(response.body).xpath("//p[@align='center']//a[@target='_self']//text()")
        law_belong_to = law_belong_to[2:7]
        law_url = html.fromstring(response.body).xpath("//p[@align='center']//a[@target='_self']/@href")
        law_url = law_url[1: 6]
        for law_category, url in zip(law_belong_to, law_url):
            item = {}
            item["law_category"] = law_category.strip()
            yield scrapy.Request(
                url=url,
                callback=self.parse_law_category,
                meta={'item': deepcopy(item)}
            )

    def parse_law_category(self, response):
        item = deepcopy(response.meta["item"])
        next = response.xpath("//p[@align='left']//a//text()").extract()[-2]
        if(next == "下一页"):
            laws = response.xpath("//p[@align='left']//a//text()").extract()[:-3]
            law_urls = response.xpath("//p[@align='left']//a//@href").extract()[:-3]
            law_urls = ['/'.join(response._get_url().split('/')[:-1]) + '/' + i for i in law_urls]
        else:
            laws = response.xpath("//p[@align='left']//a//text()").extract()[:-2]
            law_urls = response.xpath("//p[@align='left']//a//@href").extract()[:-2]
            law_urls = ['/'.join(response._get_url().split('/')[:-1]) + '/' + i for i in law_urls]

        for law, law_url in zip(laws, law_urls):
            item["law"] = law.strip()
            yield scrapy.Request(
                url=law_url,
                callback=self.parse_law,
                meta={'item':deepcopy(item)}
            )

        if(next == "下一页"):
            law_url = response.xpath("//p[@align='left']//a//@href").extract()[-2]
            law_url = '/'.join(response._get_url().split('/')[:-1]) + '/' + law_url
            yield scrapy.Request(
                url=law_url,
                callback=self.parse_law_category,
                meta={'item': deepcopy(response.meta["item"])}
            )

    def parse_law(self, response):
        item = response.meta["item"]
        contents = html.fromstring(response.body).xpath("//span[@id='content']//text()")
        contents = [self.cut(i) for i in contents]
        item["content"] = contents
        yield item