import pickle
import numpy as np

with open("num2embedding", "rb") as f:
    num2content = pickle.load(f)

def calc_score(i, j, indexi, indexj):
    vector_a = np.mat(i)
    vector_b = np.mat(j)
    num = float(vector_a * vector_b.T)
    denom = np.linalg.norm(vector_a) * np.linalg.norm(vector_b)
    cos = num / denom
    sim = 0.5 + 0.5 * cos
    return sim

def calc_correlation(article1, article2):
    a = num2content[article1]
    b = num2content[article2]
    score_list = []
    for indexi, i in enumerate(a):
        max = -10
        for indexj, j in enumerate(b):
            temp = calc_score(i, j, indexi, indexj)
            if temp > max:
                max = temp
        score_list.append(max)
    if(len(score_list) == 1):
        return score_list[0]
    elif(len(score_list) == 2):
        return (score_list[0] + score_list[1]) / 2
    elif(len(score_list) == 3):
        return (score_list[0] + score_list[1] + score_list[2]) / 3
    elif(len(score_list) == 4):
        return (score_list[0] + score_list[1] + score_list[2] + score_list[3]) / 4
    elif(len(score_list) == 5):
        return (score_list[0] + score_list[1] + score_list[2] + score_list[3] + score_list[4]) / 5
    elif(len(score_list) == 6):
        return (score_list[0] + score_list[1] + score_list[2] + score_list[3] + score_list[4] + score_list[5]) / 6
    elif(len(score_list) == 7):
        return (score_list[0] + score_list[1] + score_list[2] + score_list[3] + score_list[4] + score_list[5] + score_list[6]) / 7
    else:
        score_list.sort()
        return (score_list[-1] + score_list[-2] + score_list[-3] + score_list[-4] + score_list[-5] + score_list[-6] + score_list[-7] + score_list[-8]) / 8

size = num2content.__len__()
similar_matrix = np.zeros((size+1, size+1))
for i in range(854, size+1):
    for j in range(1, size+1):
        similar_matrix[i][j] = calc_correlation(i, j)
    print(i)
print(similar_matrix)
with open("similar_matrix", "wb") as f:
    pickle.dump(similar_matrix, f)
