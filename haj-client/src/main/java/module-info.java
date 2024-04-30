module com.zemlovka.jah.client {
    //IMPORTANT this shit must contain all dependencies
    requires javafx.controls;
    requires javafx.fxml;
    requires com.zemlovka.jah.utils;
    requires org.slf4j;


    opens com.zemlovka.haj.client to javafx.fxml;
    exports com.zemlovka.haj.client.fx;
    exports com.zemlovka.haj.client.ws;
    opens com.zemlovka.haj.client.fx to javafx.fxml;
}