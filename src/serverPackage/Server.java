package serverPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final String IP_SERVEUR = "127.0.0.1"; // IPv4 réelle de la machine serveur
    static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            InetAddress adresseIp = InetAddress.getByName(IP_SERVEUR);
            InetSocketAddress adresseServeur = new InetSocketAddress(adresseIp, PORT);

            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(adresseServeur);
            System.out.println("Je suis un serveur en attente la connexion d'un client sur " + IP_SERVEUR + ":" + PORT);

            Socket socket = serverSocket.accept();
            System.out.println("un client est connecté");

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            InputStreamReader isr = new InputStreamReader(in); // convertit les octets en caractères
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(out, true);       // true = envoi immédiat (autoflush)

            String s;
            while ((s = br.readLine()) != null) {
                if (s.equalsIgnoreCase("fin")) {
                    System.out.println("Le client a mis fin à la communication.");
                    break;
                }
                // s a la forme "55 * 25"
                String[] parties = s.trim().split("\\s+");
                double a = Double.parseDouble(parties[0]);
                double b = Double.parseDouble(parties[2]);
                double resultat = a * b;

                System.out.println("Reçu : " + s + " -> résultat = " + resultat);
                pw.println(resultat);
            }

            br.close();
            pw.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}