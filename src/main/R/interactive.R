# Title     : interactive
# Objective : Some simple gui interaction dialogs
# Created by: Per nyfelt
# License: MIT

# Since there is no meaningful stdin in a javafx applicaton we pop up a prompt instead
readline <- function(prompt = "") {
  prompt(message = prompt)
}

# A text input dialog
prompt <- function(title = "", headerText = "", message = "", defaultValue = "") {
  import(se.alipsa.rideutils.Dialogs)
  dialogs <- Dialogs$new(inout$getStage())
  if (is.na(defaultValue) || is.null(defaultValue)) {
    defaultValue <- ""
  }
  dialogs$prompt(title, headerText, message, defaultValue)
}

# todo maybe add a default to prompt date as well?
#promptDate <- function(title = "", message = "", outputFormat = "yyyy-MM-dd", defaultValue = as.character(Sys.Date())) {
promptDate <- function(title = "", message = "", outputFormat = "yyyy-MM-dd") {
  import(se.alipsa.rideutils.Dialogs)
  dialogs <- Dialogs$new(inout$getStage())
  dialogs$promptDate(title, message, outputFormat)
}

# languageTag is one of language tags in the table: https://www.oracle.com/java/technologies/javase/jdk8-jre8-suported-locales.html
promptYearMonth <- function(title = "",  message = "", from=NA, to=NA, initial=NA, languageTag=NA, monthFormat = "MMMM", outputFormat = "yyyy-MM") {
  import(se.alipsa.rideutils.Dialogs)
  dialogs <- Dialogs$new(inout$getStage())

  if (is.na(initial)) {
    initialDate <- as.POSIXlt(Sys.Date())
    initial <- format(initialDate, "%Y-%m")
  } else {
    initialDate <- as.POSIXlt(paste0(initial, "-01"))
  }
  if (is.na(from)) {
    fromDate <- initialDate
    fromDate$year <- fromDate$year - 3
    from <-  format(fromDate, "%Y-%m")
  }
  if (is.na(to)) {
    toDate <- initialDate
    toDate$year <- toDate$year + 3
    to <-  format(fromDate, "%Y-%m")
  }
  dialogs$promptYearMonth(title, message, from, to, initial, languageTag, monthFormat, outputFormat)
}

# A file chooser dialog
chooseFile <- function (title, initialDir = ".", description, ...) {
  import(se.alipsa.rideutils.Dialogs)
  dialogs <- Dialogs$new(inout$getStage())
  file <- dialogs$chooseFile(title,  initialDir, description, ...)
  if (is.null(file)) {
    return(NA)
  } else {
    return(file$getAbsolutePath())
  }
}

# A dir chooser dialog
chooseDir <- function (title, initialDir = ".") {
  import(se.alipsa.rideutils.Dialogs)
  dialogs <- Dialogs$new(inout$getStage())
  dir <- dialogs$chooseDir(title,  initialDir)
  if (is.null(dir)) {
    return(NA)
  } else {
    return(dir$getAbsolutePath())
  }
}