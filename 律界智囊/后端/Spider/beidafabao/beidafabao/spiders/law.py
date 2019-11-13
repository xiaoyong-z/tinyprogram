# -*- coding: utf-8 -*-
import time

import scrapy
from selenium import webdriver
from scrapy.http import HtmlResponse

class LawSpider(scrapy.Spider):
    name = 'law'
    allowed_domains = ['pkulaw.cn']

    def __init__(self):
        path = "/Users/tinysean/Downloads/chromedriver"  # 注意这个路径需要时可执行路径（chmod 777 dir or 755 dir）
        self.browser = webdriver.Chrome(executable_path=path)
        self.browser.set_page_load_timeout(30)

    def closed(self,spider):
        print("spider closed")
        self.browser.close()

    def start_requests(self):
        start_urls = ['http://www.pkulaw.cn/cluster_call_form.aspx']
        yield scrapy.Request(url=start_urls[0],
                             callback=self.parse)

    def parse(self, response):
        self.browser.get(response.url)
        self.browser.execute_script("do_Cluster('0/XA01/XA0101',1)")
        count = 1
        url_set = set()
        while True:
            sel_list = self.browser.find_elements_by_xpath("//td[@class='main-top4']//tbody//td[@align='left']/a")
            url_list = [sel.get_attribute("href") for sel in sel_list]
            url_set |= set(url_list)
            time.sleep(6)
            self.browser.execute_script("show_hide('pic_wait',true); Need_Cluster_after_Search=false;do_Search(" + str(count) + ",24,'chl')")
            count = count + 1
            if (count > 24):
                break

        with open('url_set.txt', mode='w') as f:
            f.write(repr(url_set))
        print(len(url_set))
