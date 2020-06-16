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

    # get index array of 0 or 1
    keep_train = np.bitwise_or(labels_train == 2, labels_train == 7)
    keep_test = np.bitwise_or(labels_test == 2, labels_test == 7)

    # delete columns that don't give 2 or 7 response
    X_train = X_train[keep_train]
    labels_train = labels_train[keep_train].astype(int)
    X_test = X_test[keep_test]
    labels_test = labels_test[keep_test].astype(int)

    # binary classify to -1, 1
    labels_train[labels_train == 2] = -1
    labels_train[labels_train == 7] = 1
    labels_test[labels_test == 2] = -1
    labels_test[labels_test == 7] = 1
    return X_train, labels_train, X_test, labels_test


x_train, bin_train, x_test, bin_test = load_dataset()  # load in data


# calculate mu
def mu(w, b, X, Y):
    return 1 / (1 + np.exp(-Y * (b + X.T @ w)))


# get the cost function J
def J(w, b, X, Y, lmbda):
    _, n = X.shape
    return np.sum(np.log(1 / mu(w, b, X, Y))) / n + lmbda * np.linalg.norm(w) ** 2


# gradient of w on J (dell w J)
def dwJ(w, b, X, Y, lmbda):
    d, n = X.shape
    mu_i = mu(w, b, X, Y)
    # do matrix algebra much faster
    # https://stackoverflow.com/questions/26089893/understanding-numpys-einsum
    return np.einsum("i, i, di -> d", (1 - mu_i), -Y, X) / n + 2 * lmbda * w


# gradient of b on J (dell b J)
def dbJ(w, b, X, Y):
    d, n = X.shape
    mu_i = mu(w, b, X, Y)
    # do element wise multiplication faster
    return np.einsum("i, i -> ", (1 - mu_i), -Y) / n


# gradient descent
def gd(w0, b0, X, Y, lmbda, stepSize, iter):
    # step size = learning rate
    keepJ = []
    # start will all zeros
    keepW = np.zeros((iter, len(w0)))
    keepB = np.zeros(iter)
    keepW[0] = w0  # 0th row is the starting weights
    keepB[0] = b0  # 0th row is the starting intercept
    keepJ.append(J(w0, b0, X, Y, lmbda))  # keep the 0th cost J
    for i in range(1, iter):
        # new = old - step size * gradient
        keepW[i] = keepW[i - 1] - stepSize * dwJ(keepW[i - 1], keepB[i - 1], X, Y, lmbda)
        # print(i,":")
        # print("keepW: ", keepW)
        keepB[i] = keepB[i - 1] - stepSize * dbJ(keepW[i - 1], keepB[i - 1], X, Y)
        # print("keepB: ", keepB)
        keepJ.append(J(keepW[i], keepB[i], X, Y, lmbda))
        # print("keepJ: ", keepJ)
        i += 1
    return keepW, keepB, keepJ


# classification error
def classifier(w, b, X, Y):
    d, n = X.shape
    levelx = np.sign(b + X.T @ w) > 0  # classify to 1 or -1
    levely = Y == 1
    return 1 - np.sum(np.equal(levelx, levely) / n)


def sgd(w0, b0, X, Y, lmbda, stepSize, iter, batchSize):
    keepJ = []
    # start will all zeros
    keepW = np.zeros((iter, len(w0)))
    keepB = np.zeros(iter)
    keepW[0] = w0  # 0th row is the starting weights
    keepB[0] = b0  # 0th row is the starting intercept
    keepJ.append(J(w0, b0, X, Y, lmbda))  # keep the 0th cost J
    for i in range(1, iter):
        batch = np.random.randint(0, len(Y), batchSize)  # random index selection
        # new = old - step size * gradient
        keepW[i] = keepW[i - 1] - stepSize * dwJ(keepW[i - 1], keepB[i - 1], X[:, batch], Y[batch], lmbda)
        keepB[i] = keepB[i - 1] - stepSize * dbJ(keepW[i - 1], keepB[i - 1], X[:, batch], Y[batch])
        keepJ.append(J(keepW[i], keepB[i], X, Y, lmbda))
        i += 1
    return keepW, keepB, keepJ


def problem6b():
    d, _ = x_train.T.shape
    w0 = np.zeros(d)
    b0 = 0
    lmbda = 0.1
    iter = 100
    trainW, trainB, trainJ = gd(w0, b0, x_train.T, bin_train, lmbda, 0.05, iter)
    testW, testB, testJ = gd(w0, b0, x_test.T, bin_test, lmbda, 0.05, iter)

    plt.figure(0)
    plt.plot(range(0, iter), trainJ, label="train")
    plt.plot(range(0, iter), testJ, label="test")
    plt.xlabel("Iteration")
    plt.ylabel("Cost J(w,b)")
    plt.legend()
    plt.savefig('hw2_6.png')

    train_error = [classifier(w, b, x_train.T, bin_train) for w, b in zip(trainW, trainB)]
    test_error = [classifier(w, b, x_test.T, bin_test) for w, b in zip(testW, testB)]
    plt.figure(1)
    plt.plot(range(0, iter), train_error, label="train")
    plt.plot(range(0, iter), test_error, label="test")
    plt.xlabel("Iteration")
    plt.ylabel("Misclassification Error")
    plt.legend()
    plt.savefig('hw2_7.png')


def problem6c():
    d, _ = x_train.T.shape
    w0 = np.zeros(d)
    b0 = 0
    lmbda = 0.1
    iter = 100
    batch = 1
    trainW, trainB, trainJ = sgd(w0, b0, x_train.T, bin_train, lmbda, 0.05, iter, batch)
    testW, testB, testJ = sgd(w0, b0, x_test.T, bin_test, lmbda, 0.05, iter, batch)

    plt.figure(2)
    plt.plot(range(0, iter), trainJ, label="train")
    plt.plot(range(0, iter), testJ, label="test")
    plt.xlabel("Iteration")
    plt.ylabel("Cost J(w,b)")
    plt.legend()
    plt.savefig('hw2_8.png')

    train_error = [classifier(w, b, x_train.T, bin_train) for w, b in zip(trainW, trainB)]
    test_error = [classifier(w, b, x_test.T, bin_test) for w, b in zip(testW, testB)]
    plt.figure(3)
    plt.plot(range(0, iter), train_error, label="train")
    plt.plot(range(0, iter), test_error, label="test")
    plt.xlabel("Iteration")
    plt.ylabel("Misclassification Error")
    plt.legend()
    plt.savefig('hw2_9.png')


def problem6d():
    d, _ = x_train.T.shape
    w0 = np.zeros(d)
    b0 = 0
    lmbda = 0.1
    iter = 100
    batch = 100
    trainW, trainB, trainJ = sgd(w0, b0, x_train.T, bin_train, lmbda, 0.05, iter, batch)
    testW, testB, testJ = sgd(w0, b0, x_test.T, bin_test, lmbda, 0.05, iter, batch)

    plt.figure(4)
    plt.plot(range(0, iter), trainJ, label="train")
    plt.plot(range(0, iter), testJ, label="test")
    plt.xlabel("Iteration")
    plt.ylabel("Cost J(w,b)")
    plt.legend()
    plt.savefig('hw2_10.png')

    train_error = [classifier(w, b, x_train.T, bin_train) for w, b in zip(trainW, trainB)]
    test_error = [classifier(w, b, x_test.T, bin_test) for w, b in zip(testW, testB)]
    plt.figure(5)
    plt.plot(range(0, iter), train_error, label="train")
    plt.plot(range(0, iter), test_error, label="test")
    plt.xlabel("Iteration")
    plt.ylabel("Misclassification Error")
    plt.legend()
    plt.savefig('hw2_11.png')


problem6b()
problem6c()
problem6d()
