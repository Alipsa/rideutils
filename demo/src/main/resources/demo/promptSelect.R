library("se.alipsa:rideutils")

# if no defaultValue is given (or defaultValue is not in options), the first option becomes the default
fruit <- promptSelect(
  title = "todays fruit",
  headerText = "pick the fruit to serve",
  message = "pick the fruit of today",
  options = c("Apple", "Banana", "Orange"),
)
print(paste0("The fruit of today is ", fruit, ", type is ", typeof(fruit) ))

# If a list of numbers are given the result will be a number (a double)
num <- promptSelect(
  title = "todays number",
  message = "pick the number of today",
  options = c(1, 2, 3),
  defaultValue = 3
)
print(paste0("The number of today is ", num, ", type is ", typeof(num) ))

# It is also possible to put any object in a list and pass as options
obj <- promptSelect(
  title = "An object of choice",
  message = "pick an object",
  options = list(1, as.integer(2), "none", TRUE),
  defaultValue = as.integer(2)
)
print(paste0("The object is ", obj, ", type is ", typeof(obj) ))
