package demo;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.renjin.script.RenjinScriptEngine;
import org.renjin.script.RenjinScriptEngineFactory;
import org.renjin.sexp.ListVector;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class InteractiveDemo extends Application {

    static Logger log = LogManager.getLogger(InteractiveDemo.class);
    Stage stage;

    public static void main(String[] args) {
        log.info("starting demo");
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        BorderPane pane = new BorderPane();
        TextArea ta = new TextArea();
        pane.setCenter(ta);

        VBox left = new VBox();
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

        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        pane.setBottom(bottom);
        Button runButton = new Button("Run");
        bottom.getChildren().add(runButton);
        runButton.setOnAction(a -> runScriptInThread(ta.getText()));
        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void runScript(String text) {
        RenjinScriptEngine engine = new RenjinScriptEngineFactory().getScriptEngine();
        try {
            engine.put("inout", this);
            engine.eval(text);
        } catch (ScriptException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Failed to execute script: " + e);
            alert.showAndWait();
        }
    }

    void runScriptInThread(String rCode) {
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    RenjinScriptEngine engine = new RenjinScriptEngineFactory().getScriptEngine();
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
            System.out.println("Done");
        });

        task.setOnFailed(e -> {
            Throwable throwable = task.getException();
            Throwable ex = throwable.getCause();
            if (ex == null) {
                ex = throwable;
            }
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING, "Failed to execute script: " + ex);
            alert.showAndWait();
        });
        Thread scriptThread = new Thread(task);
        scriptThread.setDaemon(false);
        scriptThread.start();
    }

    public Stage getStage() {
        return stage;
    }
}
