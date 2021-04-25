module com.dickens {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.jfoenix;
    requires org.json;


    opens com.dickens to javafx.fxml;
    exports com.dickens;
}