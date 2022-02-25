package se.alipsa.rideutils;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class ReadImageTest  {

  @Start
  public void start(Stage stage) {
    stage.show();
  }

  @Test
  public void testReadSvg() throws IOException {
    Image img = ReadImage.read("sinplot.svg");
    assertNotNull(img);
  }

  @Test
  public void testReadPng() throws IOException {
    Image img = ReadImage.read("plot.png");
    assertNotNull(img);
  }

  @Test
  public void testReadJpg() throws IOException {
    Image img = ReadImage.read("dino.jpg");
    assertNotNull(img);
  }
}
