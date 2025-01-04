import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable 
{
    private Socket clientSocket;
    private Game game;
    private Player player;

    public ClientHandler(Socket clientSocket, Game game, Player player) 
    {
        this.clientSocket = clientSocket;
        this.game = game;
        this.player = player;
    }

    public void sendBoardUpdate(IBoard board) 
    {
        try 
        {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(board);
            out.flush();
    
            // Print confirmation for debugging
            System.out.println("Board update sent to client " + clientId);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
    

    @Override
    public void run() 
    {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) 
        {

            // Send greeting with client ID
            out.writeObject(clientId);
            out.flush();

            while (true) 
            {
                String clientRequest = (String) in.readObject();
                System.out.println("Client " + clientId + " request: " + clientRequest);

                switch (clientRequest) 
                {
                    case "GET_BOARD":
                        out.writeObject(game.getBoard());
                        out.flush();
                        System.out.println("Board sent to client " + clientId + ".");
                        break;

                    case "GET_READY":
                        out.writeObject("Client " + clientId + " is now ready!");
                        out.flush();
                        System.out.println("Client " + clientId + " marked as ready.");
                        break;

                    case "GET_NOT_READY":
                        out.writeObject("Client " + clientId + " is now not ready.");
                        out.flush();
                        System.out.println("Client " + clientId + " marked as not ready.");
                        break;

                    case "SEND_MOVE":
                        Move move = (Move) in.readObject();
                        synchronized (game) 
                        {
                            System.out.println("Client " + clientId + " sent move: " + move.startId + " -> " + move.endId);

                            try 
                            {
                                game.move(move);

                                out.writeObject("Moved succesfully: " +  move.startId + " -> " + move.endId);
                                out.flush();

                                // Notify all clients about the updated board
                                CheckersServer.broadcastBoardUpdate(game.getBoard());
                            } 
                            catch (IllegalArgumentException e) 
                            {
                                e.printStackTrace();
                                out.writeObject("Error while moving: " + e.getMessage());
                                out.flush();
                            }
                        }
                        break;

                    default:
                        out.writeObject("Error: Unknown request.");
                        System.out.println("Unknown request from client " + clientId + ": " + clientRequest);
                }
            }
        } 
        catch (EOFException e) 
        {
            System.out.println("Client " + clientId + " disconnected.");
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                clientSocket.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
}
