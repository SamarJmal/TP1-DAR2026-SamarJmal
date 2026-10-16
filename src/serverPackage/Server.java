package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Je suis un serveur en attente la connexion d'un client ");

            Socket socket = serverSocket.accept();
            System.out.println("un client est connecté");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                int x = in.readInt();                 // receive x
                if (x == 0) {
                    System.out.println("Le client a envoyé 0 : fin de la communication");
                    break;
                }
                int result = x * 5;                   // compute
                System.out.println("Reçu : " + x + " -> envoi : " + result);
                out.writeInt(result);                 // send result
                out.flush();
            }

            in.close();
            out.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}