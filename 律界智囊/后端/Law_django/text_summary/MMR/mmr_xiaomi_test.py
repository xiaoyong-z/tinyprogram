import os
import re
import jieba
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import operator
import pickle
# from jieba import analyse
# print(os.getcwd())
f = open('text_summary/MMR/stopwords.txt')  # 停止词
stopwords = f.readlines()
stopwords = [i.replace("\n", "") for i in stopwords]
# textrank = analyse.textrank

def cleanData(name):
    setlast = jieba.cut(name, cut_all=False)
    seg_list = [i.lower() for i in setlast if i not in stopwords]
    return " ".join(seg_list)


def calculateSimilarity(sentence, doc):  # 根据句子和句子，句子和文档的余弦相似度
    if doc == []:
        return 0
    vocab = {}
    for word in sentence.split():
        vocab[word] = 0  # 生成所在句子的单词字典，值为0

    docInOneSentence = ''
    for t in doc:
        docInOneSentence += (t + ' ')  # 所有剩余句子合并
        for word in t.split():
            vocab[word] = 0  # 所有剩余句子的单词字典，值为0
    cv = CountVectorizer(vocabulary=vocab.keys())

    docVector = cv.fit_transform([docInOneSentence])
    sentenceVector = cv.fit_transform([sentence])
    return cosine_similarity(docVector, sentenceVector)[0][0]


# 引入TextRank关键词抽取接口


# with open("../../Train/anliku", mode="rb") as f:
#     a = pickle.load(f)

# anliku = []
# count = 0
# for fact in a:
def MMR(fact):
    texts = fact["fact"].split("。")
    #print(texts)
    #texts = [i[:-1] if i[-1] == '\n' else i for i in texts]
    # texts = [i[:-1] for i in texts if i[-1] == '\n' ]
    # print(texts)
    sentences = []
    clean = []
    originalSentenceOf = {}

    import time

    start = time.time()

    # Data cleansing
    for line in texts:
        parts = line.split('，')  # 句子拆分
        # print(parts)
        for part in parts:
            # print("0")
            # print(part)
            cl = cleanData(part)  # 句子切分以及去掉停止词
            # print("1")
            # print (cl)
            sentences.append(part)  # 原本的句子
            clean.append(cl)  # 干净有重复的句子
            originalSentenceOf[cl] = part  # 字典格式
    # print(sentences)
    # print(clean)

    setClean = set(clean)  # 干净无重复的句子
    # print(setClean)

    # calculate Similarity score each sentence with whole documents
    scores = {}
    for data in clean:
        temp_doc = setClean - set([data])  # 在除了当前句子的剩余所有句子
        score = calculateSimilarity(data, list(temp_doc))  # 计算当前句子与剩余所有句子的相似度
        scores[data] = score  # 得到相似度的列表
    # print score


    # calculate MMR
    n = 5 * len(sentences) / 100  # 摘要的比例大小
    # print(len(sentences))
    # print(n)
    alpha = 0.5
    summarySet = []
    while n > 0:
        mmr = {}
        # kurangkan dengan set summary
        for sentence in scores.keys():
            if not sentence in summarySet:
                mmr[sentence] = alpha * scores[sentence] - (1 - alpha) * calculateSimilarity(sentence, summarySet)  # 公式
        selected = max(mmr.items(), key=operator.itemgetter(1))[0]
        summarySet.append(selected)
        #	print (summarySet)
        n -= 1

    # rint str(time.time() - start)

    # print('Summary:')
    for (index, sentence) in enumerate(summarySet):
        a = originalSentenceOf[sentence].lstrip(' ').lstrip('\r').lstrip('\n').lstrip(r'\w+、')
        if(len(a) < 10 and (index != len(summarySet) - 1)):
            continue
        else:
            if(len(a) < 10 or "指控" in a):
                a = texts[0]
                c = re.split(r'[，：]', a)
                if(len(c) == 1):
                    a = c
                else:
                    a = c[1:]
                b = []
                for i in a:
                    if "年" in i and len(a) > 1:
                        continue
                    else:
                        b.append(i)
                a = ",".join(b)
                a = a.strip(' ').strip('\r').strip('\n').strip(r'\w+、')
                # a = a.replace("被害人", "").replace("被告人", "").replace("被告", "")

                fact["summary"] = a
            else:
                # a = a.replace("被害人", "").replace("被告人", "").replace("被告", "")
                # a = re.split(r'[，：]', a)
                # b = []
                # for i in a:
                #     if "年" in i:
                #         continue
                #     else:
                #         b.append(i)
                # a = ",".join(b)
                a = a.strip(' ').strip('\r').strip('\n').strip(r'\w+、')
                fact["summary"] = a
                # print(a)
            break
    return fact
    # count += 1
    #
    # if count % 50 == 0:
    #     print("\n")
    #     print(count)
    #     print("\n")
    # anliku.append(fact)

# with open("../../Train/anliku2", mode="wb") as f:
#     pickle.dump(anliku, f)

