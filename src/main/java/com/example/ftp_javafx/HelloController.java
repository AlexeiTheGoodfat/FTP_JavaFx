package com.example.ftp_javafx;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class HelloController {
    static int i = 1;

    @FXML
    private Button cargarButton;


    @FXML
    private TextField claveTextField;

    @FXML
    private Button enviarButton;

    @FXML
    private Button enviarCorreoButton;


    @FXML
    private Button mostrarButton;

    @FXML
    private TextField puertoTextField;

    @FXML
    private TextField serverTextField;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField urlTextField;

    @FXML
    private TextField usuarioTextField;
    DescargarArchivo descargarArchivo = new DescargarArchivo();
    String archivo;

    @FXML
    void enviar() {
        try {
            subirArchivo(archivo);
            textArea.appendText("Archivo subido: " + archivo + "\n");
            i++;
        } catch (NullPointerException e) {
            textArea.appendText("No se ha descargado el archivo aun" + "\n");
        }
    }

    @FXML
    void mostrar() {
        try {
            mostrarFichero(serverTextField.getText(), usuarioTextField.getText(), claveTextField.getText(), Integer.parseInt(puertoTextField.getText()));
        } catch (NullPointerException e) {
            textArea.appendText("Completa todos los campos" + "\n");
        } catch (NumberFormatException e) {
            textArea.appendText("Completa el campo puerto correctamente" + "\n");
        }
    }

    @FXML
    void descargar() {
        try {
            archivo = "archivo" + i + urlTextField.getText().substring(urlTextField.getText().lastIndexOf("."));
            descargarArchivo.descargarArchivo(new URL(urlTextField.getText()), archivo);
            textArea.appendText("Archivo descargado: " + urlTextField.getText() + "\n");
        } catch (IOException e) {
            textArea.appendText("Completa todos los campos" + "\n");
        } catch (StringIndexOutOfBoundsException e) {
            textArea.appendText("Proporciona una URL correcta" + "\n");
        }
    }

    @FXML
    void enviarCorreo() {
        final Properties prop = new Properties();
        prop.put("mail.smtp.username", "cobosneanalexei622@gmail.com");
        prop.put("mail.smtp.password", "dlll krnb qfxn frxp");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS

        // Create the Session with the user credentials
        Session mailSession = Session.getInstance(prop, new jakarta.mail.Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication(prop.getProperty("mail.smtp.username"), prop.getProperty("mail.smtp.password"));
            }
        });
        try {
            Message message = new MimeMessage(mailSession);
            // Set From and subject email properties
            message.setFrom(new InternetAddress("no-reply@gmail.com"));
            message.setSubject("Sending Mail with pure Java Mail API ");
            // Set to, cc & bcc recipients
            InternetAddress[] toEmailAddresses =
                    InternetAddress.parse("cobosneanalexei622@gmail.com, jesua.educa@gmail.com");

            message.setRecipients(Message.RecipientType.TO, toEmailAddresses);

            /* Mail body with plain Text */
            message.setText(textArea.getText());
            Transport.send(message);
        }catch (MessagingException e){
            e.printStackTrace();
        }

    }

    public void mostrarFichero(String server, String user, String pass, int puerto) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server);
            showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                textArea.appendText("Connect failed" + "\n");
                return;
            }
            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);
            if (!success) {
                textArea.appendText("Could not login to the server" + "\n");
                return;
            }
            // Lists files and directories
            FTPFile[] files1 = ftpClient.listFiles();
            // uses simpler methods
            String[] files2 = ftpClient.listNames();
            printFileDetails(files1);
            printNames(files2);
        } catch (IOException ex) {
            textArea.appendText("Oops! Something wrong happened" + "\n");
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

    private void printFileDetails(FTPFile[] files) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FTPFile file : files) {
            String details = file.getName();
            if (file.isDirectory()) {
                details = "[" + details + "]";
            }
            details += "\t\t" + file.getSize();
            details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());
            textArea.appendText(details + "\n");
        }
    }

    private void printNames(String files[]) {
        if ((files != null && files.length > 0)) {
            for (String aFile : files) {
                textArea.appendText(aFile + "\n");
            }
        }
    }

    private void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if ((replies != null && replies.length > 0)) {
            for (String aReply : replies) {
                textArea.appendText("SERVER: " + aReply + "\n");
            }
        }
    }

    //Directorio de trabajo
    public String conectarse(String sFTP, String sUser, String sPassword, int puerto) {
        FTPClient client = new FTPClient();
        String rutaVirtual = "";
        try {
            client.connect(sFTP);
            boolean login = client.login(sUser, sPassword);
            textArea.appendText("Cliente conectado" + "\n");
            rutaVirtual = (client.printWorkingDirectory());
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
        return rutaVirtual;
    }

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
            textArea.appendText("Start uploading first file" + "\n");
            boolean done = ftpClient.storeFile(archivo, inputStream);
            inputStream.close();
            long tiempofinal = System.currentTimeMillis();
            textArea.appendText("Tiempo:" + (tiempofinal - inicio) + "\n");
            if (done) {
                textArea.appendText("The first file is uploaded successfully. " + tamanyo + " bytes en " +
                        (tiempofinal - inicio) / 1000 + " b/s" + "\n");
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
            textArea.appendText("Error: " + ex.getMessage() + "\n");
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

    @FXML
    void mostrarDirectorio() {
        try {
            textArea.appendText(conectarse(serverTextField.getText(), usuarioTextField.getText(), claveTextField.getText(), Integer.parseInt(puertoTextField.getText())) + "\n");
        } catch (NullPointerException e) {
            textArea.appendText("Completa todos los campos" + "\n");
        } catch (NumberFormatException e) {
            textArea.appendText("No te has podido conectar correctamente" + "\n");
        }
    }

    @FXML
    void initialize() {
        assert cargarButton != null : "fx:id=\"cargarButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert claveTextField != null : "fx:id=\"claveTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert enviarButton != null : "fx:id=\"enviarButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert enviarCorreoButton != null : "fx:id=\"enviarCorreoButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert mostrarButton != null : "fx:id=\"mostrarButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert puertoTextField != null : "fx:id=\"puertoTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert serverTextField != null : "fx:id=\"serverTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert urlTextField != null : "fx:id=\"urlTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert usuarioTextField != null : "fx:id=\"usuarioTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
    }

}
