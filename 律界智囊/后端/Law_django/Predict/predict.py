import json
from Predict.Preprocess.preprocess import prep
from Predict.Model.Predict import predict_accu, predict_tiaoli, predict_xingqi
from text_summary.MMR.mmr_xiaomi_test import *

def predict_entrance(fact):
    fact_pad = prep(fact)
    accu = predict_accu(fact_pad)
    tiaoli = predict_tiaoli(fact_pad)
    impr = predict_xingqi(fact_pad)
    result = {}
    result['impr'] = impr
    result['tiaoli'] = list(tiaoli[0])
    result['tiaoli_prob'] = list(tiaoli[1])
    result['tiaoli_prob'] = [float(i) for i in result['tiaoli_prob']]
    result['accu'] = list(accu[0][0])
    result['accu_prob'] = list(accu[0][1])
    result['accu_prob'] = [float(i) for i in result['accu_prob']]
    result['accu_rele'] = list(accu[1])
    for j in range(len(result['accu'])):
        for i in result['accu_rele'][j]:
            i = MMR(i)
    result['accu_rele'] = [list(i) for i in result['accu_rele']]
    return result

# predict_entrance("2016年元月份，被告人刘1某在“58同城”网站上发布出售二手车的虚假广告信息并留下联系电话185××××1998骗取钱")
