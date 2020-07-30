# rideutils
Java FX GUI utilities for use in Renjin applications e.g. Ride

## API / Usage overview

### Hamcrest extensions
__greaterThan <- function(expected)__
Allows you do do test expressions such as `assertThat(someVar, greaterThan(22))`
It is vectorized so asserts that all elements are greater than the given for a vector
if given as an argument.

__lessThan <- function(expected)__
Allows you do do test expressions such as `assertThat(somVar, lessThan(22))`
it is vectorized so asserts that all elements are less than the given for a vector
if given as an argument.

### Image View
__readImage <- function(url)__

_param:_ url - the url path to the reasource to read

_return value:_ A javafx.scene.image.Image that can be passed to a java program to e.g. display it

__as.imageView <- function(x)__
Wraps a javafx.scene.image.Image in a javafx.scene.image.ImageView

_param:_ x - the Image

_return value:_ as javafx.scene.image.ImageView

### Utils
__View <- function(x, title = NA)__
Provides a similar functionality as the R utils function View i.e. Invoke a spreadsheet-style data viewer on a matrix-like R object.

_param:_ x - The matrix or data.frame to view, the content is formatted for viewing using format(). 

It depends on an object called inout injected into the session that has a java method called View defined as
`void View(SEXP sexp, String... title);`

__display <- function(x, title = NA)__
Used to display an image in a javafx application.

_param:_ x - the filename, imageView or image object to display

The javafx application executing the R code through the Renjin scripting engine need to 
inject an object called inout that implements the following methods
```java
void display(javafx.scene.Node node, String... title);

void display(javafx.scene.image.Image img, String... title);

void display(String fileName, String... title);
``` 
_Example:_
```r
library("grDevices")
library("graphics")
library("se.alipsa:rideutils") 
# plot a svg image to a file
fileName <- "/tmp/svgplot.svg"
svg(fileName)
plot(sin, -pi, 2*pi)
dev.off()
# convert the image to a a javafx Image and display it in the javafx application, the second argument is the title of the window (optional)
display(fileName, "svgplot")
```

### Interactive user input
These are functions that allows the R program to interact with the user running it.

__readline <- function(prompt = "")__
Overrides the base R readline function and pops up a graphical input dialog instead of reading from stdin.

_Example:_
```r
library("se.alipsa:rideutils") 
var <- readline("enter a number")
print(paste("var is", var))
```

__prompt <- function(title = "", headerText = "", message = "")__
Allows a user to enter string input which we can use in subsequent code.

_Return value:_ It returns a string (character vector) with user input or NA if cancel was pressed.

__chooseFile <- function (title, initialDir = ".", description, ...)__
Allows a user to pick a file.

The elipsis parameter (...) are the allowed file patterns (typically extensions) that the user can pick.

_Example_
```r
library("se.alipsa:rideutils") 
file <- chooseFile(
title = "Choose the forecast excel for previous month",
initialDir = ".",
description = "Excel files",
"*.xls", "*.xlsx"
)
```

__chooseDir <- function (title, initialDir = ".")__
Allows a user to pick a directory.

_Example:_
```r
library("se.alipsa:rideutils") 
dir <- chooseDir("Select output dir", ".")
print(paste("Dir chosen is", dir))
```

__promptDate <- function(title = "", message = "", outputFormat = "yyyy-MM-dd")__
Pops up a date picker dialog allowing the user to pick a date.

_@param:_ outputFormat - determines the format of the picked date in the dialog as well as in the 
return value

_@return value:_ a character string formatted according to the outputFormat param or
in the format "yyyy-MM-dd" is no outputFormat is given.

__promptYearMonth <- function(title = "",  message = "", from=NA, to=NA, initial=NA, languageTag=NA, monthFormat = "MMMM", outputFormat = "yyyy-MM")__

_@param:_ from - a character string with the start year month than can be chosen in the format "yyyy-MM"

_@param:_ to - a character string with the en year month than can be chosen in the format "yyyy-MM"

_@param:_ initial - the initial (default) value in the format "yyyy-MM"

_@param:_ languageTag - The short code for the local e.g. en-US. For a full list of 
language tags see https://www.oracle.com/java/technologies/javase/jdk8-jre8-suported-locales.html

_@param:_ monthFormat - determines the format of the month in the dialog 

_@param:_ outputFormat - determines the format of the picked date in the dialog as well as in the 
return value

_Example:_
```r
library("se.alipsa:rideutils") 
date2 <- promptDate("Date", message = "Another date", outputFormat = "dd MMM yyyy")
print(paste("Date is", date2))
```

## Version history

### Ver 1.5-SNAPSHOT
override readline to allow the base R readline function to work in a GUI context
expand documentation

### Ver 1.4
add additional parameters to promptDate and promptYearMonth.
changed demo to stream output to not give the wrong impression on input availability.

### Ver 1.3
Add gui dialog interactions: 
- prompt which prompts for a text input
- chooseFile which opens a file chooser
- chooseDir which opens a directory chooser

### Ver 1.2
Format before View so that dates looks understandable

### Ver 1.1
Add display so you dont have to write inout$display()

### Ver 1.0
Hamcrest extension greaterThan and lessThan
export View so you dont have to write inout$View()
lower level image functions bridging to java: readImage, as.imageView
 
## 3:rd party software used


### org.renjin:renjin-script-engine, hamcrest
The framework this extension is for ;)

Copyright © 2010-2018 BeDataDriven Groep B.V. and contributors under GNU General Public License v2.0


### de.codecentric.centerdevice:javafxsvg
Used to allow handling of svg images. License: BSD 3-clause: https://github.com/codecentric/javafxsvg/blob/master/LICENSE.txt

Copyright (c) 2015, codecentric AG

JavaFxSVG is a simple library adding SVG support to JavaFX and thus allowing to use SVG graphics just like any other image type.

### org.slf4j
Used for logging internally.

### se.alipsa:fx-yearmonth-picker
Provides the control used for picking year months