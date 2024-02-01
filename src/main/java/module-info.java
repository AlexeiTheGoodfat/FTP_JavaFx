module com.example.ftp_javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.net;
    requires async.http.client;
    requires org.apache.commons.io;
    requires jakarta.mail;


    opens com.example.ftp_javafx to javafx.fxml;
    exports com.example.ftp_javafx;
}