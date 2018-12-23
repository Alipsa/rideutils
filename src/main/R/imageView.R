# Title     : imageView
# Objective : View various images in a JavFx Node
# Created by: Per nyfelt
# License: MIT


readImage <- function(url) {
    import(se.alipsa.rideutils.ReadImage)
    import(javafx.scene.image.Image)
    #sapply(url, function(name) ReadImage$read(name = name), USE.NAMES = FALSE)
    img <- ReadImage$read(url)
    return(img)
}


as.imageView <- function(x) {
    import(javafx.scene.image.ImageView)
    view <- ImageView$new(x)
    return(view)
}