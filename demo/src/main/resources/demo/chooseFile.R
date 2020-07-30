library("se.alipsa:rideutils")

file <- chooseFile(
  "Choose the forecast excel for previous month",
  ".",
  "Excel files",
  "*.xls", "*.xlsx"
)
print(paste("File chosen is", file))