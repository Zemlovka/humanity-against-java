module com.zemlovka.haj.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires reflections;
    requires com.zemlovka.haj.utils;


    opens com.zemlovka.haj.client to javafx.fxml;
    exports com.zemlovka.haj.client.fx;
    exports com.zemlovka.haj.client.ws;
    exports com.zemlovka.haj.client.ws.commands;
    opens com.zemlovka.haj.client.fx to javafx.fxml;
    exports com.zemlovka.haj.client.ws.entities;
    exports com.zemlovka.haj.client.fx.controllers;
    opens com.zemlovka.haj.client.fx.controllers to javafx.fxml;
    exports com.zemlovka.haj.client.fx.notificationService;
    opens com.zemlovka.haj.client.fx.notificationService to javafx.fxml;
}