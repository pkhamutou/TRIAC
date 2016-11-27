package com.triac;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Created by pk on 11/26/16.
 */
public class Controller implements Initializable {

    @FXML
    MenuItem menuItemLoadFiles;

    @FXML
    BorderPane window;

    @FXML
    ListView<File> loadedImagesList;

    @FXML
    ImageView generatedImage;

    @FXML
    Button generateImage;

    private List<File> loadedFiles;

    public void loadFiles() {


        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(window.getScene().getWindow());

        System.out.println(Arrays.toString(selectedDirectory.listFiles()));

        File[] files = filterPng(selectedDirectory.listFiles());

        loadedFiles = Arrays.asList(files);

        loadedImagesList.getItems().addAll(loadedFiles);

    }

    public void generateImage() {
        int index = new Random().nextInt(5);
        System.out.println(index);
        generatedImage.setImage(new Image(loadedFiles.get(index).toURI().toString()));
    }

    private static File[] filterPng(File[] files) {
        return Arrays.stream(files).filter(f -> f.getName().endsWith(".png")).toArray(File[]::new);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //menuItemLoadFiles.setOnAction(e -> loadFiles());
    }
}
