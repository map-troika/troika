package clientServer;
import java.io.*;
import java.net.*;

public class Client
{
    public static void main(String argv[])
    {
        BufferedReader in = null;
        PrintStream out = null;
        Socket socket = null;
        String message;
        try
        {
            // Apre una comunicazione socket
            socket = new Socket("localhost", 4000);
            // Apre i canali di comunicazione e la connessione con il  server
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream(), true);
            // Lettura di un messaggio da parte del client
            message = in.readLine();
            System.out.print("Messaggio Ricevuto : " + message);
            // Chiude i canali di comunicazione e la connessione con il server
            out.close();
            in.close();
        }
        catch(Exception e) { System.out.println(e.getMessage());}
    }
}