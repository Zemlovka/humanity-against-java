package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.Player;
import com.zemlovka.haj.client.ws.LobbyWSActions;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class App extends Application {

    public static final String CSS = Objects.requireNonNull(App.class.getResource("/com/zemlovka/haj/client/styles.css")).toExternalForm();

    private Stage primaryStage;
    private Player player;

    private ObservableList<Parent> previousScenes = FXCollections.observableArrayList(); //?

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;

        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("/com/zemlovka/haj/client/login.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 900, 600);
        loginScene.getStylesheets().add(CSS);

        Font.loadFont(getClass().getResourceAsStream("/com/zemlovka/haj/client/Montserrat/static/Montserrat-Regular.ttf"), 14); // Load font collection
        Font regularFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Regular"), 14);
        Font boldFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Bold"), 14);
        Font italicFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Italic"), 14);


        primaryStage.setTitle("Humanity Against Java");
        primaryStage.setScene(loginScene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("/com/zemlovka/haj/client/logo.png"))));

        //primaryStage.setResizable(false);

        primaryStage.show();



        //LobbyWSActions wsActions = new LobbyWSActions();

        //LoginController loginController = loginLoader.getController();
        //loginController.setWsActions(wsActions);

    }

    public static void main(String[] args) {
        launch();

    }
}