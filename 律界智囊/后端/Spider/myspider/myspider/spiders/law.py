# -*- coding: utf-8 -*-
import scrapy
from myspider.items import MyspiderItem

class LawSpider(scrapy.Spider):
    name = 'law'
    allowed_domains = ['66law.cn']
    start_urls = ['http://www.66law.cn/zuiming/']

    def cut(self, w):
        w = w.strip().replace(u'\u3000', u'').replace(u'\xa0', u'').\
            replace(u'\ufeff', u'').replace('\r', '').replace('\n','').replace(' ', '')
        return w

    def parse(self, response):
        url_base = "http://www.66law.cn/zuiming/"
        #24, 533
        for i in range(500, 533):
            item = MyspiderItem()
            url_main = url_base + str(i) + ".aspx"
            item['accu_url'] = url_base + str(i)
            yield scrapy.Request(
                url=url_main,
                callback=self.parse_main,
                meta = {'item' : item}
            )


    def parse_main(self, response):
        item = response.meta['item']
        accu_name = response.xpath("//div[@class ='nav-f24 clearfix']/h2").extract()[0]
        item['accu_name'] = accu_name[4:-5]
        accu_belong_to = response.xpath("//div[@class='u-weizhi mt20']//a[@target='_blank']/text()").extract()
        if len(accu_belong_to) == 3:
            item['accu_belong_to'] = accu_belong_to[2]
        else:
            item['accu_belong_to'] = None
        url_gainian = item['accu_url'] + "_gainian.aspx"
        yield scrapy.Request(
            url=url_gainian,
            callback=self.parse_gainian,
            meta={'item':item}
        )

    def parse_gainian(self, response):
        item = response.meta['item']
        accu_gainian = response.xpath("//div[@class='zm-detail mt30']/div//text()").extract()
        if(len(accu_gainian) >= 1):
            accu_gainian = [self.cut(i) for i in accu_gainian]
            accu_gainian = [i for i in accu_gainian if i != '']
            item['accu_gainian'] = accu_gainian
        else:
            item['accu_gainian'] = None
        url_goucheng = item['accu_url'] + '_goucheng.aspx'
        yield scrapy.Request(
            url=url_goucheng,
            callback=self.parse_goucheng,
            meta={'item': item}
        )

    def parse_goucheng(self, response):
        item = response.meta['item']
        accu_goucheng = response.xpath("//div[@class='zm-detail mt30']/div/p//text()").extract()
        if len(accu_goucheng) >= 1:
            accu_goucheng = [self.cut(i) for i in accu_goucheng]
            accu_goucheng = [i for i in accu_goucheng if i != '']
            item['accu_goucheng'] = accu_goucheng
        else:
            item['accu_goucheng'] = None

        url_rending = item['accu_url'] + '_rending.aspx'
        yield scrapy.Request(
            url=url_rending,
            callback=self.parse_rending,
            meta={'item': item}
        )

    def parse_rending(self, response):
        item = response.meta['item']
        accu_rending = response.xpath("//div[@class='zm-detail mt30']/div/p//text()").extract()
        if len(accu_rending) >= 1:
            accu_rending = [self.cut(i) for i in accu_rending]
            accu_rending = [i for i in accu_rending if i != '']
            item['accu_rending'] = accu_rending
        else:
            item['accu_rending'] = None

        url_liangxing = item['accu_url'] + '_liangxing.aspx'
        yield scrapy.Request(
            url=url_liangxing,
            callback=self.parse_liangxing,
            meta={'item': item}
        )

    def parse_liangxing(self, response):
        item = response.meta['item']
        accu_liangxing = response.xpath("//div[@class='zm-detail mt30']/div/p//text()").extract()
        if len(accu_liangxing) >= 1:
            accu_liangxing = [self.cut(i) for i in accu_liangxing]
            accu_liangxing = [i for i in accu_liangxing if i != '']
            item['accu_liangxing'] = accu_liangxing
        else:
            item['accu_liangxing'] = None

        url_lian= item['accu_url'] + '_lian.aspx'
        yield scrapy.Request(
            url=url_lian,
            callback=self.parse_lian,
            meta={'item': item}
        )

    def parse_lian(self, response):
        item = response.meta['item']
        accu_lian = response.xpath("//div[@class='zm-detail mt30']/div/p//text()").extract()
        if len(accu_lian) >= 1:
            accu_lian = [self.cut(i) for i in accu_lian]
            accu_lian = [i for i in accu_lian if i != '']
            item['accu_lian'] = accu_lian
        else:
            item['accu_lian'] = None

        url_jieshi= item['accu_url'] + '_jieshi.aspx'
        yield scrapy.Request(
            url=url_jieshi,
            callback=self.parse_jieshi,
            meta={'item': item}
        )

    def parse_jieshi(self, response):
        item = response.meta['item']
        accu_jieshi = response.xpath("//div[@class='zm-detail mt30']/div/p//text()").extract()
        if len(accu_jieshi) >= 1:
            accu_jieshi = [self.cut(i) for i in accu_jieshi]
            accu_jieshi = [i for i in accu_jieshi if i != '']
            item['accu_jieshi'] = accu_jieshi
        else:
            item['accu_jieshi'] = None
        yield item