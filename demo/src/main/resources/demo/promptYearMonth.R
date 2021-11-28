library("se.alipsa:rideutils")

# simple version with all defaults
start <- promptYearMonth(title="yearMonth with defaults", message = "Select start month")
print(paste("start year month is", start))

# change output format and languageTag etc.
end <- promptYearMonth(
  title="yearMonth with range",
  message = "Select end month",
  from = "2019-10",
  to = "2022-02",
  initial = "2020-08",
  languageTag = "sv-SE",
  monthFormat = "MMM",
  outputFormat = "MMM/yyyy"
)
print(paste("end year month is", end))