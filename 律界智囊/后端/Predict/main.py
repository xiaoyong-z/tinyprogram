import json
from Preprocess.preprocess import prep
from Model.Predict import predict_accu, predict_tiaoli, predict_xingqi

if __name__ == "__main__":
    with open("input.json") as f:
        fact = f.readlines()
        fact = json.loads(fact[0])
    fact_pad = prep(fact['fact'])
    accu = predict_accu(fact_pad)
    tiaoli = predict_tiaoli(fact_pad)
    impr = predict_xingqi(fact_pad)
    result = {}
    result['impr'] = impr
    result['tiaoli'] = list(tiaoli[0])
    result['tiaoli_prob'] = list(tiaoli[1])
    result['accu'] = list(accu[0][0])
    result['accu_prob'] = list(accu[0][1])
    result['accu_rele'] = list(accu[1])
    result['accu_rele'] = [list(i) for i in result['accu_rele']]
    print(result)