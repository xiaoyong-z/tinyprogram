import codecs
import json
import re

import jieba
import numpy as np
import os
from conf.getconfig import getConfig

config = getConfig()
stopwords_path = config.get("data", "stopwords")

class data_helper():
    def __init__(self):
        pass

    def read_data(self, path):
        with open(path, encoding="utf-8") as f:
            data = []
            for line in f.readlines():
                data.append(json.loads(line))
        return data

    def extract_data(self, data, select):
        if select == "fact":
            result = list(map(lambda x: x['fact'], data))
        elif select in ['accusation', 'relevant_articles']:
            result = list(map(lambda x: x['meta'][select], data))
        elif select == 'imprisonment':
            result = []
            for i in data:
                if i['meta']['term_of_imprisonment']['death_penalty']:
                    result.append(-2)
                elif i['meta']['term_of_imprisonment']['life_imprisonment']:
                    result.append(-1)
                else:
                    result.append(i['meta']['term_of_imprisonment']['imprisonment'])
        return result

    def participle(self, data, word_len = 1):
        stopwords = []
        stopwords_line = codecs.open(stopwords_path, 'r', 'utf-8').readlines()
        for stop in stopwords_line:
            stopwords.append(stop.replace('\r', '').replace('\n', ''))
        facts_list = []
        for count, fact_str in enumerate(data):
            if(count % 1000 == 0):
                print("step1: finish --- " + str(count) + " sentences")
            fact_list = []
            fact_str = re.sub(r'\r', '', fact_str)
            fact_str = re.sub(r'\t', '', fact_str)
            fact_str = re.sub(r'\n', '', fact_str)
            fact_str = re.sub(r'([0-9]{4}年)?[0-9]{1,2}月([0-9]{1,2}日)?', '', fact_str)
            fact_str = re.sub(r'[0-9]{1,2}时([0-9]{1,2}分)?许?', '', fact_str)

            cut_list = jieba.cut(fact_str)

            for w in cut_list:
                if w in stopwords:
                    continue
                elif '省' in w:
                    continue
                elif '市' in w:
                    continue
                elif '镇' in w:
                    continue
                elif '村' in w:
                    continue
                elif '路' in w:
                    continue
                elif '县' in w:
                    continue
                elif '区' in w:
                    continue
                elif '城' in w:
                    continue
                elif '府' in w:
                    continue
                elif '庄' in w:
                    continue
                elif '道' in w:
                    continue
                elif '车' in w:
                    continue
                elif '店' in w:
                    continue
                elif '某' in w:
                    continue
                elif '辆' in w:
                    continue
                elif '房' in w:
                    continue
                elif '馆' in w:
                    continue
                elif '场' in w:
                    continue
                elif '街' in w:
                    continue
                elif '墙' in w:
                    continue
                elif '牌' in w:
                    continue
                elif len(w) < word_len:
                    continue
                elif "检察院" in w:
                    continue
                elif "公诉" in w:
                    continue
                elif "机关" in w:
                    continue
                elif "指控" in w:
                    continue
                elif "查明" in w:
                    continue
                elif "起诉" in w:
                    continue
                elif "审理" in w:
                    continue
                else:
                    fact_list.append(w)
            facts_list.append(fact_list)
        return facts_list

    def create_labels(self, data, name):
        if name in ["accusation", "relevant_articles"]:
            if not (os.path.exists("Data/%s" % (name))):
                label_set = self.create_label_set(data, name)
            else:
                with open(("Data/%s" % (name)), mode="r", encoding="utf-8") as f:
                    label_set = []
                    for i in f.readlines():
                        label_set.append(i[:-1])
        else:
            label_set = [400, 500] + list(range(0, 25 * 12 + 1))

        label_dict = {}
        for i in range(len(label_set)):
            label_dict[str(label_set[i])] = i
        self.label_dict = label_dict
        self.length = len(label_dict)
        one_hot_data = np.zeros([len(data), self.length])
        for index, crimes in enumerate(data):
            for i in crimes:
                one_hot_data[index][self.label_dict[str(i)]] = 1
        return one_hot_data


    def create_label_set(self, data, name):
        set_data = list(map(set, data))
        set_label = set()
        for i in set_data:
            set_label |= i
        with open("Data/%s" % (name), mode="w", encoding="utf-8") as f:
            for i in set_label:
                f.write(str(i) + "\n")
        return list(set_label)
