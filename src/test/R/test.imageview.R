library("se.alipsa:rideutils")
library("hamcrest")

test.readImage <- function() {
    import(javafx.scene.image.Image)

    # TODO: does not work, need to create a javafx mock or similar first
    #  maybe use https://github.com/TestFX/TestFX
    img <- readImage("dino.jpg")
    assertTrue(exists("img"))
    assertTrue(img$getHeight() > 0)

}