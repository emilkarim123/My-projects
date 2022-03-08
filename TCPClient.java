package tcpclient;

import java.net.*;
import java.io.*;




public class TCPClient {

    boolean myshut;
    Integer tim;
    Integer horton;

    public TCPClient(boolean shutdown, Integer timeout, Integer limit) {
         myshut = shutdown;
        tim = timeout;
        horton = limit;

    }


    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException, SocketTimeoutException {

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {

        Socket schmocket = new Socket(hostname, port);
        if (myshut) {
            schmocket.shutdownOutput();
            return toServerBytes;
        }


        schmocket.getOutputStream().write(toServerBytes, 0, toServerBytes.length);
        if(tim != null)
            schmocket.setSoTimeout(tim);
        int index;


         if(this.horton == null) {
             while ((index = schmocket.getInputStream().read(buffer)) != -1) {
                 System.out.println("Piss");
                 baos.write(buffer,0, index);
            }
         }
         else if(this.horton != null){
            while ((index = schmocket.getInputStream().read(buffer)) != -1) {
                if (horton <  index) {
                    System.out.println("Poop");
                    baos.write(buffer, 0, horton);
                    schmocket.close();
                    System.out.println(baos.size());
                    return baos.toByteArray();
                }
                else {
                    baos.write(buffer, 0, index);
                    horton = horton - index;
                }
         }

     }
        schmocket.close();

    } catch(SocketTimeoutException these_hands){
        System.err.println(these_hands);
        return baos.toByteArray();
    } catch(IOException error){
        System.err.println(error);
        System.exit(1);

    }
        return baos.toByteArray();
    }


}