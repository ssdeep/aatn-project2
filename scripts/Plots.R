# read the output into a data frame
data <- read.csv("output.csv");
# plot densities vs lambda values
plot(data$densities, data$LambdaG, main = "Lambda vs Density", xlab = "Density", ylab = "Lambda", type="l")
# plot densities vs criticalEdges values
plot(data$densities, data$criticalEdges, main = "Critical Edges vs Density", xlab = "Density", ylab = "Critical Edges", type="l")
# additional test case
mymat <- matrix(scan("forcedInput.txt"), ncol = 12, byrow = T)
# include libraries for diagram and shape
library('shape')
library('diagram')
# plot web for the flow graph
plotmat(mymat, legend = F, main="Network topology for forced input")
