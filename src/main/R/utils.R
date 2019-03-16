# Overrides some common utils functions that does not yet work properly

View <- function(x, title = NA) {
    if (is.na(title)) {
        inout$View(x)
    } else {
        inout$View(x, title)
    }
}