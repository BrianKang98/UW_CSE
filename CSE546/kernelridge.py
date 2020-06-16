# Brian Kang
import numpy as np
import matplotlib.pyplot as plt
from itertools import compress


# generate random data
def generateData(n):
    np.random.seed(446)
    X = np.random.uniform(size=n)
    e = np.random.randn(n)
    Y = f(X) + e
    return X, Y


# for easier plotting later
def f(X):
    return 4 * np.sin(np.pi * X) * np.cos(6 * np.pi * X ** 2)


# first kernel function
def kernelPoly(X, z, d):
    return np.power(1 + X * z, d)


# second kernel function
def kernelRBF(X, z, gamma):
    return np.exp(-gamma * np.square(X - z))


def kernelRidge(X, Y, kernelFunc, hyperparam, lmbda):
    Xi, Xj = np.meshgrid(X, X)  # get 2D array of all points made from [X by X]
    K = kernelFunc(Xi, Xj, hyperparam)  # do kernel evaluation
    # np.linalg.lstsq(a, b, rcond = 'warning'):
    # Solves the equation a*x = b by computing a vector x that
    # minimizes the squared Euclidean 2-norm of b-ax
    # https://stackoverflow.com/questions/54753132/understanding-numpys-lstsq
    alphahat, _, _, _ = np.linalg.lstsq(K.T @ K + lmbda * K, K.T @ Y, rcond=None)  # get alpha hat
    # return a lambda function of getting array of fhat = sum of alphahat * kernel poly or rbf
    return lambda xprime: np.array([np.sum(alphahat * kernelFunc(X, z, hyperparam)) for z in xprime])


# do both LOOCV and 10-fold by setting k=1 or 10
def kCV(k, X, Y, kernelFunc, hyperparam, lmbda):
    total = len(X)
    index = np.arange(total).astype(int)
    # mix up index and make n/k by k matrix, each row is then a fold
    fold = np.random.permutation(index).reshape(int(total / k), k)
    error = np.zeros(int(total / k))
    # do ridge
    for i, testindx in enumerate(fold):
        trainindx = np.ones(total).astype(int)
        trainindx[testindx] = 0
        # get training set from X using compress() in np.array format
        Xtrain = np.array([xtrain for xtrain in compress(X, trainindx)])
        Ytrain = np.array([ytrain for ytrain in compress(Y, trainindx)])
        # define predictor function
        fhat = kernelRidge(Xtrain, Ytrain, kernelFunc, hyperparam, lmbda)
        # SSR/n
        error[i] = np.sum(np.power(Y[testindx] - fhat(X[testindx]), 2)) / len(testindx)
    return np.mean(error)  # mean error


def problemsABC(n, nfold, B, figureNumber):
    X, Y = generateData(n)

    # Part 3.A.poly
    # make some lambda values
    LMBDA = np.power(10, np.linspace(-2, 2, 10))
    # make some hyperparameter d's in the Natural numbers
    D = np.arange(0, 15)
    savePolyResult = np.zeros([len(LMBDA) * len(D), 3])
    index = 0
    for d in D:
        for lmbda in LMBDA:
            savePolyResult[index] = np.array([d, lmbda, kCV(nfold, X, Y, kernelPoly, d, lmbda)])
            index = index + 1
    # get vars corresponding with smallest error
    bestd, bestlmbda, _ = savePolyResult[np.argmin(savePolyResult[:, 2])]
    print("Best d for poly kernel is " + str(bestd))
    print("Best lambda for poly kernel is " + str(bestlmbda))

    # Part 3.B & C.poly
    xvals = np.linspace(0, 1, 100)
    # bootstrap
    index = np.arange(n).astype(int)
    saveFhat = []
    for i in range(B):
        bootstrap = np.random.choice(index, n, replace=True)
        saveFhat.append(kernelRidge(X[bootstrap], Y[bootstrap], kernelPoly, bestd, bestlmbda))
    # confidence intervals using percentiles
    sortedFhat = np.sort(np.array([fhat(xvals) for fhat in saveFhat]), axis=0)
    CIdown = sortedFhat[int(0.025 * B), :]
    CIup = sortedFhat[int(0.975 * B), :]
    # define predictor function
    fhatPoly = kernelRidge(X, Y, kernelPoly, bestd, bestlmbda)
    plt.figure(figureNumber)
    ax = plt.gca()
    plt.scatter(X, Y)
    plt.plot(xvals, f(xvals), label="True f")  # true f(x)
    plt.plot(xvals, fhatPoly(xvals), label="Predicted f")  # fhat(x)
    ax.fill_between(xvals, CIdown, CIup, color='b', alpha=0.1)
    plt.xlabel("X")
    plt.ylabel("Y")
    plt.legend()
    plt.savefig("hw3_" + str(figureNumber) + ".png")

    # Part 3.A.rbf
    # make some lambda values
    LMBDA = np.power(10, np.linspace(-2, 2, 10))
    # make some hyperparameter gamma's
    GAMMA = np.linspace(0, 50, 20)
    saveRBFResult = np.zeros([len(LMBDA) * len(GAMMA), 3])
    index = 0
    for gamma in GAMMA:
        for lmbda in LMBDA:
            saveRBFResult[index] = np.array([gamma, lmbda, kCV(1, X, Y, kernelRBF, gamma, lmbda)])
            index = index + 1
    # get vars corresponding with smallest error
    bestgamma, bestlmbda, _ = saveRBFResult[np.argmin(saveRBFResult[:, 2])]
    print("Best gamma for rbf kernel is " + str(bestgamma))
    print("Best lambda for rbf kernel is " + str(bestlmbda))

    # Part 3.B & C.rbf
    xvals = np.linspace(0, 1, 100)
    # bootstrap
    index = np.arange(n).astype(int)
    saveFhat = []
    for i in range(B):
        bootstrap = np.random.choice(index, n, replace=True)
        saveFhat.append(kernelRidge(X[bootstrap], Y[bootstrap], kernelRBF, bestgamma, bestlmbda))
    # confidence intervals using percentiles
    sortedFhat = np.sort(np.array([fhat(xvals) for fhat in saveFhat]), axis=0)
    CIdown = sortedFhat[int(0.025 * B), :]
    CIup = sortedFhat[int(0.975 * B), :]
    # define predictor function
    fhatRBF = kernelRidge(X, Y, kernelRBF, bestgamma, bestlmbda)
    plt.figure(figureNumber + 1)
    ax = plt.gca()
    plt.scatter(X, Y)
    plt.plot(xvals, f(xvals), label="True f")  # true f(x)
    plt.plot(xvals, fhatRBF(xvals), label="Predicted f")  # fhat(x)
    ax.fill_between(xvals, CIdown, CIup, color='b', alpha=0.1)
    plt.xlabel("X")
    plt.ylabel("Y")
    plt.legend()
    plt.savefig("hw3_" + str(figureNumber + 1) + ".png")

    return fhatPoly, fhatRBF  # for part E


# problem 3.abc.1
problemsABC(n=30, nfold=1, B=300, figureNumber=1)
print()
# problem 3.abc.2 = 3.d
fhatPoly, fhatRBF = problemsABC(n=300, nfold=10, B=300, figureNumber=3)

# problem 3.e
m = 1000
B = 300
X, Y = generateData(m)
# bootstrap
index = np.arange(m).astype(int)
saveMu = []
for i in range(B):
    bootstrap = np.random.choice(index, m, replace=True)  # size m
    error = np.mean(
        np.power(Y[bootstrap] - fhatPoly(X[bootstrap]), 2) - np.power(Y[bootstrap] - fhatRBF(X[bootstrap]), 2))
    saveMu.append(error)
# confidence intervals using percentiles
sortedMu = np.sort(np.array(saveMu), axis=0)
CIdown = sortedMu[int(0.025 * B)]
CIup = sortedMu[int(0.975 * B)]
print()
print("The CI is: (" + str(CIdown) + ", " + str(CIup) + ")")
if (CIdown <= 0) and (CIup >= 0):
    print(
        "The confidence interval includes 0, so there exists NO statistically significant\nevidence that one of f_rbf or f_poly is better at predicting Y from X.")
else:
    print(
        "The confidence interval does NOT include 0, so there is statistically significant\nevidence that one of f_rbf or f_poly is better at predicting Y from X.")
