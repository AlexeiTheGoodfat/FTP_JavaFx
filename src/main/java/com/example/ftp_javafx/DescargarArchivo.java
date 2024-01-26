package com.example.ftp_javafx;

import java.io.File;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.asynchttpclient.Dsl;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.Response;


public class DescargarArchivo {

    public void descargarArchivo(URL url, String nombre) throws IOException {
        FileUtils.copyURLToFile(url, new File(nombre));
        System.out.println("ESTO VA");
    }


}