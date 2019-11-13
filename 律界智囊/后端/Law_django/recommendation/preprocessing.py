import pickle

with open("similar_matrix", "rb") as f:
    similar_matrix = pickle.load(f)

arg_sort = similar_matrix.argsort()
arg_sort_top10 = arg_sort[:,-11:-1]
arg_sort_top5 = arg_sort[:, -6:-1]
arg_sort_top3 = arg_sort[:, -4:-1]

with open("arg_sort_top10", "wb") as f:
    pickle.dump(arg_sort_top10, f)

with open("arg_sort_top5", "wb") as f:
    pickle.dump(arg_sort_top5, f)

with open("arg_sort_top3", "wb") as f:
    pickle.dump(arg_sort_top3, f)