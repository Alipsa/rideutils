# Overrides some common utils functions that does not yet work properly

View <- function(x, title = NA) {
    if (is.na(title)) {
        title <- deparse(substitute(x))
    }
    if (class(x) == "table") {
        inout$View(as.data.frame(x))
    } else {
        inout$View(format(x), title)
    }
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

help <- function(topic, package = NULL, version = NULL,  ...) {
    if (!is.character(topic)) {
        topic <- deparse(substitute(topic))
    }

    additionalArgs <- list(...)
    if (length(additionalArgs) > 0) {
        warning("Ignoring all other additional arguments besides topic and version")
    }

    defaultVerson <- "3.5.3"

    createUrl <- function(packageName, pkgVersion, funName) {
        paste0("https://www.rdocumentation.org/packages/", packageName, "/versions/", pkgVersion, "/topics/", funName)
    }

    createSearchUrl <- function(topic) {
        #paste0("https://www.rdocumentation.org/search?q=", topic) # Does not diplay in a javafx webview
        paste0("https://search.r-project.org/?P=", topic)
    }

    if (!is.null(package)) {
        url <- createUrl(package, ifelse(is.null(version), defaultVerson, version), topic)
        if (!UrlUtil$exists(url, 10L)) {
            message(paste0("'", topic, "' in package '", package, "' with url '", url, "' is not valid" ))
            url <- createSearchUrl(topic)
        }
        inout$viewHelp(url, topic)
        return()
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
        inout$viewHelp(createSearchUrl(topic), topic)
    } else {
        # if it is not a "standard R package" (e.g. base, stats, graphics etc) then the version will likely be something else
        standardPackages <- c("stats", "stats4", "graphics", "grDevices", "utils", "datasets", "methods", "base")
        for (idx in pos) {
            packageName <- names(funcList)[idx]
            funName <- funcList[idx]
            pkgVersion <- ifelse(is.null(version), defaultVerson, version)

            if (!packageName %in% standardPackages && is.null(version)) {
                pkgVersion <- packageVersion(packageName)
                message(paste0("Guessing that '", topic, "' in package '", packageName, "', is version ", pkgVersion))
            }
            url <- createUrl(packageName, pkgVersion, funName)
            if (!UrlUtil$exists(url, 10L)) {
                message(paste0("'", topic, "' in package '", packageName, "' with url '", url, "' is not valid" ))
                url <- createSearchUrl(topic)
            }
            inout$viewHelp(url, paste(packageName, ":", topic))
        }
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