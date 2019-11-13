import pickle
import jieba
from jieba import analyse
# 引入TextRank关键词抽取接口
textrank = analyse.textrank



stopwords = ["被告人", "被害人", "本院", "中华人民共和国", "认为", "机关", "相关", "上述事实"]
with open("../anliku", mode="rb") as f:
    a = pickle.load(f)

for i in a:
    fact = i["fact"]
    keywords = textrank(fact)
    for i in stopwords:
        if i in keywords:
            keywords.remove(i)
    print(keywords)