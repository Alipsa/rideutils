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

help <- function(topic, version = NA,  ...) {
    if (!is.character(topic)) {
        topic <- deparse(substitute(topic))
    }
    if (is.na(version)) {
        pkgVersion <- "3.5.3"
    } else {
        pkgVersion <- version
    }
    additionalArgs <- list(...)
    if (length(additionalArgs) > 0) {
        warning("Ignoring all other additional arguments besides topic and version")
    }

    funcList <- NULL
    # if it is not in www.rdocumentation.org i.e. it is not a CRAN package, the help tab will display a 404 message
    packages <- .packages()
    for (package in packages) {
        #pkg <- paste0('package:', package)
        #fun <- ls(pkg)[(ls(pkg) %in% c(lsf.str(pkg)))]
        fun <- ls(paste0('package:', package))
        names(fun) <- rep(package, length(fun))
        funcList <- c(funcList, fun)
    }
    pos <- which(funcList == topic)
    if(length(pos) == 0) {
        warning(paste("Cannot find help for", topic))
    } else {
        # if it is not a "standard R package" (e.g. base, stats, graphics etc) then the version will likely be something else
        standardPackages <- c("stats", "stats4", "graphics", "grDevices", "utils", "datasets", "methods", "base")
        packageName <- names(funcList)[pos]
        if (!packageName %in% standardPackages && is.na(version)) {
            pkgVersion <- packageVersion(packageName)
            warning(paste("topic is not in the standard packages, please specify version, making a guess that the version is", pkgVersion))
        }
        url <- paste0("https://www.rdocumentation.org/packages/", packageName, "/versions/", pkgVersion, "/topics/", funcList[pos])
        inout$viewHelp(url, topic)
    }
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