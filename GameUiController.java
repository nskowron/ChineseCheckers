public class GameUiController 
{
    private final GameUI gameUI;
    private final GameClient gameClient;
    private GraphicNode selectedNode = null;

    public GameUiController(GameUI gameUI, GameClient gameClient) 
    {
        this.gameUI = gameUI;
        this.gameClient = gameClient;

        // Setup interaction handler
        //this.gameUI.setNodeClickHandler(this::handleNodeClick);
    }

    public void handleNodeClick(Object source) 
    {
        if (!(source instanceof GraphicNode)) return;

        GraphicNode clickedNode = (GraphicNode) source;

        if (!isPlayerTurn()) return;

        if (selectedNode == null) 
        {
            // First click: Select starting node
            selectedNode = clickedNode;

            // Request possible moves
            Request getMovesRequest = Request.GET_MOVES;
            getMovesRequest.setData(selectedNode.getId()); // Assuming nodes have IDs
            gameClient.sendMessage(getMovesRequest);

            // Highlight possible moves (this would be handled based on server response)
            gameUI.clearAllHighlights();
            highlightPossibleMoves(clickedNode); // You would need to implement the logic for possible moves
        } 
        else if (selectedNode == clickedNode) 
        {
            // Deselect if clicked again
            selectedNode = null;
            gameUI.clearAllHighlights();
        } 
        else if (clickedNode.isHighlighted()) 
        {
            // If a highlighted node is clicked, make the move
            GraphicNode targetNode = clickedNode;
            Move move = new Move(selectedNode.getId(), targetNode.getId(), 0); // Assume playerId is 0 for now
            Request moveRequest = Request.MOVE;
            moveRequest.setData(move);
            gameClient.sendMessage(moveRequest);

            // Clear highlights after the move
            gameUI.clearAllHighlights();
            selectedNode = null;
        }
    }

    private boolean isPlayerTurn() 
    {
        return true;
    }

    private void highlightPossibleMoves(GraphicNode node) 
    {
        // Highlight all possible moves for the selected node
        // You would call gameUI.highlightNode for each possible move
    }
}
