import numpy as np
import pickle, params
from sif_help_function import *
## run
wordfile = 'sgns.wiki.word'

weightfile = 'THUOCL_law.txt'
weightpara = 1e-3 #-1,1e-1,1e-2,1e-3,1e-4]
rmpc = 1 # 0,1,2

with open("num2content2.pickle", mode="rb") as f:
    num2content = pickle.load(f)


def sim_evaluate_one(We, words, weight4ind, scoring_function, rmpc):
    num2embedding = {}
    for index, value in num2content.items():
        if(len(value) > 0):
            embedding = sim_getEmbedding(We, words, value, weight4ind, scoring_function, rmpc)
            if(embedding.shape[0] < 10):
                print(str(index) + " : " + str(embedding.shape[0]))
            num2embedding[index] = embedding
        else:
            num2embedding[index] = np.zeros((1, 300))
    return num2embedding

# word2weight = getWordWeight(weightfile, weightpara)
# (words, We) = getWordmap(wordfile)
# weight4ind = getWeight(words, word2weight)

# with open("word2weight", "wb") as f:
#     pickle.dump(word2weight, f)
#
# with open("words", "wb") as f:
#     pickle.dump(words, f)
#
# with open("We", "wb") as f:
#     pickle.dump(We, f)
#
# with open("weight4ind", "wb") as f:
#     pickle.dump(weight4ind, f)

with open("weight4ind", "rb") as f:
    weight4ind = pickle.load(f)

with open("words", "rb") as f:
    words = pickle.load(f)

with open("We", "rb") as f:
    We = pickle.load(f)
print('word vectors loaded from %s' % wordfile)
print('word weights computed from %s using parameter a=%f' % (weightfile, weightpara))
print('remove the first %d principal components' % rmpc)
num2embedding = sim_evaluate_one(We, words, weight4ind, weighted_average_sim_rmpc, rmpc)

with open("num2embedding", "wb") as f:
    pickle.dump(num2embedding, f)
