package se.alipsa.rideutils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlUtil {

  public static boolean exists(String urlString, int timeout) {
    try {
      URL url = new URL(urlString);
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      HttpURLConnection.setFollowRedirects(false);
      con.setRequestMethod("HEAD");
      con.setConnectTimeout(timeout);
      int responseCode = con.getResponseCode();
      return responseCode == 200;
    } catch (RuntimeException | IOException e) {
      return false;
    }
  }
}
