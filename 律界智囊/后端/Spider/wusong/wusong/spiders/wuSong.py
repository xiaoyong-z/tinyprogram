# -*- coding: utf-8 -*-
from copy import deepcopy

import scrapy
import time
import scrapy
from selenium import webdriver
from scrapy.http import HtmlResponse
from lxml import html
import json

class WusongSpider(scrapy.Spider):
    name = 'wuSong'
    allowed_domains = ['victory.itslaw.com']
    start_urls = ['https://www.itslaw.com/search/articles?searchMode=articles&sortType=2&conditions=label%2B727%2B4%2B%E5%AE%9E%E5%8A%A1']

    # def __init__(self):
    #     path = "/Users/tinysean/Downloads/chromedriver"  # 注意这个路径需要时可执行路径（chmod 777 dir or 755 dir）
    #     self.browser = webdriver.Chrome(executable_path=path)
    #     self.browser.set_page_load_timeout(30)
    #
    # def closed(self,spider):
    #     print("spider closed")
    #     self.browser.close()


    def parse(self, response):
        cookies = {}
        with open("cookie.txt", "r") as fp:
            for line in fp.read().split(";"):
                name, value = line.strip().split("=", 1)
                cookies[name] = value
        for i in range(0, 100):
            url = "https://www.itslaw.com/api/v1/articles?startIndex="+str(i*20)+"&countPerPage=20&sortType=2&conditions=label%2B727%2B4%2B%E5%AE%9E%E5%8A%A1"
            yield scrapy.Request(
                url=url,
                cookies=cookies,
                callback=self.entrance_parse,
                dont_filter=True
            )

        # self.browser.get(response.url)
        #
        # count = 0
        # while(count < 2):
        #     button = self.browser.find_element_by_xpath('//span[@class="glyphicon glyphicon-chevron-down"]')
        #     button.click()
        #     count += 1
        #     time.sleep(3)
        #
        # print("ok")
        # count = 1
        # url_set = set()
        # while True:
        #     sel_list = self.browser.find_elements_by_xpath("//td[@class='main-top4']//tbody//td[@align='left']/a")
        #     url_list = [sel.get_attribute("href") for sel in sel_list]
        #     url_set |= set(url_list)
        #     time.sleep(6)
        #     self.browser.execute_script("show_hide('pic_wait',true); Need_Cluster_after_Search=false;do_Search(" + str(count) + ",24,'chl')")
        #     count = count + 1
        #     if (count > 24):
        #         break

        # with open('url_set.txt', mode='w') as f:
        #     f.write(repr(url_set))
        # print(len(url_set))

    def entrance_parse(self, response):
        json_data = json.loads(response.text)
        articles = json_data['data']['articlesSearchResult']['articles']
        item = {}
        for i in articles:
            item['articleId'] = i['articleId']
            item['articleSourceName'] = i['articleSourceName']
            item['articleTitle'] = i['articleTitle']
            item['articleUrl'] = i['articleUrl']
            item['contentLabels'] = i['contentLabels']
            item['hitResult'] = i['hitResult']
            item['imageUrl'] = i['imageUrl']
            item['publishDate'] = i['publishDate']

            url = "https://victory.itslaw.com/victory/api/v1/articles/article/" + item['articleId']
            yield scrapy.Request(
                url=url,
                callback=self.article_parse,
                meta={'item': deepcopy(item)}
            )

    def cut(self, w):
        w = w.strip().replace(u'\u3000', u'').replace(u'\xa0', u'').\
            replace(u'\ufeff', u'').replace('\r', '').replace('\n','').replace(' ', '')
        return w

    def my_remove(self, w):
        if(w.startswith("（") and w.endswith("）")):
            return False
        return True

    def article_parse(self, response):
        item = response.meta["item"]
        text = response.xpath("//div[@class='content-view']//p[2]/following-sibling::p//text()").extract()
        text = [self.cut(i) for i in text]
        text = [i for i in text if len(i) > 0]
        text = [i for i in text if self.my_remove(i)]
        item["content"] = text
        print(item["articleTitle"])
        yield item