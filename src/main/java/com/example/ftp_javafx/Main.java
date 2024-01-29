package com.example.ftp_javafx;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class Main {
    public void conectarse(String sFTP,String sUser, String sPassword,int puerto) {
        FTPClient client = new FTPClient();
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
