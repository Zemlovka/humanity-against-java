package com.zemlovka.haj.client.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/zemlovka/haj/client/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        scene.getStylesheets().add(getClass().getResource("/com/zemlovka/haj/client/styles.css").toExternalForm());
        Font.loadFont(getClass().getResourceAsStream("/com/zemlovka/haj/client/Montserrat/static/Montserrat-Regular.ttf"), 14); // Load font collection
        //Font regularFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Regular"), 14);
        //Font boldFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Bold"), 14);
        //Font italicFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Italic"), 14);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}