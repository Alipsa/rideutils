package se.alipsa.rideutils;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import se.alipsa.ymp.YearMonthPicker;

import java.io.File;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Dialogs {

    private final Stage stage;

    /** Stage is the owner of the dialogs, typically the primary stage of the javafx application */
    public Dialogs(Stage stage) {
        this.stage = stage;
    }

    public String prompt(String title, String headerText, String message) throws InterruptedException, ExecutionException {
        FutureTask<String> task = new FutureTask<>(() -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle(title);
            dialog.setHeaderText(headerText);
            dialog.setContentText(message);
            dialog.setResizable(true);
            return dialog.showAndWait().orElse(null);
        });
        Platform.runLater(task);
        return task.get();
    }

    public String promptDate(String title, String message, String outputFormat) throws InterruptedException, ExecutionException {
        if (outputFormat == null) outputFormat = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(outputFormat);
        FutureTask<LocalDate> task = new FutureTask<>(() -> {
            Dialog<LocalDate> dialog = new Dialog<>();
            dialog.setTitle(title);
            FlowPane content = new FlowPane();
            content.setHgap(5);
            content.getChildren().add(new Label(message));
            DatePicker picker = new DatePicker();
            content.getChildren().add(picker);
            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    return picker.getValue();
                }
                return null;
            });
            dialog.setResizable(true);
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
            return dialog.showAndWait().orElse(null);
        });
        Platform.runLater(task);
        LocalDate result = task.get();
        return result == null ? null : formatter.format(result);
    }

    public String promptYearMonth(String title, String message, String from, String to, String initial,
                                  String languageTag, String monthFormat, String outputFormat)
            throws InterruptedException, ExecutionException {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputFormat);
        YearMonth fromYM = YearMonth.parse(from, inputFormatter);
        YearMonth toYM = YearMonth.parse(to, inputFormatter);
        YearMonth initialYM = YearMonth.parse(initial, inputFormatter);
        Locale locale = languageTag == null ? Locale.getDefault() : Locale.forLanguageTag(languageTag);

        FutureTask<YearMonth> task = new FutureTask<>(() -> {
            Dialog<YearMonth> dialog = new Dialog<>();
            dialog.setTitle(title);
            FlowPane content = new FlowPane();
            content.setHgap(5);
            content.getChildren().add(new Label(message));
            YearMonthPicker picker = new YearMonthPicker(fromYM, toYM, initialYM, locale, monthFormat, outputFormat);
            content.getChildren().add(picker);
            dialog.getDialogPane().setContent(content);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.setResultConverter(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    return picker.getValue();
                }
                return null;
            });
            dialog.setResizable(true);
            dialog.getDialogPane().getScene().getWindow().sizeToScene();
            return dialog.showAndWait().orElse(null);
        });
        Platform.runLater(task);
        YearMonth result = task.get();

        return result == null ? null : outputFormatter.format(result);
    }

    public File chooseFile(String title, String initialDirectory, String description, String... extensions) throws InterruptedException, ExecutionException {
        FutureTask<File> task = new FutureTask<>(() -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle(title == null ? "Select file" : title);
            if (initialDirectory != null) {
                chooser.setInitialDirectory(new File(initialDirectory));
            }
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter(description, extensions)
            );
            return chooser.showOpenDialog(stage);
        });
        Platform.runLater(task);
        return task.get();
    }

    public File chooseDir(String title, String initialDirectory) throws InterruptedException, ExecutionException  {
        FutureTask<File> task = new FutureTask<>(() -> {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setInitialDirectory(new File(initialDirectory));
            chooser.setTitle(title);
            return chooser.showDialog(stage);
        });
        Platform.runLater(task);
        return task.get();
    }
}
