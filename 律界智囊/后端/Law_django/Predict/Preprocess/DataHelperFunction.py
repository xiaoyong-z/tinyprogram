import codecs
import re
import jieba
from Predict.Conf.getconfig import getConfig

config = getConfig()
stopwords_path = config.get("path", "stopwords_path")


class data_helper():
    def __init__(self):
        pass

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
        fact_str = data
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
        return fact_list
