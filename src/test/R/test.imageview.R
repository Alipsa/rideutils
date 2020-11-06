library("se.alipsa:rideutils")
library("hamcrest")

# mock the inout class with an R6 class
Inout <- setRefClass(
  Class = "Inout",
  methods = list(
      display = function(...) {
         # do nothing
      }
  )
)
inout <- Inout$new()

test.readImage <- function() {
    import(javafx.scene.image.Image)
    # TODO: does not work, need to create a javafx mock or similar first
    #  maybe use https://github.com/TestFX/TestFX
    #img <- readImage("dino.jpg")
    #assertTrue(exists("img"))
    #assertTrue(img$getHeight() > 0)
}

test.plot <- function() {
  irisFile <- plotPng(
    hist(iris$Sepal.Width),
    "sepal widths"
  )
  #print(paste("Created plot of iris sepal width here", irisFile))
  assertTrue(file.exists(irisFile))
  file.remove(irisFile)
}