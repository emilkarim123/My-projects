import java.net.*;
import java.io.*;

public class ConcHTTPAsk {
    public static void main(String[] args) throws IOException {

        int portnr = Integer.parseInt(args[0]);
        ServerSocket shreddschmocket = new ServerSocket(portnr);

        while(true) {
            MyRunnable mr = new MyRunnable(shreddschmocket.accept());
            Thread Shredd = new Thread(mr);
            Shredd.start();
        }
    }
    }

