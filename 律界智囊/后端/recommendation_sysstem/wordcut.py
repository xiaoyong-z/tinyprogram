import jieba                                           #导入jieba模块
import pickle
import codecs
jieba.load_userdict("THUOCL_law.txt")                     #加载自定义词典
import jieba.posseg as pseg

stopwords = []
stopwords_line = codecs.open('stopwords.txt', 'r', 'utf-8').readlines()
for stop in stopwords_line:
    stopwords.append(stop.replace('\r', '').replace('\n', ''))

with open("num2content.pickle", mode="rb") as f:
    num2content = pickle.load(f)

for id, contents in num2content.items():
    new_contents = []
    for content in contents[0]:
        new_content = []
        cut_list = jieba.cut(content)
        for i in cut_list:
            if(len(i) <= 1):
                continue
            else:
                new_content.append(i)
        if('本文' in new_content and '作者' in new_content):
            continue
        if(len(new_content) >= 5):
            new_contents.append(new_content)
    num2content[id] = new_contents

with open("num2content2.pickle", mode="wb") as f:
    pickle.dump(num2content, f)
