from sklearn.datasets.samples_generator import make_blobs
from sklearn.cluster import AgglomerativeClustering
import itertools
import sys

X, y = make_blobs(n_samples=100, centers=8, n_features=300,random_state=0)
print(X.shape)

clustering = AgglomerativeClustering(linkage='ward', n_clusters=int(sys.argv[1]), compute_full_tree=False)
y_predict=clustering.fit_predict(X)


ii = itertools.count(X.shape[0])
tree=[{'node_id': next(ii), 'left': x[0], 'right':x[1]} for x in clustering.children_]
for leaf in tree:
	print leaf

print y_predict

matrix = open("matrix", "w")
for i in range(100):
	for j in range(300):
		matrix.write(str(X[i][j]) + "\t")
	matrix.write("\n")
matrix.close()

result = open(sys.argv[1]+"_clusters", "w")
for value in y_predict:
	result.write(str(value)+"\n")
result.close()
