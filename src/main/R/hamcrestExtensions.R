greaterThan <- function(expected) {
    if(is.na(expected)) {
        stop("expected is NA, greaterThan NA makes no sense")
        expected <- na.omit(expected)
    }
    if (any( sapply(expected, is.infinite))) {
        stop("expected is infinite, greaterThan infinite makes no sense")
    }
    function(actual) {
        all(actual > expected)
    }
}

lessThan <- function(expected) {
    if(is.na(expected)) {
        stop("expected is NA, lessThan NA makes no sense")
        expected <- na.omit(expected)
    }
    if (any( sapply(expected, is.infinite))) {
        stop("expected is infinite, lessThan infinite makes no sense")
    }
    function(actual) {
        all(actual < expected)
    }
}

#' check wether the actual begins with the expected character vector (string)
str.beginsWith <- function(expected) {
    if(is.na(expected)) {
        stop("expected is NA, str.beginsWith NA makes no sense")
    }
    function(actual) {
        startsWith(as.character(actual), as.character(expected))
    }
}

#' check wether the actual ends with the expected character vector (string)
str.endsWith <- function(expected) {
    if(is.na(expected)) {
        stop("expected is NA, str.endsWith NA makes no sense")
    }
    function(actual) {
        endsWith(as.character(actual), as.character(expected))
    }
}