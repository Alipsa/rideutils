# Overrides some common utils functions that does not yet work properly

View <- function(x, title = NA) {
    if (is.na(title)) {
        title <- deparse(substitute(x))
    }
    inout$View(format(x), title)
}

display <- function(x, title = NA) {
    if (is.na(title)) {
        if (is.character(x)) {
            title <- basename(x)
        } else {
            title <- deparse(substitute(x))
        }
    }
    possibleFile <- paste0(getwd(), "/", x)
    if (file.exists(possibleFile)) {
        inout$display(possibleFile, title)
    } else {
        inout$display(x, title)
    }
}

viewPlot <- function(func, title = "") {
    plotFile <- tempfile(pattern = "plot", fileext = ".svg")
    svg(plotFile)
    # eval(substitute(FUN))
    eval(func)
    dev.off()
    inout$display(plotFile, title)
    return(plotFile)
}

viewHtml <- function(exp, title = "") {
    inout$viewHtml(exp, title)
}

viewer <- function(url, title="") {
    inout$viewer(url, title)
}

# provide a default implementation of browseURL which opens the url in the viewer tab
# still you can fall back to the external brower function of utils::browseURL by specifying a browser
# e.g. browseURL(url="http://www.alipsa.se", browser = "firefox")
browseURL <- function(url, browser = getOption("browser"), encodeIfNeeded = FALSE, title="") {
    if (is.null(browser)) {
        inout$viewer(url, title)
    } else {
        utils::browseURL(url, browser, encodeIfNeeded)
    }
}