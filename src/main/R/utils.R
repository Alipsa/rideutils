# Overrides some common utils functions that does not yet work properly

View <- function(x, title = NA) {
    if (is.na(title)) {
        title <- deparse(substitute(x))
    }
    inout$View(format(x), title)
}

display <- function(x, title = NA) {
    if (is.na(title)) {
        title <- deparse(substitute(x))
    }
    inout$display(x, title)
}