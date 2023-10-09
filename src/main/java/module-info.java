module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ku.cs to javafx.fxml;
    exports ku.cs;

    exports ku.cs.controllers;
    opens ku.cs.controllers to javafx.fxml;
}