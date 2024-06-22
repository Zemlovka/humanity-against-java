package com.zemlovka.haj.client.fx;

import com.zemlovka.haj.client.fx.controllers.LoginController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;


public class App extends Application {
    private static final int DEFAULT_PORT = 8082;
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final Logger log = LoggerFactory.getLogger(App.class);

    public static final String CSS = Objects.requireNonNull(App.class.getResource("/com/zemlovka/haj/client/css/styles.css")).toExternalForm();
    private static int port;
    private static String host;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("/com/zemlovka/haj/client/fxml/login.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 1200, 800);

        final KeyCombination FullScreenKeyCombo = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.ALT_DOWN);

        loginScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (FullScreenKeyCombo.match(event)) {
                stage.setFullScreen(!stage.isFullScreen());
                event.consume();
            }
        });

        loginScene.getStylesheets().add(CSS);

        Font.loadFont(getClass().getResourceAsStream("/com/zemlovka/haj/client/Montserrat/static/Montserrat-Regular.ttf"), 14);

        stage.setTitle("Humanity Against Java");
        stage.setScene(loginScene);
        stage.setMinWidth(loginScene.getWidth());
        stage.setMinHeight(loginScene.getHeight());
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getResourceAsStream("/com/zemlovka/haj/client/logo.png"))));

        ConnectionLostNotifier connectionStatusNotifier = new ConnectionLostNotifier(stage);
        WSActions wsActions = new WSActions(connectionStatusNotifier, host, port);

        LoginController loginController = loginLoader.getController();
        loginController.setWsActions(wsActions);

        stage.show();

    }



    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        log.info("Please specify host: ");
        host = scanner.nextLine();
        if (host.isEmpty()) {
            log.info("Host not found in the user input, using default host: {}", DEFAULT_HOST);
            host = DEFAULT_HOST;
        }
        log.info("Please specify port: ");
        try {
            port = Integer.parseInt(scanner.nextLine());
        } catch (Exception e2) {
            log.info("Port not found in the user input, using default port: {}", DEFAULT_PORT );
            port = DEFAULT_PORT;
        }

        launch();
    }
}