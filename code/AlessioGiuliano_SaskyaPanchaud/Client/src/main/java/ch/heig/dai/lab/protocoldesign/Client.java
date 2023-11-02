package ch.heig.dai.lab.protocoldesign;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    final String SERVER_ADDRESS = "localhost";
    final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private void run() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            String separator = "/";
            String add = "ADD";
            String substract = "SUBSTRACT";
            String multiply = "MULTIPLY";
            String divide = "DIVIDE";
            String instructions = "Enter " + add + separator + substract + separator + multiply + separator + divide + " follow by two numers (int or float) :\n";
            System.out.println(instructions);

            Scanner input = new Scanner(System.in);
            while (input.hasNext()) {
                out.write(input.nextLine());
                out.flush();
            }

            System.out.println(in.read());

        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}