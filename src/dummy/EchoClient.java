package dummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);

                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);     // output to the server
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream())); // input to the client from server

                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in)); // input from the console
        ) {
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {   // reads everyline from the console
                out.println(userInput);                      // sends line-by-line to the server
                System.out.println("echo: " + in.readLine()); // reads the input, which we got from the server
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

}