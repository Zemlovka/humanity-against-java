module com.zemlovka.jah.client.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zemlovka.jah.client.client to javafx.fxml;
    exports com.zemlovka.jah.client.client;
}