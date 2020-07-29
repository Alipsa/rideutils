library("se.alipsa:rideutils")

# default format is yyyy-MM-dd
date <- promptDate("Date", message = "Target date")
print(paste("Date is", date))

# we can also specify the output (and display) format
date2 <- promptDate("Date", message = "Another date", outputFormat = "dd MMM yyyy")
print(paste("Date is", date2))