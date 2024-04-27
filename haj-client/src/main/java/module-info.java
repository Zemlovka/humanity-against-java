module com.zemlovka.jah.client {
    //IMPORTANT this shit must contain all dependencies
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;


    opens com.zemlovka.haj.client to javafx.fxml;
    exports com.zemlovka.haj.client.fx;
    opens com.zemlovka.haj.client.fx to javafx.fxml;
}