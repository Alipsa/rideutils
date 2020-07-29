library("se.alipsa:rideutils")

fileType <- promptYearMonth("Select a year month", message = "Forecast date")
print(paste("year month is", fileType))