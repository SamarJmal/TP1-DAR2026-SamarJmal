package clientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    static final String IP_SERVEUR = "127.0.0.1";
    static final int PORT = 1234;

    // nombre (négatif ou décimal possible) * nombre
    static final String SYNTAXE = "-?\\d+(\\.\\d+)?\\s*\\*\\s*-?\\d+(\\.\\d+)?";

    public static void main(String[] args) {
        try {
            InetAddress adresseIp = InetAddress.getByName(IP_SERVEUR);
            InetSocketAddress adresseServeur = new InetSocketAddress(adresseIp, PORT);

            System.out.println("Je suis un client pas encore connecté…");
            Socket socket = new Socket();
            socket.connect(adresseServeur, 5000);
            System.out.println("je suis un client connecté à " + IP_SERVEUR + ":" + PORT);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            PrintWriter pw = new PrintWriter(out, true);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("Entrez une multiplication ou 'fin' : ");
                String saisie = sc.nextLine().trim();

                if (saisie.equalsIgnoreCase("fin")) {
                    pw.println("fin");
                    break;
                }

                if (!saisie.matches(SYNTAXE)) {
                    System.out.println("Syntaxe incorrecte. Format attendu : nombre * nombre");
                    continue;
                }

                pw.println(saisie);
                String resultat = br.readLine();
                System.out.println("Résultat : " + resultat);
            }

            System.out.println("Fin de la communication");
            sc.close();
            br.close();
            pw.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}