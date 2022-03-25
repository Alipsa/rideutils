package se.alipsa.rideutils.grDevices;

import org.renjin.grDevices.GraphicsDevice;

/**
 * A grDevice is registered by setting it as an option in the Renjin session
 * e.g. session.getOptions().set("device", theGraphicsDevice)
 */
public class JfxDevice extends GraphicsDevice {

  @Override
  public void open(double width, double height) {

  }
}
