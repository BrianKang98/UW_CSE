# Brian Kang
import numpy as np
import matplotlib.pyplot as plt
from mnist import MNIST


def load_dataset():
    mndata = MNIST('.\data')
    mndata.gz = True
    X_train, labels_train = map(np.array, mndata.load_training())
    X_test, labels_test = map(np.array, mndata.load_testing())
    X_train = X_train / 255.0
    X_test = X_test / 255.0
    return X_train, labels_train, X_test, labels_test


# Part A
class kmeans:
    def __init__(self, k):
        self.k = k
        self.objective = []
        self.cluster = None
        self.center = None

    # lloyds algorithm
    def train(self, X, maxIter):
        # get k starting center points randomly chosen
        n, _ = X.shape
        np.random.seed(446)
        startCenter = X[np.random.randint(0, n, size=self.k)]
        # center_i = mu_i
        center = np.copy(startCenter)  # use the copy
        dist = np.zeros((n, self.k))  # must have extra parentheses
        for iter in range(maxIter):
            for i in range(self.k):
                dist[:, i] = np.linalg.norm(X - center[i], axis=1) ** 2
            # find centeroids of each clusters
            centroids = []
            objective = 0
            # referred to some stackoverflow page & lecture notes
            for i in range(self.k):
                getCenter = X[np.argmin(dist, axis=1) == i]  # point with min dist
                objective += np.sum(np.linalg.norm(getCenter - center[i], axis=1) ** 2)
                centroid = np.mean(getCenter, axis=0)
                centroids.append(centroid)
            center = np.copy(np.array(centroids))  # updated center
            self.objective.append(objective)
        self.center = center
        self.cluster = np.argmin(dist, axis=1)

    def predict(self, X):
        n, _ = X.shape
        dist = np.zeros((n, self.k))  # must have extra parentheses
        for i in range(self.k):
            dist[:, i] = np.linalg.norm(X - self.center[i], axis=1) ** 2
        pred = self.center[np.argmin(dist, axis=1)]
        return pred


# Part B.1
x_train, y_train, x_test, y_test = load_dataset()  # load in data

kmean1 = kmeans(10)
kmean1.train(x_train, 75)
plt.figure(1)
plt.plot(kmean1.objective)
plt.xlabel("Iteration")
plt.ylabel("Objective Value")
plt.savefig("hw3_9.png")

# Part B.2
# visualize the cluster centers
# k=10=2*5
plt.figure(2)
fig, axes = plt.subplots(2, 5)
for i, ax in enumerate(axes.flatten()):
    ax.imshow(kmean1.center[i].reshape(28, 28), cmap='gray')
    ax.set_title('Cluster Center {}'.format(i))
plt.tight_layout()
plt.savefig("hw3_10.png")

# Part C
ks = np.array([2, 4, 8, 16, 32, 64])
trainError = []
testError = []
for k in ks:
    kmeani = kmeans(k)
    kmeani.train(x_train, 25)
    predTrain = kmeani.predict(x_train)
    predTest = kmeani.predict(x_test)
    trainError.append(np.mean(np.linalg.norm(x_train - predTrain, axis=1) ** 2))
    testError.append(np.mean(np.linalg.norm(x_test - predTest, axis=1) ** 2))

plt.figure(3)
plt.plot(ks, trainError, label='Train')
plt.plot(ks, testError, label='Test')
plt.xlabel("K")
plt.ylabel("Error")
plt.legend()
plt.savefig("hw3_11.png")
