# Some simple gui interaction dialogs

# A text input dialog
prompt <- function(title = "", headerText = "", message = "") {
  import(se.alipsa.rideutils.Dialogs)
  dialogs <- Dialogs$new(inout$getStage())
  dialogs$prompt(title, headerText, message)
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