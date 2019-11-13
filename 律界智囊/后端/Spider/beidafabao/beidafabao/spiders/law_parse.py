# -*- coding: utf-8 -*-
import time

import scrapy
from selenium import webdriver
from lxml import html

class LawParseSpider(scrapy.Spider):
    name = 'law_parse'
    allowed_domains = ['pkulaw.cn']
    start_urls = ['http://pkulaw.cn/']

    # def __init__(self):
    #     path = "/Users/tinysean/Downloads/chromedriver"  # 注意这个路径需要时可执行路径（chmod 777 dir or 755 dir）
    #     self.browser = webdriver.Chrome(executable_path=path)
    #     self.browser.set_page_load_timeout(30)
    #
    # def closed(self,spider):
    #     print("spider closed")
    #     self.browser.close()


    def parse(self, response):
        with open("url_set.txt", mode="r") as f:
            urls = f.read()
        urls = urls.split("', '")
        urls[0] = urls[0].replace("{'", '')
        urls[-1] = urls[-1].replace("'}", '')

        for i in urls:
            # self.browser.get(i)
            # sel_list = self.browser.find_elements_by_xpath("//td[@class='main-top4']//tbody//td[@align='left']/a")
            # time.sleep(3)
            cookies = {}
            with open("cookie.txt", "r") as fp:
                for line in fp.read().split(";"):
                    name, value = line.strip().split("=", 1)
                    cookies[name] = value

            yield scrapy.Request(
                url=i,
                cookies = cookies,
                callback=self.law_parse
            )

    def wenzi_deal(self, text):
        atext = text.replace(" ", "").replace("\r", "").replace("\n", "").replace("\t", "").replace("，","")
        atext = atext.replace("【", "").replace("】","").replace("法宝引证码","")
        return atext

    def wenzi_deal2(self, w):
        w = w.strip().replace(u'\u3000', u'').replace(u'\xa0', u''). \
            replace(u'\ufeff', u'').replace('\r', '').replace('\n', '').replace(' ', '')
        return w

    def law_parse(self, response):
        body = html.fromstring(response.body)
        chinese_name = None
        english_name = None
        department = None
        store_issue = None
        start_date = None
        exec_date = None
        effective = None
        level = None
        belong_to = None
        name = body.xpath('//td[@align="center" and @colspan="2" and @style="width:100%;"]//text()')
        if(len(name) == 2):
            chinese_name = name[0]
            english_name = name[1]
        else:
            chinese_name = name[0]
        text = body.xpath("//table[@id='tbl_content_main']//text()")
        text = [self.wenzi_deal(i) for i in text]
        text = [i for i in text if(len(i) > 0)]
        for index, i in enumerate(text):
            if(i == "发布部门"):
                department = text[index+1]
            if(i == "发文字号"):
                store_issue = text[index+1]
            if(i == "发布日期"):
                start_date = text[index+1]
            if(i == "实施日期"):
                exec_date = text[index+1]
            if(i == "时效性"):
                effective = text[index+1]
            if(i == "效力级别"):
                level = text[index+1]
            if(i == "法规类别"):
                belong_to = text[index+1]
        ite = 1
        flag = 0
        while True:
            a = body.xpath('//*[@id="div_content"]/p[' + str(ite) + ']//text()')
            print(a)
            ite += 1
            try:
                if("第一章" in a[0]):
                    break
            except:
                flag = 1
                break
        chapter = 1
        articles = {}
        tiaoli_count = 0
        if(flag == 0):
            # capter_len = len(body.xpath('//*[@id="div_content"]/p//text()'))
            after = body.xpath('//*[@id="div_content"]/p['+ str(ite-1) +']/following-sibling::text()')
            n_count = 1
            for index, i in enumerate(after):
                i = str(i)
                if(n_count == 2):
                    # articles['第' + str(chapter) + '章'] = {}
                    chapter += 1
                    n_count = 0
                if(i == '\n'):
                    n_count += 1
                elif(i.startswith('\u3000')):
                    tiaoli_count += 1
                    articles['第' + str(tiaoli_count) + '条'] = self.wenzi_deal2(i)
                    n_count = 0
                elif(i.startswith(' ')):
                    tiaoli_count += 1
                    articles['第' + str(tiaoli_count) + '条'] = self.wenzi_deal2(i)
                    n_count = 0
                else:
                    if('第' + str(tiaoli_count) + '条' in articles.keys()):
                        articles['第' + str(tiaoli_count) + '条'] += self.wenzi_deal2(i)
                    else:
                        tiaoli_count += 1
                        articles['第' + str(tiaoli_count) + '条'] = self.wenzi_deal2(i)
                    n_count = 0

            item = {}
            item["chinese_name"] = chinese_name
            item["english_name"] = english_name
            item["department"] = department
            item["store_issue"] = store_issue
            item["start_date"] = start_date
            item["exec_date"] = exec_date
            item["effective"] = effective
            item["level"] = level
            item["belong_to"] = belong_to
            item["article"] = articles
            yield item
