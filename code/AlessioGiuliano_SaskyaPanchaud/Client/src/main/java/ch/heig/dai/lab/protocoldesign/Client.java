package ch.heig.dai.lab.protocoldesign;

public class Client {
    final String SERVER_ADDRESS = "1.2.3.4.5.6";
    final int SERVER_PORT = 123456;

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private void run() {
        // FIXME : premiere ligne ?
        try (Socket socket = new Socket("localhost", 1234);
             var in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             var out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

            // print the supported operations
            String separator = "/";
            String add = "ADD";
            String substract = "SUBSTRACT";
            String multiply = "MULTIPLY";
            String divide = "DIVIDE";
            String instructions = "Enter " + add + separator + substract + separator + multiply + separator + divide + " follow by two numers (int or float) :\n";

            // wait for the user to enter a command
            // FIXME : ???
            String input = ...

            // sent the command to the server
            out.write(contenuEntreParUtilisateur);
            System.out.println(in.readLine());

        } catch (IOException e) {
            System.out.println("Client: exception while using client socket: " + e);
        }
    }
}