from scipy.optimize import linprog

# Using a library with the Simplex algorithm
# to solve the "brewers problem"


# Coefficients of the objective function
# prices of products A and B
c = [-13, -23]

# Constraints

# 3 material costs os products A and B
A = [[5, 15], [4, 4], [35, 20]]

# 3 material quantities (limiting factor)
b = [480, 160, 1190]

# Bounds on variables are min and max values the variables can take
# in this case, product quantities
x0_bounds = (0, None)
x1_bounds = (0, None)

# Maximize profit
# method = 'simplex' is deprecated
res = linprog(c, A_ub=A, b_ub=b, bounds=[x0_bounds, x1_bounds], method='highs')

print("Optimal values:", res.x)
print("Maximum profit:", -res.fun)
