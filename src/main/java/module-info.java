module se.alipsa.rideutils {
  requires java.desktop;
  requires javafx.controls;
  requires javafx.swing;
  requires se.alipsa.ymp;
  requires renjin.core;
  requires grDevices;
  requires renjin.script.engine;
  requires org.apache.tika.core;
  requires fxsvgimage;
  //requires org.girod.javafx.svgimage;
  exports se.alipsa.rideutils;
}