# Brian Kang
import numpy as np
import matplotlib.pyplot as plt
from mnist import MNIST


def load_dataset():
    mndata = MNIST('.\data')
    mndata.gz = True
    x_train, labels_train = map(np.array, mndata.load_training())
    x_test, labels_test = map(np.array, mndata.load_testing())
    x_train = x_train / 255.0
    x_test = x_test / 255.0
    return x_train, labels_train, x_test, labels_test


# Part A
x_train, y_train, x_test, y_test = load_dataset()
mu = np.mean(x_train, axis=0)
simga = np.dot((x_train - mu).T, x_train - mu) / len(x_train)  # covariance matrix
lmbda, V = np.linalg.eig(simga)
# sort in decreasing order
sortIndx = np.argsort(lmbda)[::-1]
lmbda = lmbda[sortIndx].astype('float')  # change type for later
V = V[:, sortIndx].astype('float')
indx = [0, 1, 9, 29, 49]
print("Eigenvalues:", lmbda[indx])  # get highest selected lmbda
sumlmbda = np.sum(lmbda)
print("Sum of Eigenvalues:", sumlmbda)

# Part C.1
trainError = []
testError = []
ks = np.arange(1, 101)
for k in ks:
    predTrain = mu + np.dot(np.dot(x_train - mu, V[:, :k]), V[:, :k].T)
    predTest = mu + np.dot(np.dot(x_test - mu, V[:, :k]), V[:, :k].T)
    trainError.append(np.mean(np.linalg.norm(x_train - predTrain, axis=1) ** 2))  # mse
    testError.append(np.mean(np.linalg.norm(x_test - predTest, axis=1) ** 2))

plt.figure(1)
plt.plot(ks, trainError, label="Train")
plt.plot(ks, testError, label="Test")
plt.xlabel("K")
plt.ylabel("Error")
plt.legend()
plt.savefig("hw3_13.png")

# Part C.2
value = []
for k in ks:
    value.append(1 - np.sum(lmbda[:k]) / sumlmbda)

plt.figure(2)
plt.plot(ks, value)
plt.xlabel("K")
plt.ylabel("1-Sum(lambda 1...k) / Sum(lambda 1...d)")
plt.savefig("hw3_14.png")

# Part D
# visualize the PCA eigenvectors
# k=10=2*5
plt.figure(3)
fig, axes = plt.subplots(2, 5)
for i, ax in enumerate(axes.flatten()):
    ax.imshow(V[:, i].reshape(28, 28), cmap='gray')
    ax.set_title('PCA Eigen {}'.format(i))
plt.tight_layout()
plt.savefig("hw3_15.png")

# Part E
fignum = 16
for nums in [2, 6, 7]:
    digit = x_train[y_train == nums][0]  # arbitrary
    ks = [5, 15, 40, 100]
    # keep the brackets
    reconstruct = [digit] + [mu + np.dot(np.dot(digit - mu, V[:, :k]), V[:, :k].T) for k in ks]
    fig, axes = plt.subplots(1, 5)
    for i, ax in enumerate(axes.flatten()):
        ax.imshow(reconstruct[i].reshape(28, 28), cmap='gray')
        if i == 0:
            ax.set_title("Real Num.")
        else:
            ax.set_title("k=" + str(ks[i - 1]))
    plt.tight_layout()
    plt.savefig("hw3_" + str(fignum) + ".png")
    fignum = fignum + 1
