package com.example.ftp_javafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class SubirArchivp {
    public void subirArchivo() {
        String server = "172.18.185.27";
        int port = 21;
        String user = "alexei";
        String pass = "psp123";
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            //ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File("archivo_descargado.jpg");
            long inicio = System.currentTimeMillis();
            long tamanyo = firstLocalFile.length();


            String firstRemoteFile = "Aules.png";
            InputStream inputStream = new FileInputStream(firstLocalFile);
            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(firstRemoteFile, inputStream);
            inputStream.close();
            long tiempofinal = System.currentTimeMillis();
            System.out.println("Tiempo:" + (tiempofinal - inicio));
            if (done) {
                System.out.println("The first file is uploaded successfully. " + tamanyo + " bytes en " +
                        (tiempofinal - inicio) / 1000 + " b/s");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
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
}