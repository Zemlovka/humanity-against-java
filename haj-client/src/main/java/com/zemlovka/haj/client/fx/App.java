package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.ws.WSActions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class App extends Application {

    public static final String CSS = Objects.requireNonNull(App.class.getResource("/com/zemlovka/haj/client/styles.css")).toExternalForm();

    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {

        primaryStage = stage;

        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("/com/zemlovka/haj/client/login.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 1200, 800);

        final KeyCombination FullScreenKeyCombo = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);

        loginScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (FullScreenKeyCombo.match(event)) {
                stage.setFullScreen(!stage.isFullScreen());
                event.consume();
            }
        });

        loginScene.getStylesheets().add(CSS);

        Font.loadFont(getClass().getResourceAsStream("/com/zemlovka/haj/client/Montserrat/static/Montserrat-Regular.ttf"), 14); // Load font collection
        //Font regularFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Regular"), 14);
        //Font boldFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Bold"), 14);
        //Font italicFont = Font.loadFont(getClass().getResourceAsStream("/YourFontCollection.ttf#Italic"), 14);


        primaryStage.setTitle("Humanity Against Java");
        primaryStage.setScene(loginScene);
        primaryStage.setMinWidth(loginScene.getWidth());
        primaryStage.setMinHeight(loginScene.getHeight());
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("/com/zemlovka/haj/client/logo.png"))));

        //primaryStage.setResizable(false);
        WSActions wsActions = new WSActions();
        LoginController loginController = loginLoader.getController();
        loginController.setWsActions(wsActions);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();

    }
}