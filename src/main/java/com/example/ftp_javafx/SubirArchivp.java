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
    public void subirArchivo(String archivo) {
        String server = "localhost";
        int port = 14148;
        String user = "alexei";
        String pass = "psp123";
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server);
            ftpClient.login(user, pass);
            //ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // APPROACH #1: uploads first file using an InputStream
            File firstLocalFile = new File(archivo);
            long inicio = System.currentTimeMillis();
            long tamanyo = firstLocalFile.length();


            InputStream inputStream = new FileInputStream(firstLocalFile);
            System.out.println("Start uploading first file");
            boolean done = ftpClient.storeFile(archivo, inputStream);
            inputStream.close();
            long tiempofinal = System.currentTimeMillis();
            System.out.println("Tiempo:" + (tiempofinal - inicio));
            if (done) {
                System.out.println("The first file is uploaded successfully. " + tamanyo + " bytes en " +
                        (tiempofinal - inicio) / 1000 + " b/s");
            }
            /*File secondLocalFile = new File(archivo);
            String secondRemoteFile = archivo;
            InputStream inputStream = new FileInputStream(secondLocalFile);
            System.out.println("Start uploading second file");
            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = inputStream.read(bytesIn)) == -1) {
                outputStream.write(bytesIn, 0, read);
            }

            inputStream.close();

            outputStream.close();

            boolean completed = ftpClient.completePendingCommand();

            if (completed) {
                System.out.println("The second file is uploaded successfully.");
            }*/
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