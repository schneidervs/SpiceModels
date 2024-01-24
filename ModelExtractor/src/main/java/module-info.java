module com.example.modelextractor {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.modelextractor to javafx.fxml;
    exports com.example.modelextractor;
}