import numpy as np
from mnist import MNIST


def load_dataset():
    mndata = MNIST('.\data')
    mndata.gz = True
    X_train, labels_train = map(np.array, mndata.load_training())
    X_test, labels_test = map(np.array, mndata.load_testing())
    X_train = X_train / 255.0
    X_test = X_test / 255.0
    return X_train, labels_train, X_test, labels_test


x_train, label_train, x_test, label_test = load_dataset()  # load in data


# print("x_train: ")
# print(x_train, x_train.shape)
# label_train = np.asfarray(label_train)

# print("\noriginal label: ")
# print(label_train, label_train.shape)
# print(x_test, x_test.shape)
# print(label_test, label_test.shape)

def train(X, Y, reg_lambda):
    d = X.shape[1]
    # solve for \hat{w}: (XtX+lambda*I) * w_hat = XtY
    lhs = np.dot(np.transpose(X), X) + reg_lambda * np.eye(d)
    rhs = np.dot(np.transpose(X), Y)
    w_hat = np.linalg.solve(lhs, rhs)
    return w_hat


def predict(w_hat, X):  # y values we are predicting are lables
    Y_hat = np.dot(X, w_hat)  # equal to e_j'th column of I * w_hat^T * x_i'th column from X
    # print("\nDim of y hat: ", Y_hat.shape)
    return Y_hat.argmax(axis=1)  # get argmax_j [e_j * w_hat * x_i]


num_class = 10  # this is k
onehot_label_train = np.eye(num_class)[label_train]
onehot_label_test = np.eye(num_class)[label_test]
# print("\none hot coded label: ")
# print(onehot_label_train, onehot_label_train.shape)

What = train(x_train, onehot_label_train, 1E-4)
# print("\ndim of w hat: ", What.shape)

# on training set
pred_train_label = predict(What, x_train)
# print("\npredicted label: ")
# print(pred_train_label, pred_train_label.shape)

labelDiff_train = np.equal(label_train, pred_train_label)
errorTrain = np.mean(1 - labelDiff_train)

# on test set
pred_test_label = predict(What, x_test)
labelDiff_test = np.equal(label_test, pred_test_label)
errorTest = np.mean(1 - labelDiff_test)

print("train error: ", errorTrain)
print("test error: ", errorTest)
