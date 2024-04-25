module com.zemlovka.jah.client {
    //IMPORTANT this shit must contain all dependencies
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;


    opens com.zemlovka.jah.client to javafx.fxml;
    exports com.zemlovka.jah.client.fx;
    opens com.zemlovka.jah.client.fx to javafx.fxml;
}