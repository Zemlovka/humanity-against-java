module com.zemlovka.jah.client {
    //IMPORTANT this shit must contain all dependencies
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires reflections;
    requires com.zemlovka.jah.utils;


    opens com.zemlovka.haj.client to javafx.fxml;
    exports com.zemlovka.haj.client.fx;
    exports com.zemlovka.haj.client.ws;
    exports com.zemlovka.haj.client.ws.commands;
    opens com.zemlovka.haj.client.fx to javafx.fxml;
}