import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.IOException;

public class Server {

    private final int PORT = 5000;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private int clientCounter = 0;
    private ArrayList < User > userList;

    public Server(ArrayList < User > userList) {
        this.userList = userList;
    }

    public static void main(String[] args) throws Exception {

        //Taking input for login
        ArrayList < User > userList = new ArrayList < User > ();
        File userDatabase = new File("Server Database/USER_DATABASE.txt");
        Scanner readUsers = new Scanner(userDatabase);
        while (readUsers.hasNext()) {
            String lineAt = readUsers.next();
            String[] loginComponents = lineAt.split("/");
            userList.add(new User(loginComponents[0], loginComponents[1]));
        }

        Server server = new Server(userList);
        server.go();
    }

    public void go() throws Exception {
        //create a socket with the local IP address and wait for connection request       
        System.out.println("Waiting for a connection request from a client ...");
        serverSocket = new ServerSocket(PORT); //create and bind a socket
        while (true) {
            clientSocket = serverSocket.accept(); //wait for connection request
            clientCounter = clientCounter + 1;
            System.out.println("Client " + clientCounter + " connected");
            Thread connectionThread = new Thread(new ConnectionHandler(clientSocket));
            connectionThread.start(); //start a new thread to handle the connection
        }
    }

    //------------------------------------------------------------------------------
    class ConnectionHandler extends Thread {
        private Socket socket;
        private PrintWriter output;
        private BufferedReader input;
        private String login;

        //Print to file method, returns current state
        //TODO: IMPLEMENT

        public ConnectionHandler(Socket socket) {
            this.socket = socket;
            System.out.println(userList);
        }

        public boolean authenticateUser(User currentUser) {
            //Loops through entire user database to check if login is the same
            for (int userIndex = 0; userIndex < userList.size(); userIndex++) {
                if (userList.get(userIndex).compare(currentUser)) {
                    return true;
                }
            }
            return false;
        }

        public void addUser(User currentUser) {
            userList.add(currentUser);
        }

        public void run() {
            try {
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new PrintWriter(socket.getOutputStream());
                //LOGIN HANDLER
                login = input.readLine();
                System.out.println(login);
                String[] loginComponents = login.split("/");
                User loginUser = new User(loginComponents[0], loginComponents[1]);
                if (authenticateUser(loginUser)) {
                    output.println("Authenticated!");
                    output.flush();
                } else {
                    addUser(loginUser);
                    output.println("Authenticated!");
                    output.flush();
                }
                //LISTEN TO TRADES
                //If Client has disconnected
                if (input.read() == -1 && input.readLine() == null) {
                    input.close();
                    output.close();
                    System.out.println("DISCONNECTED!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}