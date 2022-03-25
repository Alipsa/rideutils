package se.alipsa.rideutils;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.renjin.sexp.SEXP;

public interface GuiInteraction {

  /**
   * display an image in the Plot tab
   *
   * @param node the Node to display
   * @param title an optional title for the component displaying the node
   */
  void display(Node node, String... title);

  /**
   * display an image in the Plot tab
   *
   * @param img the Image to display
   * @param title an optional title for the component displaying the image
   */
  void display(Image img, String... title);

  /**
   * display an image in the Plot tab
   *
   * @param fileName the file name of the image to display
   * @param title an optional title for the component displaying the image
   */
  void display(String fileName, String... title);

  /**
   * display data in the Viewer tab
   *
   * @param sexp the dataframe or matrix to show
   * @param title an optional title for the component displaying the sexp
   */
  void View(SEXP sexp, String... title);

  /**
   * display html in the Viewer tab
   *
   * @param sexp a character vector or similar with the html content to view
   * @param title an optional title for the component displaying the sexp
   */
  void viewHtml(SEXP sexp, String... title);

  /**
   * display html in the Viewer tab
   *
   * @param sexp a character vector or similar with the html content to view
   * @param title an optional title for the component displaying the sexp
   */
  void viewer(SEXP sexp, String... title);

  /**
   * display html in the Help tab
   *
   * @param sexp a character vector or similar with the html content to view
   * @param title an optional title for the component displaying the sexp
   */
  void viewHelp(SEXP sexp, String... title);

  /** @return the current active script file or null if is has not been saved yet */
  String scriptFile();

  /** Allows Dialogs and similar in external packages to interact with Ride
   * @return the primary stage
   **/
  Stage getStage();
}
