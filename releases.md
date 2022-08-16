## Version history

### Ver 1.8.5
- upgrade dependencies: javafx -> 18.02, tika -> 2.4.1

### Ver 1.8.4 (2022-Mar-25), Java 11
change module name to `se.alipsa.rideutils`
fix the demo

### Ver 1.8.3 (2022-Mar-25), Java 11
Moved version history to its own file.
Move grDevice implementation (WIP) to the rideutils package
Upgrade to java 11, add module-info `rideutils`

### Ver 1.8.2 (2022-Jan-19), Java 8
- Add support for View of a table directly by auto converting it to a data.frame
- upgrade tika version

### Ver 1.8.1 (2021-Dec-14), Java 8
- Bump fxsvgimage version

### Ver 1.8 (2021-Nov-28), Java 8
- Add a help() function overriding the standard help which does not do anything useful
- Add an extra check to display() when just a filename without path is passed as parameter
- Add default value to promptDate
- Add promptSelect interactive function
- Change readImage to handle svg files using fxsvgimage instead of javafxsvg as the latter depends on batik
  which has problems reading many svg files.

### Ver 1.7 (2021-Jan-24), Java 8
- Add viewPlot function

### Ver 1.6 (2020-Sep-13), Java 8
- Add variable name for View if no title was supplied.

### Ver 1.5 (2020-Jul-30), Java 8
- Override `readline` from base R to work in a GUI context
- Greatly expand documentation
- Moved demo to separate module
- Create demo jar with dependencies and fixed file traversal so it also works inside a jar.

### Ver 1.4 (2020-Jul-29), Java 8
- Add additional parameters to promptDate and promptYearMonth.
- Changed demo to stream output to not give the wrong impression on input availability.

### Ver 1.3 (2020-Jul-24), Java 8
- Add gui dialog interactions:
    - prompt which prompts for a text input
    - chooseFile which opens a file chooser
    - chooseDir which opens a directory chooser

### Ver 1.2 (2020-May-27), Java 8
- Format before View so that dates looks understandable

### Ver 1.1 (2020-May-12), Java 8
- Add display so you don't have to write inout$display()

### Ver 1.0, Java 8
- Hamcrest extension greaterThan and lessThan
- Export View so you don't have to write inout$View()
- Lower level image functions bridging to java: readImage, as.imageView

## 3:rd party software used

### org.renjin:renjin-script-engine, hamcrest
The framework this extension is for ;)

Copyright © 2010-2018 BeDataDriven Groep B.V. and contributors under GNU General Public License v2.0

### de.codecentric.centerdevice:javafxsvg
Used to allow handling of svg images. License: BSD 3-clause: https://github.com/codecentric/javafxsvg/blob/master/LICENSE.txt

Copyright (c) 2015, codecentric AG

JavaFxSVG is a simple library adding SVG support to JavaFX and thus allowing to use SVG graphics just like any other image type.

### org.slf4j
Used for logging internally. Apache licensed.

### se.alipsa:fx-yearmonth-picker
Provides the control used for picking year months. MIT licensed.