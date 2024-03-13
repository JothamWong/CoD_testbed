package CoD;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Simple class that starts a server on port 9000 and writes to the socket at
 * random intervals, and random text in a stream fashion.
 * To be consumed by the Stream Job.
 * Keeps a simple in-memory calculation to verify correctness of results at the end
 * of the job.
 */
public class DataStreamGenerator {
    // write to port 9000
    private static int port = 9000;
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        System.out.println("Connecting...");
        try {
            serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            System.out.println("Connected to socket!");
            Generator generator = new Generator(socket);
            generator.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Generator extends Thread {

        private static final int minWords = 1;
        private static final int maxWords = 5;
        private static final int minLength = 5;
        private static final int maxLength = 10;
        private static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private final Random random;
        private PrintWriter outputWriter;

        public Generator(Socket socket) {
            this.random = new Random(42069);
            try {
                outputWriter = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String generateWords() {
            StringBuilder sb = new StringBuilder();
            int numWords = random.nextInt(maxWords - minWords) + minWords;
            for (int currentWord = 0; currentWord < numWords; currentWord++) {
                int currentWordLength = random.nextInt(maxLength - minLength) + minLength;
                for (int currentChar = 0; currentChar < currentWordLength; currentChar++) {
                    int randomIndex = random.nextInt(characters.length());
                    char randomChar = characters.charAt(randomIndex);
                    sb.append(randomChar);
                }
                sb.append(" ");
            }
            return sb.toString();
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String generatedWords = generateWords();
                    System.out.println("Generated " + generatedWords);
                    outputWriter.println(generatedWords);
                    // Sleep anywhere between 0 and 9000 milliseconds
                    Thread.sleep((long) Math.random() * 9000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
