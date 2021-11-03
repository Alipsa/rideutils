package se.alipsa.rideutils;

import javafx.scene.image.Image;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.alipsa.JfxTestRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@RunWith( JfxTestRunner.class )
public class ReadImageTest {

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
