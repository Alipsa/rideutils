package se.alipsa.rideutils;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import se.alipsa.ymp.YearMonthPicker;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
            return dialog.showAndWait().orElse(null);
        });
        Platform.runLater(task);
        return task.get();
    }

    public String promptDate(String title, String headerText, String message) throws InterruptedException, ExecutionException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        FutureTask<LocalDate> task = new FutureTask<>(() -> {
            Dialog<LocalDate> dialog = new Dialog<>();
            dialog.setTitle(title);
            dialog.setHeaderText(headerText);
            dialog.setContentText(message);
            DatePicker picker = new DatePicker();
            dialog.getDialogPane().setContent(picker);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            return dialog.showAndWait().orElse(null);
        });
        Platform.runLater(task);
        return formatter.format(task.get());
    }

    public String promptYearMonth(String title, String headerText, String message) throws InterruptedException, ExecutionException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        FutureTask<LocalDate> task = new FutureTask<>(() -> {
            Dialog<LocalDate> dialog = new Dialog<>();
            dialog.setTitle(title);
            dialog.setHeaderText(headerText);
            dialog.setContentText(message);
            YearMonthPicker picker = new YearMonthPicker();
            dialog.getDialogPane().setContent(picker);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            return dialog.showAndWait().orElse(null);
        });
        Platform.runLater(task);
        return formatter.format(task.get());
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
