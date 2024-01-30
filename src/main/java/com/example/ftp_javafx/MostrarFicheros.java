package com.example.ftp_javafx;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


public class MostrarFicheros {
    public void mostrarFichero(String server,String user, String pass,int puerto) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Connect failed");
                return;
            }
            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
            if (!success) {
                System.out.println("Could not login to the server");
                return;
            }
            // Lists files and directories
            FTPFile[] files1 = ftpClient.listFiles();
            // uses simpler methods
            String[] files2 = ftpClient.listNames();
            printFileDetails(files1);
            printNames(files2);
        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        } finally {
            // logs out and disconnects from server
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void printFileDetails(FTPFile[] files) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FTPFile file : files) {
            String details = file.getName();
            if (file.isDirectory()) {
                details = "[" + details + "]";
            }
            details += "\t\t" + file.getSize();
            details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
            System.out.println(details);
        }
    }

    private static void printNames(String files[]) {
        if ((files != null && files.length > 0)) {
            for (String aFile : files) {
                System.out.println(aFile);
            }
        }
    }

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if ((replies != null && replies.length > 0)) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }
}
