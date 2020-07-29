library("se.alipsa:rideutils")

# simple version with all defaults
start <- promptYearMonth(message = "Select start month")

# change output format and languageTag etc.
end <- promptYearMonth(
  message = "Select end month",
  from = "2019-10",
  to = "2022-02",
  initial = "2020-08",
  languageTag = "sv-SE",
  monthFormat = "MMM",
  outputFormat = "MMM/yyyy"
)
print(paste0("start year month is ", start, ", end year month is ", end))
