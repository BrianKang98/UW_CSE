import matplotlib.pyplot as plt
import numpy as np
from PIL import Image as Image
from mnist import MNIST

def load_dataset():
  mndata = MNIST('../python-mnist/data/')
  X_train, labels_train = map(np.array, mndata.load_training())
  X_test, labels_test = map(np.array, mndata.load_testing())
  return X_train, labels_train, X_test, labels_test

N = 60000
d = 784
X_train, labels_train, X_test, labels_test = load_dataset()
mu = np.empty((d, N), int)
c = np.empty((N, d), int)
for i in (0, d-1):
  val = np.sum(X_train[:, i])/d
  mu = np.append(mu, [np.full(N, val)], axis=0)
  c = np.append(c, np.full((N, 0), val* N), axis=1)
c = X_train - c
sigma = c.T.dot(c)/ N
U, S, V = np.linalg.svd(sigma)
print(S[1])
print(S[2])
print(S[10])
print(S[30])
print(S[50])
sums = np.sum(S)
print(sums)