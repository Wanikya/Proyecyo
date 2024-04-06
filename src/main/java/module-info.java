module com.example.proyecyo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires commons.math3;

    opens com.example.proyecyo to javafx.fxml;
    exports com.example.proyecyo;
}