# Brian Kang
import torch
import torch.nn as nn
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


class nnet(nn.Module):
    def __init__(self, X, Y):
        super(nnet, self).__init__()
        self.X = X.T  # 784 x n
        self.Y = Y.T  # 10 x n
        d, n1 = self.X.shape  # 784
        k, n2 = self.Y.shape
        h = 64  # number of nodes in the hidden layer
        alpha = 1 / np.sqrt(d)
        torch.manual_seed(446)
        self.w0 = nn.Parameter(torch.FloatTensor(h, d).uniform_(-alpha, alpha), requires_grad=True)
        self.w1 = nn.Parameter(torch.FloatTensor(k, h).uniform_(-alpha, alpha), requires_grad=True)
        self.b0 = nn.Parameter(torch.FloatTensor(h, n1).uniform_(-alpha, alpha), requires_grad=True)
        self.b1 = nn.Parameter(torch.FloatTensor(k, n2).uniform_(-alpha, alpha), requires_grad=True)
        self.layer1 = torch.zeros(h, n1)
        self.output = torch.zeros(k, n2)
        self.reLU = nn.ReLU()  # we instantiate an instance of the ReLU module

    def forward(self, X):
        self.layer1 = self.reLU(torch.add(torch.mm(self.w0, X.T), self.b0))  # reLU(W*X+b)
        self.output = torch.add(torch.mm(self.w1, self.layer1), self.b1)
        return self.output


# Part A
learningRate = 0.001
h = 64
d = 10

x_train, y_train, x_test, y_test = load_dataset()  # load in data
num_class = 10  # this is k
onehot_label_train = np.eye(num_class)[y_train]  # one-hot code the labels
onehot_label_test = np.eye(num_class)[y_test]

minibatch_size = 600
torch.manual_seed(446)
batch = np.random.randint(0, len(x_train), minibatch_size)  # random index selection
model = nnet(np.array(x_train[batch, :]), np.array(onehot_label_train[batch, :]))

model.train()
optim = torch.optim.Adam(model.parameters(), lr=learningRate)
lossFunc = nn.CrossEntropyLoss()

i = 0
indx = []
lost = []
cont = True  # start training
while cont:
    optim.zero_grad()  # zero all the gradients to get new gradients soon
    outputs = model(torch.from_numpy(np.array(x_train[batch, :])).float())
    label = []  # get label
    for idx, vec in enumerate(np.array(onehot_label_train[batch, :])):
        label = np.append(label, np.where(vec == 1)[0])
    input = outputs.T
    loss = lossFunc(input,
                    torch.LongTensor(torch.from_numpy(np.array(label)).long()))
    loss.backward()  # computes derivatives of the loss with respect to W
    optim.step()  # make a step

    i = i + 1
    indx.append(i)
    lost.append(loss.item())
    if i % 1000 == 1:
        learningRate = learningRate + 0.00025  # slowly increase learning rate
    if i == 60000 and loss.item() >= 0.01:
        i = 0
    if loss.item() < 0.01:
        cont = False  # loss is small enough, stop training
    # next random batch to train on
    torch.manual_seed(446)
    batch = np.random.randint(0, len(x_train), minibatch_size)  # random index selection

# test loss
model.eval()
torch.manual_seed(446)
batch = np.random.randint(0, len(x_test), minibatch_size)
ii = 0
lostVal = []
for iter in range(0, i):
    outputs = model(torch.from_numpy(np.array(x_test[batch, :])).float())
    label = []
    for idx, vec in enumerate(np.array(onehot_label_test[batch, :])):
        label = np.append(label, np.where(vec == 1)[0])
    input = outputs.T
    loss = lossFunc(input,
                    torch.LongTensor(torch.from_numpy(np.array(label)).long()))
    ii = ii + 1
    lostVal.append(loss.item())
    torch.manual_seed(446)
    batch = np.random.randint(0, len(x_test), minibatch_size)

# loss plot
plt.figure(1)
plt.plot(indx, lost, label="Train", linewidth=0.5)
plt.plot(indx, lostVal, label="Test", linewidth=0.5)
plt.xlabel("Iteration")
plt.ylabel("Cross Entropy Training Loss")
plt.suptitle("Minibatch of Size 600")
plt.legend()
plt.savefig("hw3_5.png")

# accuracy plot
acc = [1 - x for x in lost]
accVal = [1 - x for x in lostVal]
plt.figure(2)
plt.plot(indx, acc, label="Train", linewidth=0.5)
plt.plot(indx, accVal, label="Test", linewidth=0.5)
plt.xlabel("Iteration")
plt.ylabel("Accuracy")
plt.suptitle("Minibatch of Size 600")
plt.legend()
plt.ylim(bottom=0)
plt.savefig("hw3_6.png")


# Part B
class nnet2(nn.Module):
    def __init__(self, X, Y):
        super(nnet2, self).__init__()
        self.X = X.T  # 784 x n
        self.Y = Y.T  # 10 x n
        d, n = self.X.shape  # 784 x minibatch
        k, _ = self.Y.shape
        h1 = 32  # number of nodes in the hidden layer
        h2 = 32  # second hidden layer
        alpha = 1 / np.sqrt(d)
        torch.manual_seed(446)
        self.w0 = nn.Parameter(torch.FloatTensor(h1, d).uniform_(-alpha, alpha), requires_grad=True)
        self.w1 = nn.Parameter(torch.FloatTensor(h2, h1).uniform_(-alpha, alpha), requires_grad=True)
        self.w2 = nn.Parameter(torch.FloatTensor(k, h2).uniform_(-alpha, alpha), requires_grad=True)
        self.b0 = nn.Parameter(torch.FloatTensor(h1, n).uniform_(-alpha, alpha), requires_grad=True)
        self.b1 = nn.Parameter(torch.FloatTensor(h2, n).uniform_(-alpha, alpha), requires_grad=True)
        self.b2 = nn.Parameter(torch.FloatTensor(k, n).uniform_(-alpha, alpha), requires_grad=True)
        self.layer1 = torch.zeros(h1, n)
        self.layer2 = torch.zeros(h2, n)
        self.output = torch.zeros(k, n)
        self.reLU = nn.ReLU()  # we instantiate an instance of the ReLU module

    def forward(self, X):
        self.layer1 = self.reLU(torch.add(torch.mm(self.w0, X.T), self.b0))  # reLU(W*X+b)
        self.layer2 = self.reLU(torch.add(torch.mm(self.w1, self.layer1), self.b1))
        self.output = torch.add(torch.mm(self.w2, self.layer2), self.b2)
        return self.output


learningRate = 0.001
h1 = 32
h2 = 32
d = 10

minibatch_size = 600
torch.manual_seed(446)
batch = np.random.randint(0, len(x_train), minibatch_size)  # random index selection
model = nnet2(np.array(x_train[batch, :]), np.array(onehot_label_train[batch, :]))

model.train()
optim = torch.optim.Adam(model.parameters(), lr=learningRate)
lossFunc = nn.CrossEntropyLoss()

i = 0
indx = []
lost = []
cont = True  # start training
while cont:
    optim.zero_grad()  # zero all the gradients to get new gradients soon
    outputs = model(torch.from_numpy(np.array(x_train[batch, :])).float())
    label = []  # get label
    for idx, vec in enumerate(np.array(onehot_label_train[batch, :])):
        label = np.append(label, np.where(vec == 1)[0])
    input = outputs.T
    loss = lossFunc(input,
                    torch.LongTensor(torch.from_numpy(np.array(label)).long()))
    loss.backward()  # computes derivatives of the loss with respect to W
    optim.step()  # make a step

    i = i + 1
    indx.append(i)
    lost.append(loss.item())
    if i % 1000 == 1:
        learningRate = learningRate + 0.00025  # slowly increase learning rate
    if i == 60000 and loss.item() >= 0.01:
        i = 0
    if loss.item() < 0.01:
        cont = False  # loss is small enough, stop training
    # next random batch to train on
    torch.manual_seed(446)
    batch = np.random.randint(0, len(x_train), minibatch_size)  # random index selection

# test loss
model.eval()
torch.manual_seed(446)
batch = np.random.randint(0, len(x_test), minibatch_size)
ii = 0
lostVal = []
for iter in range(0, i):
    outputs = model(torch.from_numpy(np.array(x_test[batch, :])).float())
    label = []
    for idx, vec in enumerate(np.array(onehot_label_test[batch, :])):
        label = np.append(label, np.where(vec == 1)[0])
    input = outputs.T
    loss = lossFunc(input,
                    torch.LongTensor(torch.from_numpy(np.array(label)).long()))
    ii = ii + 1
    lostVal.append(loss.item())
    torch.manual_seed(446)
    batch = np.random.randint(0, len(x_test), minibatch_size)

# loss plot
plt.figure(3)
plt.plot(indx, lost, label="Train", linewidth=0.5)
plt.plot(indx, lostVal, label="Test", linewidth=0.5)
plt.xlabel("Iteration")
plt.ylabel("Cross Entropy Training Loss")
plt.suptitle("Minibatch of Size 600")
plt.legend()
plt.savefig("hw3_7.png")

# accuracy plot
acc = [1 - x for x in lost]
accVal = [1 - x for x in lostVal]
plt.figure(4)
plt.plot(indx, acc, label="Train", linewidth=0.5)
plt.plot(indx, accVal, label="Test", linewidth=0.5)
plt.xlabel("Iteration")
plt.ylabel("Accuracy")
plt.suptitle("Minibatch of Size 600")
plt.legend()
plt.ylim(bottom=0)
plt.savefig("hw3_8.png")
