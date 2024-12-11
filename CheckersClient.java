import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CheckersClient 
{

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) 
    {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) 
        {
            System.out.println("Connected to server!");

            final int playerId;

            try 
            {
                playerId = (Integer) in.readObject();
                System.out.println("Received Id from server: "+playerId);
            } 
            catch (Exception e) 
            {
                System.out.println("Id assigment failed, Error: " + e.getMessage());
                return;
            }

            // Initialize CLI
            CLI cli = new CLI();

            // Add CLI commands
            cli.addCommand("request board", new Executable()
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        out.writeObject("GET_BOARD");
                        out.flush();
                        System.out.println("Request sent to server: GET_BOARD");

                        Board board = (Board) in.readObject();
                        System.out.println("Received board from server:");
                        System.out.println(board);
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                @Override
                public String getDescription() 
                {
                    return "Request the current board state from the server.";
                }
            });

            cli.addCommand("ready", new Executable() 
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        out.writeObject("GET_READY");
                        out.flush();
                        System.out.println("Request sent to server: GET_READY");

                        String response = (String) in.readObject();
                        System.out.println("Server response: " + response);
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                @Override
                public String getDescription() 
                {
                    return "Notify server that the player is ready.";
                }
            });

            cli.addCommand("Nready", new Executable() 
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        out.writeObject("GET_NOT_READY");
                        out.flush();
                        System.out.println("Request sent to server: GET_NOT_READY");

                        String response = (String) in.readObject();
                        System.out.println("Server response: " + response);
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                @Override
                public String getDescription() 
                {
                    return "Notify server that the player is not ready.";
                }
            });

            cli.addCommand("send move", new Executable() 
            {
                @Override
                public void run() 
                {
                    try 
                    {
                        System.out.println("Enter move beginId: ");
                        int moveBegin = Integer.parseInt(scanner.nextLine().trim()); // Parse to int

                        System.out.println("Enter move endId: ");
                        int moveEnd = Integer.parseInt(scanner.nextLine().trim()); // Parse to int

                        out.writeObject("SEND_MOVE");
                        out.writeObject(new Move(moveBegin, moveEnd, playerId));
                        out.flush();
                        System.out.println("Move sent to server: " + moveBegin + " -> " + moveEnd);

                        //String response = (String) in.readObject();
                        //System.out.println("Server response: " + response);
                    } 
                    catch (Exception e) 
                    {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                @Override
                public String getDescription() 
                {
                    return "Send a move to the server (requires two arguments).";
                }
            });

            cli.run(scanner);

        } 
        catch (IOException e) 
        {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}
