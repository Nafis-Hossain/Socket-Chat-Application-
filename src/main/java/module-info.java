module com.example.socketchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.socketchat to javafx.fxml;
    exports com.example.socketchat;
}