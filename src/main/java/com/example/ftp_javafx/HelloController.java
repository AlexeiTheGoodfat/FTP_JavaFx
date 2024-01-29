package com.example.ftp_javafx;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;

public class HelloController {
    static int i=1;

    @FXML
    private Button cargarButton;

    @FXML
    private TextField claveTextField;

    @FXML
    private Button enviarButton;

    @FXML
    private Button mostrarButton;

    @FXML
    private TextField puertoTextField;

    @FXML
    private Button loginButton;

    @FXML
    private TextField serverTextField;

    @FXML
    private TextArea textArea;

    @FXML
    private TextField urlTextField;

    @FXML
    private TextField usuarioTextField;
    SubirArchivp subirArchivp=new SubirArchivp();
    DescargarArchivo descargarArchivo=new DescargarArchivo();
    Main main=new Main();
    MostrarFicheros mostrarFicheros=new MostrarFicheros();
    String archivo;
    @FXML
    void enviar() {
        subirArchivp.subirArchivo(archivo);
        i++;
    }
    @FXML
    void mostrar() {
        mostrarFicheros.mostrarFichero(serverTextField.getText(),usuarioTextField.getText(),claveTextField.getText(),Integer.parseInt(puertoTextField.getText()));
    }
    @FXML
    void descargar() {
        try {
            archivo="archivo"+i+urlTextField.getText().substring(urlTextField.getText().lastIndexOf("."));
            descargarArchivo.descargarArchivo(new URL(urlTextField.getText()),archivo);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void conectar() {
        main.conectarse(serverTextField.getText(),usuarioTextField.getText(),claveTextField.getText(),Integer.parseInt(puertoTextField.getText()));
    }

    @FXML
    void initialize() {
        assert cargarButton != null : "fx:id=\"cargarButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert claveTextField != null : "fx:id=\"claveTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert enviarButton != null : "fx:id=\"enviarButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert mostrarButton != null : "fx:id=\"mostrarButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert puertoTextField != null : "fx:id=\"puertoTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert serverTextField != null : "fx:id=\"serverTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert textArea != null : "fx:id=\"textArea\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert urlTextField != null : "fx:id=\"urlTextField\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert usuarioTextField != null : "fx:id=\"usuarioTextField\" was not injected: check your FXML file 'hello-view.fxml'.";

    }

}
