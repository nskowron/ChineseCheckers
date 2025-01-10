package server;

import shared.Move;
import shared.Player;
import utils.IntMap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Board implements IBoard
{
    private Map<int[], Node> nodes;

    private List<String> starColors;

    public Board(File jsonStarFile) throws IOException, StreamReadException, DatabindException
    {
        this.nodes = new IntMap<>();

        ObjectMapper jsonMapper = new ObjectMapper();
        Star star = jsonMapper.readValue(jsonStarFile, Star.class);

        this.starColors = star.starColors;

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
                node = new Node(id, colorStarting, starColors.get((starColors.indexOf(colorStarting) + 3) % 6)); // TO CHANGE THAT I NEED TO CHANGE THE JSON FILE
            }

            nodes.put(id, node);
        }


        for(Node node : nodes.values())
        {
            linkRecursive(node);
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
        int[] playedColorId;

        switch(players.size())
        {
            case 2:
                playedColorId = new int[]{0, 3};
                break;

            case 3:
                playedColorId = new int[]{0, 2, 4};
                break;

            case 4:
                playedColorId = new int[]{1, 2, 4, 5};
                break;

            case 6:
                playedColorId = new int[]{0, 1, 2, 3, 4, 5};
                break;

            default:
                throw new IllegalArgumentException("Wrong number of players");
        }

        Map<String, Player> playerColors = new HashMap<>();

        for(int i = 0; i < players.size(); ++i)
        {
            String color = starColors.get(playedColorId[i]);
            Player player = players.get(i);
            player.setColor(color);
            playerColors.put(color, player);
        }

        for(Node node : nodes.values())
        {
            String color = node.getColorTarget();///
            if(playerColors.containsKey(color))
            {
                node.place(new Piece(color, playerColors.get(color)));
            }
        }

        nodes.get(new int[]{5, 11}).place(nodes.get(new int[]{4, 12}).take());//
    }

    private void linkRecursive(Node node)
    {
        if(node.getNeighbors().size() > 0)
        {
            return;
        }

        int y = node.id[0];
        int x = node.id[1];
        List<Node> neighbors = new ArrayList<>();
        neighbors.add(findNodeById(new int[]{y-1, x-1}));
        neighbors.add(findNodeById(new int[]{y-1, x+1}));
        neighbors.add(findNodeById(new int[]{y, x-2}));
        neighbors.add(findNodeById(new int[]{y, x+2}));
        neighbors.add(findNodeById(new int[]{y+1, x-1}));
        neighbors.add(findNodeById(new int[]{y+1, x+1}));

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
