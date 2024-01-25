package com.example.ftp_javafx;

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

    public void descargarArchivo(String url, String nombre) {
        try {
            //archivo a descargar
            final String ORIGEN = url;
            String extension = url.substring(url.lastIndexOf("."));
            System.out.println(extension);
            //archivo destino
            final String DESTINO = nombre + extension;

            FileOutputStream stream = new FileOutputStream(DESTINO);

            AsyncHttpClient client = Dsl.asyncHttpClient();

            AsyncCompletionHandler<FileOutputStream> asyncHandler = new AsyncCompletionHandler<FileOutputStream>() {
                @Override
                public AsyncHandler.State onBodyPartReceived(HttpResponseBodyPart bodyPart)
                        throws Exception {
                    System.out.println("onBodyPartReceived LEN:" + bodyPart.length() + " bytes | Ultima parte: " + bodyPart.isLast());
                    //escribe en archivo parte por parte                   
                    stream.getChannel().write(bodyPart.getBodyByteBuffer());
                    return AsyncHandler.State.CONTINUE;
                }

                @Override
                public FileOutputStream onCompleted(Response response)
                        throws Exception {
                    System.out.println("Descarga completa");
                    return stream;
                }
            };

            FileOutputStream fos = client.prepareGet(ORIGEN).execute(asyncHandler).get();
            fos.close();

        } catch (InterruptedException | ExecutionException | IOException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public String extensionArchivo(String url) {
        return url.substring(url.lastIndexOf("."));
    }

}