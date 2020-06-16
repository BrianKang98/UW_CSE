import seaborn as sns
import numpy as np
import matplotlib.pyplot as plt

sns.set()   # for the grid on the background

# From A.6, E[] <= 1/(4n)
# Now we want sqrt(E[]) <= 0.0025,
#   i.e. E[] <= 0.0025^2 = 1/(4n).
#   Thus, n = 40,000
n = 40000
Z = np.random.randn(n)
line1, = plt.step(sorted(Z), np.arange(1, n + 1) / float(n))
line1.set_label("Gaussian")

k = [1,8,64,512]
for i in k:
    v = np.sum(np.sign(np.random.randn(n, i))*np.sqrt(1./i), axis=1)
    line2, = plt.step(sorted(v), np.arange(1, n + 1) / float(n))
    line2.set_label(i)

plt.xlim(-3, 3)
plt.ylim(0.0,1.0)
plt.xlabel("Observations")
plt.ylabel("Probability")
plt.legend()
plt.show()
