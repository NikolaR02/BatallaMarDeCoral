module com.example.batallajuan {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.BatallaMarDeCoral to javafx.fxml;
    exports com.example.BatallaMarDeCoral;
}