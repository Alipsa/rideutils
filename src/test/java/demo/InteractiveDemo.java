package demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class InteractiveDemo extends Application {

    static Logger log = LogManager.getLogger(InteractiveDemo.class);
    Stage stage;
    TextArea outputTa;

    public static void main(String[] args) {
        log.info("starting demo");
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Interactive demo");
        BorderPane pane = new BorderPane();
        TextArea ta = new TextArea();
        pane.setCenter(ta);

        VBox left = new VBox();
        left.setPadding(new Insets(5));
        left.setSpacing(5);
        pane.setLeft(left);
        left.getChildren().add(new Label("Demo files"));
        ComboBox<File> demoFiles = new ComboBox<>();
        demoFiles.setConverter(new StringConverter<File>() {
            @Override
            public String toString(File file) {
                if (file == null) return "";
                return file.getName();
            }

            @Override
            public File fromString(String s) {
                return new File(s);
            }
        });
        left.getChildren().add(demoFiles);
        URL url = getClass().getResource("/demo");
        File dir = null;
        try {
            dir = Paths.get(url.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".R")) {
                demoFiles.getItems().add(file);
            }
        }

        demoFiles.setOnAction(a -> {
            File file = demoFiles.getValue();
            ta.setText("");
            try (Stream<String> lines = Files.lines(file.toPath(), StandardCharsets.UTF_8)) {
                lines.forEach(line -> ta.appendText(line + "\n"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button runButton = new Button("Run");
        left.getChildren().add(runButton);
        runButton.setOnAction(a -> runScriptInThread(ta.getText()));

        BorderPane bottom = new BorderPane();
        pane.setBottom(bottom);
        outputTa = new TextArea();
        outputTa.setTooltip(new Tooltip("output area"));
        outputTa.setEditable(false);
        bottom.setCenter(outputTa);
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    void runScriptInThread(String rCode) {
        // just for small demo code so no need to stream output to the console
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                try (AppenderPrintWriter out = new AppenderPrintWriter();
                     PrintWriter outputWriter = new PrintWriter(out);
                ){
                    RenjinScriptEngine engine = new RenjinScriptEngineFactory().getScriptEngine();
                    engine.getSession().setStdOut(outputWriter);
                    engine.getSession().setStdErr(outputWriter);
                    engine.put("inout", InteractiveDemo.this);
                    engine.eval(rCode);
                } catch (RuntimeException e) {
                    // RuntimeExceptions (such as EvalExceptions is not caught so need to wrap all in an exception
                    // this way we can get to the original one by extracting the cause from the thrown exception
                    throw new Exception(e);
                }
                return null;
            }
        };
        task.setOnSucceeded(e -> {
            log.info("R Script executed successfully!");
        });

        task.setOnFailed(e -> {
            outputTa.appendText("\nR Script execution failed!");
            Throwable throwable = task.getException();
            Throwable ex = throwable.getCause();
            if (ex == null) {
                ex = throwable;
            }
            log.warn("R Script execution failed!", ex);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Failed to execute script: " + ex);
            alert.showAndWait();
        });
        Thread scriptThread = new Thread(task);
        scriptThread.setDaemon(false);
        scriptThread.start();
    }

    private void printResult(StringWriter out) {
        outputTa.appendText("\n" + out.toString());
    }

    // getStage() is used in the R code, this way we can simple pass
    // this as the inout object
    public Stage getStage() {
        return stage;
    }

    class AppenderPrintWriter extends Writer {

        @Override
        public void write(char[] cbuf, int off, int len) {
            Platform.runLater(() -> outputTa.appendText(new String(cbuf, off, len)));
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }
}
