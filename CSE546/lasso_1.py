# Brian Kang
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd

# make synthetic data
n = 500  # row
d = 1000  # column
k = 100  # ???
sd = 1  # standard normal sd
X = np.random.randn(n, d)  # sample from standard normal dist
error = np.random.randn(n)  # sample from standard normal dist
wj = np.empty(d)  # made up weights
for j in range(d):
    if j < k:
        wj[j] = j / k
    else:
        wj[j] = 0
Y = np.dot(X, wj) + error  # synthetic data


# get smallest lambda that shrinks all w to 0
def lambdaMax(X, Y):
    ybar = Y - np.mean(Y)
    return np.amax(2 * np.absolute(np.dot(X.T, ybar)))


# do coordinate descent algorithm for lasso
def coordDescentLasso(X, Y, w0, lmbda, tol):
    n, d = X.shape
    a = np.zeros(d)
    c = np.zeros(d)
    wNew = np.copy(w0)  # copy of initial weights, changing one vars doesn't affect the other
    doCoordDescent = True  # while not converged, do...
    while doCoordDescent:
        wOld = np.copy(wNew)  # after first loop, wNew is now wOld
        b = np.sum(Y - np.dot(X, wNew)) / n  # get b value
        for k in range(d):
            a[k] = 2 * np.sum(np.power(X[:, k], 2))
            resid = Y - (b + np.dot(X, wNew) - X[:, k] * wNew[k])  # subtract since we sum_{j!=k}
            c[k] = 2 * np.sum(X[:, k] * resid)
            # update weights
            if c[k] < -lmbda:
                wNew[k] = (c[k] + lmbda) / a[k]
            elif c[k] > lmbda:
                wNew[k] = (c[k] - lmbda) / a[k]
            else:
                wNew[k] = 0
        doCoordDescent = (np.amax(np.absolute(wNew - wOld)) > tol)  # false if error < tolerance
    return wNew, b


def problem4():
    w0 = np.random.randn(d)  # make up starting weights
    # keep weights, b values
    keepW = []  # list, not np.array
    keepB = []

    # Compute lasso along regularization path
    lmbdaMax = lambdaMax(X, Y)  # Largest penalty
    # print(lmbdaMax)
    numlasso = 20  # number of lasso's to run. increase until regPath[numlasso] close to 0
    regPath = [lmbdaMax] * numlasso
    for i in range(numlasso):
        regPath[i] = regPath[i] / np.power(1.5, i)
    # print(regPath)
    for lmbda in regPath:
        w, b = coordDescentLasso(X, Y, w0, lmbda, 0.1)
        keepW.append(w)
        keepB.append(b)

    # a)
    keepFeat = []
    for w in keepW:
        keepFeat.append(np.sum(w > 0))  # count how many features kept

    plt.figure(0)
    plt.plot(regPath, keepFeat)
    plt.xlabel("$\lambda$")
    plt.ylabel("Number of Non-Zero Features")
    plt.xscale('log')
    plt.savefig('hw2_1.png')

    # b)
    FDR = []  # false discovery rate
    TPR = []  # true positive rate
    for w in keepW:
        FDR.append(np.sum(w[k:] > 0) / (d - k))
        TPR.append(np.sum(w[:k] > 0) / k)

    plt.figure(1)
    plt.scatter(FDR, TPR)
    plt.xlabel("False Discovery Rate")
    plt.ylabel("True Positive Rate")
    plt.savefig('hw2_2.png')


def problem5():
    df_train = pd.read_table("crime-train.txt")  # load datasets
    df_test = pd.read_table("crime-test.txt")

    d = 95  # number of features (columns)
    Xbody = df_train.iloc[:, 1:96].copy()
    X_train = Xbody.values  # get a copy
    # print(X_train)
    X_test = df_test.iloc[:, 1:96].copy().values
    Y_train = df_train['ViolentCrimesPerPop'].values  # convert to numpy array
    # print(Y_train)
    Y_test = df_test["ViolentCrimesPerPop"].values

    w0 = np.zeros(d)  # intial weights
    # keep weights, b values
    keepW = []  # list, not np.array
    keepB = []

    # Compute lasso along regularization path
    lmbdaMax = lambdaMax(X_train, Y_train)  # Largest penalty
    lmbda = 1  # temporary arbitrary number
    regPath = []
    i = 0
    while lmbda > 0.01:  # keep doing lasso until lambda <= 0.01
        lmbda = lmbdaMax / np.power(2, i)
        # print(lmbda, ", ", i)
        regPath.append(lmbda)
        w, b = coordDescentLasso(X_train, Y_train, w0, lmbda, 0.1)
        w0 = w  # use new weights as the weights for next lasso
        keepW.append(w)
        keepB.append(b)
        i += 1

    # a)
    keepFeat = []
    for w in keepW:
        keepFeat.append(np.sum(w > 0))  # count how many features kept

    plt.figure(2)
    plt.plot(regPath, keepFeat)
    plt.xlabel("$\lambda$")
    plt.ylabel("Number of Non-Zero Features")
    plt.xscale('log')
    plt.savefig('hw2_3.png')

    # b)
    # get coefficients of specific vars from the lassos
    vars = ["agePct12t29", "pctWSocSec", "pctUrban", "agePct65up", "householdsize"]
    for i in vars:
        indx = Xbody.columns.get_loc(i)
        getCoeff = [row[indx] for row in keepW]
        plt.figure(3)
        line, = plt.plot(regPath, getCoeff)
        line.set_label(i)

    plt.xlabel("$\lambda$")
    plt.ylabel("Coefficients")
    plt.xscale('log')
    plt.legend()
    plt.savefig('hw2_4.png')

    # c)
    # get squared errors from the lassos
    errorTrain = [np.mean(np.square(np.dot(X_train, w) + b - Y_train)) for w, b in zip(keepW, keepB)]
    errorTest = [np.mean(np.square(np.dot(X_test, w) + b - Y_test)) for w, b in zip(keepW, keepB)]

    plt.figure(4)
    plt.plot(regPath, errorTrain, label="train")
    # line1.set_label("Train")
    plt.plot(regPath, errorTest, label="test")
    # line2.set_label("Test")
    plt.xlabel("$\lambda$")
    plt.ylabel("MSE")
    plt.xscale('log')
    plt.legend()
    plt.savefig('hw2_5.png')

    # d)
    # print(regPath)
    indx = 4  # index where lambda approx = 30
    getCoeff = [row[indx] for row in keepW]  # the weights
    print("Most Positive Lasso Coefficient: ", np.max(getCoeff))
    # Out: "Most Positive Lasso Coefficient:  0.005256729455865754"
    maxindx = np.argmax(getCoeff)
    print("Corresponding Variable:", Xbody.columns[maxindx])
    # Out: "perCapInc"
    print("Most Negative Lasso Coefficient: ", np.min(getCoeff))
    # Out: "Most Negative Lasso Coefficient:  0.0"
    minindx = np.argmin(getCoeff)
    print("Corresponding Variable:", Xbody.columns[minindx])
    # Out: "population"


problem4()
problem5()
