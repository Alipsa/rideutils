library("se.alipsa:rideutils")
library("hamcrest")

test.greaterThanNormal <- function() {
    assertThat(23, greaterThan(22))
}

test.greaterThanNa <- function() {
    res <- tryCatch(error = function(cnd) cnd, assertThat(2, greaterThan(NA)))
    hasErrMsg <- grepl("expected is NA, greaterThan NA makes no sense", res$message, fixed=TRUE)
    assertTrue(hasErrMsg)
}

test.greaterThanInf <- function() {
    res <- tryCatch(error = function(cnd) cnd, assertThat(34, greaterThan(20/0)))
    hasErrMsg <- grepl("expected is infinite, greaterThan infinite makes no sense", res$message, fixed=TRUE)
    assertTrue(hasErrMsg)
}

test.lessThanNormal <- function() {
    assertThat(20, lessThan(22))
}

test.lessThanNa <- function() {
    res <- tryCatch(error = function(cnd) cnd, assertThat(2, lessThan(NA)))
    hasErrMsg <- grepl("expected is NA, lessThan NA makes no sense", res$message, fixed=TRUE)
    assertTrue(hasErrMsg)
}

test.lessThanInf <- function() {
    res <- tryCatch(error = function(cnd) cnd, assertThat(34, lessThan(20/0)))
    hasErrMsg <- grepl("expected is infinite, lessThan infinite makes no sense", res$message, fixed=TRUE)
    assertTrue(hasErrMsg)
}
