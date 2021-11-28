library("se.alipsa:rideutils")

# default format is yyyy-MM-dd
date <- promptDate("Date", message = "Target date")
print(paste("Date is", date))

# Date with a specific default value (other than the default which is today) use defaultValue="" to not have a default date
date <- promptDate("Date with default value", message = "Target date", defaultValue="2021-12-05")
print(paste("Date is", date))

# we can also specify the output (and display) format
date2 <- promptDate("Date with format", message = "Another date", outputFormat = "dd MMM yyyy")
print(paste("Date is", date2))