# Overrides some common utils functions that does not yet work properly

View <- function(x, title = NA) {
    if (is.na(title)) {
        inout$View(format(x))
    } else {
        inout$View(format(x), title)
    }
}

display <- function(x, title = NA) {
    if (is.na(title)) {
        inout$display(x)
    } else {
        inout$display(x, title)
    }
}