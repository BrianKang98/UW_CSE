'''
    Template for polynomial regression
    AUTHOR Eric Eaton, Xiaoxiang Hu
'''

import numpy as np


#-----------------------------------------------------------------
#  Class PolynomialRegression
#-----------------------------------------------------------------

class PolynomialRegression:

    def __init__(self, degree=1, reg_lambda=1E-8):
        """
        Constructor
        """
        self.regLambda = reg_lambda
        self.degree = degree
        self.theta = None
        self.mu = None
        self.sd = None

    def polyfeatures(self, X, degree):
        """
        Expands the given X into an n * d array of polynomial features of
            degree d.

        Returns:
            A n-by-d numpy array, with each row comprising of
            X, X * X, X ** 3, ... up to the dth power of X.
            Note that the returned matrix will not include the zero-th power.

        Arguments:
            X is an n-by-1 column numpy array
            degree is a positive integer
        """
        n, d = X.shape   # get the row and column dimension of X
        X_ = X  # save X in different variable "X_"
        for i in range(2, degree+1):    # because last index excludive and we want the d'th degree
            expX = np.power(X, i)  # make d'th power of X
            X_ = np.concatenate((X_, expX), axis=1) # add d'th power column to the right
        return X_

    def fit(self, X, y):
        """
            Trains the model
            Arguments:
                X is a n-by-1 array
                y is an n-by-1 array
            Returns:
                No return value
            Note:
                You need to apply polynomial expansion and scaling
                at first
        """
        n = len(X)
        X = self.polyfeatures(X, self.degree)  # poly expand
        xbar = np.mean(X, axis=0)   # get mean of columns
        sdhat = np.std(X, axis=0)   # get std dev of columns
        X = (X-xbar)/sdhat  # standardize
        X_ = np.c_[np.ones([n, 1]), X]  # add 1s column
        n, d = X_.shape     # get dim of new data matrix
        d = d-1  # remove 1 for the extra column of ones we added to get the original num features

        # construct reg matrix
        reg_matrix = self.regLambda * np.eye(d + 1)
        reg_matrix[0, 0] = 0
        # analytical solution (X'X + regMatrix)^-1 X' y
        self.theta = np.linalg.pinv(X_.T.dot(X_) + reg_matrix).dot(X_.T).dot(y)
        self.mu = xbar
        self.sd = sdhat

    def predict(self, X):
        """
        Use the trained model to predict values for each instance in X
        Arguments:
            X is a n-by-1 numpy array
        Returns:
            an n-by-1 numpy array of the predictions
        """
        n = len(X)
        X = self.polyfeatures(X, self.degree)  # poly expand
        X = (X - self.mu) / self.sd  # standardize again
        X_ = np.c_[np.ones([n, 1]), X]  # add 1s column
        return X_.dot(self.theta)   # predict the fitted values


#-----------------------------------------------------------------
#  End of Class PolynomialRegression
#-----------------------------------------------------------------



def learningCurve(Xtrain, Ytrain, Xtest, Ytest, reg_lambda, degree):
    """
    Compute learning curve

    Arguments:
        Xtrain -- Training X, n-by-1 matrix
        Ytrain -- Training y, n-by-1 matrix
        Xtest -- Testing X, m-by-1 matrix
        Ytest -- Testing Y, m-by-1 matrix
        regLambda -- regularization factor
        degree -- polynomial degree

    Returns:
        errorTrain -- errorTrain[i] is the training accuracy using
        model trained by Xtrain[0:(i+1)]
        errorTest -- errorTrain[i] is the testing accuracy using
        model trained by Xtrain[0:(i+1)]

    Note:
        errorTrain[0:1] and errorTest[0:1] won't actually matter, since we start displaying the learning curve at n = 2 (or higher)
    """

    n = len(Xtrain)
    errorTrain = np.zeros(n)
    errorTest = np.zeros(n)
    glm = PolynomialRegression(degree, reg_lambda)   # get polynom model
    for i in range(2, n+1):
        glm.fit(Xtrain[0:(i+1)], Ytrain[0:(i+1)])   # fit the training data
        errorTrain[i-1] = np.mean((glm.predict(Xtrain[0:(i+1)]) - Ytrain[0:(i+1)])**2)
        errorTest[i-1] = np.mean((glm.predict(Xtest[0:(i+1)]) - Ytest[0:(i+1)])**2)
    return errorTrain, errorTest
