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