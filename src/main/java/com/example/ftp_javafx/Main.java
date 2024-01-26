package com.example.ftp_javafx;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class Main {
    public void conectarse() {
        FTPClient client = new FTPClient();
        String sFTP = "172.18.185.27";
        String sUser = "alexei";
        String sPassword = "psp123";
        try {
            client.connect(sFTP);
            boolean login = client.login(sUser, sPassword);
            System.out.println("Cliente conectado");
            System.out.println(client.printWorkingDirectory());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if (client.isConnected()) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
