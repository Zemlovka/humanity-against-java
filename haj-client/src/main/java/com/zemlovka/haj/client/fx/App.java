package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Client;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;



public class App extends Application {

    String CSS = Objects.requireNonNull(getClass().getResource("/com/zemlovka/haj/client/styles.css")).toExternalForm();

    private Stage primaryStage;


    private ObservableList<Parent> previousScenes = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {

        Client client = new Client();
        client.run(); //will it work?

        primaryStage = stage;

        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("/com/zemlovka/haj/client/login.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 900, 600);
        loginScene.getStylesheets().add(CSS);
        //loginScene.setFill(Paint.valueOf("#191D2F"));

        //FXMLLoader menuLoader = new FXMLLoader(App.class.getResource("/com/zemlovka/haj/client/login.fxml"));
        //menuScene = new Scene(menuLoader.load(), 900, 600);
        //menuScene.getStylesheets().add(CSS);

        //controllers (I haven't deleted them, because I don't know if they are needed in future)
        //LoginController loginController = loginLoader.getController();
        //loginController.setPrimaryStage(primaryStage);

        Font.loadFont(getClass().getResourceAsStream("/com/zemlovka/haj/client/Montserrat/static/Montserrat-Regular.ttf"), 14); // Load font collection
        Font regularFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Regular"), 14);
        Font boldFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Bold"), 14);
        Font italicFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Italic"), 14);


        primaryStage.setTitle("Humanity Against Java");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}