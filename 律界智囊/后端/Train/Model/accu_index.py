import pickle
import os
import json
from Train.conf.getconfig import getConfig
with open("../accu_index.txt", mode="rb") as f:
    select = pickle.load(f)



config = getConfig()
os.chdir(config.get("path", "project_path2"))
num_words = int(config.get("model", "num_words"))
maxlen = int(config.get("model", "maxlen"))


def read_data(path):
    with open(path, encoding="utf-8") as f:
        data = []
        for line in f.readlines():
            data.append(json.loads(line))
    return data


data = read_data(config.get("data", "train_raw_data"))
final_data = []
for i in select:
    temp = data[i]
    temp["count"] = len(temp['meta']['accusation'])
    final_data.append(data[i])
with open("anliku", mode="wb") as f:
    pickle.dump(final_data, f)


