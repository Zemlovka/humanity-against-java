module com.zemlovka.jah.client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.zemlovka.jah.client to javafx.fxml;
    exports com.zemlovka.jah.client;
}