import numpy as np
#import matplotlib.pyplot as plt





A = np.array([[0,2,4],[2,4,2],[3,3,1]])
b = np.array([-2,-2,-4]).T  # transpose
c = np.array([1,1,1]).T

# A.11.a
Ainv = np.linalg.inv(A) # A inverse
print("A^(-1) = \n", Ainv)

# A.11.b
Ainvb = Ainv @ b  #A inverse * b
Ac = A @ c   #A * c
print("\nAb = \n", Ainvb)
print("\nAc = \n", Ac)
