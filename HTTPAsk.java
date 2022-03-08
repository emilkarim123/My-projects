import tcpclient.TCPClient;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;



public class HTTPAsk {
    static int BUFFER = 1024;

    public static void main(String[] args) throws IOException
    {

        byte [] fromClient = new byte[BUFFER];
        int portNumber = Integer.parseInt(args[0]);
        ServerSocket schmocket = new ServerSocket(portNumber);
        boolean shutdown = false;
        Integer timeout = null;
        int limit = Integer.MAX_VALUE;
        String timeoutstring;
        String limitstring;
        String shutstring;


        try {

            while(true) {
                Socket conncectionschmocket = schmocket.accept();
                OutputStream opStream = conncectionschmocket.getOutputStream();
                InputStream ipStream = conncectionschmocket.getInputStream();
                int fromClientlength = ipStream.read(fromClient);

                //System.out.println(fromClient.toString());
                String clientSentence = new String(fromClient, StandardCharsets.UTF_8);
                String capd = clientSentence.toUpperCase();
                byte[] toClientBuffer = capd.getBytes(StandardCharsets.UTF_8);

                if(!clientSentence.contains("HTTP/1.1")){

                    String eerror = "HTTP/1.1 400 Bad request" + "\r\n"
                             + "\r\n" + "\r\n" + clientSentence + "\r\n" + "\r\n";
                    opStream.write(eerror.getBytes());
                    System.out.println("An error has been acheived");
                    return;

                }


                if(!clientSentence.contains("GET /ask")||!clientSentence.contains("hostname=")||!clientSentence.contains("port=")||!clientSentence.contains("HTTP")){


                    String onweb = "HTTP/1.1 404 Page Not Found" + "\r\n"  + "\r\n" + "\r\n" + clientSentence + "\r\n" + "\r\n";
                    opStream.write(onweb.getBytes());
                    System.out.println("An error has been");
                    //socket.close();

                    return;
                }

                String [] qsplit = clientSentence.split("GET /", 0);
                String [] qsplit2 = qsplit[1].split(" ", 0);


                String [] ttests = {"hostname=", "port=", "limit=", "shutdown=", "timeout=", "string="};

                String [] splidded = new String[ttests.length];
                String [] poo = new String[ttests.length];
                String [] poo2 = new String[ttests.length];
                for(int i = 0; i < ttests.length; i++){
                    if(clientSentence.contains(ttests[i])){
                        poo =  qsplit2[0].split(ttests[i], 0);
                        poo2 = poo[1].split("&", 0);
                        splidded[i] = poo2[0];
                    }
                }
               /* for(int i = 0; i < splidded.length; i++) {
                    if (splidded[i] != null)
                        System.out.println(splidded[i]);
                }*/
                int portnr = Integer.parseInt(splidded[1]);
                System.out.println(portnr);
                System.out.println(qsplit[1]);
                String string = null;
               if (qsplit[1].contains("string="))
                    string = splidded[5];
                System.out.println(string);
                if (qsplit[1].contains("timeout=")) {
                    timeoutstring = splidded[4];
                    timeout = Integer.parseInt(timeoutstring);
                }
                System.out.println(string);
                if (qsplit[1].contains("limit=")) {
                    limitstring = splidded[2];
                    limit = Integer.parseInt(limitstring);
                }
                System.out.println(limit);
                if (qsplit[1].contains("shutdown=")) {
                    shutstring = splidded[3];
                    shutdown = Boolean.parseBoolean(shutstring);
                }



                byte[] toserver;
                System.out.println(clientSentence);
                //System.out.print(splidded[0]);
                //System.out.println(splidded[1]);
                if(string == null)
                    toserver = new byte[0];
                else
                    toserver= string.trim().getBytes();

               /* for(int i = 0; i <splidded3.length; i++) {
                    System.out.println(splidded3[i]);
                }*/
                System.out.println(shutdown);
                System.out.println(timeout);
                System.out.println(limit);

                TCPClient tcp = new TCPClient(shutdown, timeout, limit);
               /* System.out.println(hostname);
                System.out.println(portnr);*/
                System.out.println(toserver);
                System.out.println(splidded[0]);
                System.out.println(portnr);

                String web= new String(tcp.askServer(splidded[0], portnr, toserver));

                System.out.println(web);

                String webr = "HTTP/1.1 200 OK" + "\r\n" + "\r\n" + web + "\r\n" + "\r\n";
                System.out.println(webr);
                opStream.write(webr.getBytes());
                System.out.println("also test4");
                System.out.println(webr);

                opStream.close();
                ipStream.close();
                conncectionschmocket.close();
                return;
            }
        } catch(IOException error){
            System.err.println(error);
        }
    }
}

//"HTTP/1.1 404 Not Found" System.out.println("Penis");

//String clientSentence = new String(fromClient, 0, fromClientlength, StandardCharsets.UTF_8);

//String capd = clientSentence.toUpperCase();
//byte[] toClientBuffer = capd.getBytes(StandardCharsets.UTF_8);


//conncectionschmocket.getOutputStream().write(toClientBuffer);

//"HTTP/1.1 404 Not Found"

//for(int i = 0; i < fromClientlength; i++){
// char fromclient = (char) fromClient[i];




// System.out.print(capd);

               /* String [] splidded = capd.split("[?]",0);
                String [] splidded2 = splidded[1].split("HT",0);
                String [] splidded3 = splidded2[0].split("&",0);

                String [] host =splidded3[0].split("=", 0);
                String [] port =splidded3[1].split("=", 0);

                //for()

                String hostname = host[1];
                int portnr = Integer.parseInt(port[1].replaceAll("\\s", ""));*/


//+ "Content-Type: text/html"