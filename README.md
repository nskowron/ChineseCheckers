# Chinese Checkers:

## Dependencies
- `javaFX` - (GUI)
- `Jackson` - (Json Reading)

## Build and run project
- `make`
- `make runS` - to run server
    - `make runS` - to run BASIC variant
    - `make runS ARGS="OOC"`  - to run Order Out of Chaos variant
    - `make runS ARGS="TEST"` - to run special test variant
- `make runC` - to run client
    - `make runCR` - to run resizable client (compatibility)

## Nel:
- Responsible for:
    - Server Structure
    - Handling Clients
    - Game Logic

- Quick Description of Main Classes:
    - CheckersServer - Controlls the server, handles connections of Clients, assignes them a Thread with a handler
    - ClientHandler - Manages communication with each client, run 1:1 for client in different Thread in the Server
    - Game Classes:
        - Game - Controlls the course of the game
        - Piece - represents a piece on board
        - Node - represents a place on a board (Board is a graph of Nodes)
        - GameAssetsFactory - Factory creating diffrent game sets depending on Variants
        - Game Interfaces:
            - IBoard - Interface for Board for different game Variants
            - IMoveChecker - Interface for checking validity of Moves
            - GameAssetsBuilder - Interface that combines Board and MoveChecker w/ getters
    - RequestRunnable - Expanded Runnable Interface
    - Server Player - Holds Player Data

- Quick Description of Util Classes:
    - Condition - Boolean wrapper for Synchronization
    - IntList - Helper for List easier methods invocation (get(), equals())
    - IntMap - Helper for Map easier methods invocation (get(), equals())


## Jan:
- Responsible for:
    - GUI
    - Client communication w/ Server

- Quick Description of Main Classes:
    - BoardGridPane - Generates a grid, in Serever replaced by reading `./data/star.json`
    - CheckersClientApp - Main App that runs GUI
    - GameRequestMediator - Co-Dependent with GameUIController,  a server listener that changes the GUI depending on received Requests
    - GameUIController - Co-Dependent with GameRequestMediator, interprets what is happening on GUI and sends requests to Server using GameRequestMediator
    - GraphicNode - class representing a place on board
    - WelcomeUI - Creator of WAITING ROOM w/ basic controll functionalities
    - GameUI - Creator of GAME ROOM w/ basic controll functionalities

- Quick Description of Util Classes:
    - Pair (DEPRECATED)


### Co-responsible:
- Make this work
- Quick Description of Classes:
    - ColorTranslator - Helper to Serialize javaFX's Color
    - Request - Main request class containing data as Object: 
        - Move - Move on a board presented as BeginID and EndID
        - Player - Player information: Color and ID
        - GameState - Current board, if someone Won, and current Turn

