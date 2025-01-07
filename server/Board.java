package server;

import shared.Move;
import shared.Player;
import utils.Pair;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Board implements IBoard
{
    private Map<int[], Node> nodes;

    public Board(File jsonStarFile) throws IOException, StreamReadException, DatabindException
    {
        this.nodes = new HashMap<>();

        ObjectMapper jsonMapper = new ObjectMapper();
        Star star = jsonMapper.readValue(jsonStarFile, Star.class);

        for(Star.Data data : star.starPositions)
        {
            Node node;
            int[] id = {data.coordinates.get(0), data.coordinates.get(1)};
            String colorStarting = data.color;

            if(data.color.equals("GREY"))
            {
                node = new Node(id);
            }
            else
            {
                node = new Node(id, colorStarting, star.starColors.get((star.starColors.indexOf(colorStarting) + 3) % 6)); // TO CHANGE THAT I NEED TO CHANGE THE JSON FILE
            }

            nodes.put(id, node);
        }

        for(Map.Entry<int[], Node> node : nodes.entrySet())
        {
            linkRecursive(node.getValue());
            break;
        }
    }

    @Override
    public void move(Move move) throws IllegalAccessError
    {
        Node start = findNodeById(move.startId);
        Node end = findNodeById(move.endId);

        end.place(start.getPiece());
        start.take();
    }

    @Override
    public Node findNodeById(int[] id)
    {
        return nodes.get(id);
    }

    @Override
    public Map<int[], Node> getNodes() 
    {
        return nodes;
    }

    @Override
    public void layPieces(List<Player> players) throws IllegalArgumentException
    {
        // TODO: Implement laying and assigning pieces
        // check number of players
    }

    private void linkRecursive(Node node)
    {
        if(node.getNeighbors().size() > 0)
        {
            return;
        }

        int y = node.id[0];
        int x = node.id[1];
        List<Node> neighbors = List.of(
            nodes.get(new int[]{y-1, x-1}),
            nodes.get(new int[]{y-1, x+1}),
            nodes.get(new int[]{y, x-2}),
            nodes.get(new int[]{y, x+2}),
            nodes.get(new int[]{y+1, x-1}),
            nodes.get(new int[]{y+1, x+1})
        );

        node.addNeighbors(neighbors);

        for(Node neighbor : neighbors)
        {
            if(neighbor != null)
            {
                linkRecursive(neighbor);
            }
        }
    }

    private static class Star
    {
        public List<String> starColors;
        public List<Data> starPositions;

        public static class Data
        {
            public List<Integer> coordinates;
            public String color;
        }
    }
}
