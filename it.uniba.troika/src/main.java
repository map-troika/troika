import clientServer.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class main {


    public static void main(String argv[]) throws Exception {
        new Server();

        BufferedReader in = null;
        PrintStream out = null;
        Socket socket = null;
        String message;
        try {
            // open a socket connection
            socket = new Socket("localhost", 4000);

            // Apre i canali I/O
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream(), true);

            // Legge dal server
            message = in.readLine();
            System.out.print("Messaggio Ricevuto : " + message);
            out.close();
            in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
