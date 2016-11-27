package com.triac;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by pk on 11/26/16.
 */
public class Controller implements Initializable {

    @FXML
    VBox window; // the main window

    @FXML
    ListView<File> imageList; // the list of loaded images

    @FXML
    ImageView generatedImage; // the generated image

    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;

    public void loadImages() {
        fileChooser.setTitle("Select file(s)");
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(window.getScene().getWindow());
        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            imageList.getItems().addAll(selectedFiles);
        }

    }

    public void loadDir() {
        directoryChooser.setTitle("Choose a dir with source images");
        File selectedDirectory = directoryChooser.showDialog(window.getScene().getWindow());
        if (selectedDirectory != null) {
            imageList.getItems().addAll(filterPng(selectedDirectory.listFiles()));
        }
    }

    public void generateImage() {

        ObservableList<File> list = imageList.getItems();

        if (!list.isEmpty()) {
            //pseudo code
            int index = new Random().nextInt(list.size());
            generatedImage.setImage(new Image(list.get(index).toURI().toString()));
        }
    }

    public void processImage() {

        if (generatedImage.getImage() != null) {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("OMG, IT WORKS");
            stage.setMinWidth(250);

            VBox vBox = new VBox();
            vBox.getChildren().add(new Label("Well, this is just a popup"));
            vBox.setAlignment(Pos.CENTER);
            Scene scene = new Scene(vBox);

            stage.setScene(scene);
            stage.showAndWait();
        }

    }

    private static File[] filterPng(File[] files) {
        return Arrays.stream(files).filter(f -> f.getName().endsWith(".png")).toArray(File[]::new);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        fileChooser = new FileChooser();
        directoryChooser = new DirectoryChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));

        imageList.setCellFactory(v -> new ImageCell());

    }

    static private class ImageCell extends ListCell<File> {
        @Override
        protected void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                String path = item.toURI().toString();
                setText(path);
                Image image = new Image(path, 40, 40, true, true);
                setGraphic(new ImageView(image));
            }

        }
    }
}
