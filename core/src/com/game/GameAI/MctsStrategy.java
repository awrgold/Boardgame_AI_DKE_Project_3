package com.game.GameAI;


//import TreeStructure.Edge;
//import TreeStructure.Node;
//import TreeStructure.Tree;
//import com.game.Components.GameLogic.Action;
//import com.game.Components.GameLogic.GameState;
//import com.game.Components.GameLogic.GameView;
//import com.game.Components.PlayerAssets.Hand;
//import com.game.Components.PlayerAssets.Player;
//import com.game.Components.PlayerAssets.Tile;
//import com.game.Components.Tools.HexagonActor;
//import com.game.Components.Tools.Link;
//import org.codetome.hexameter.core.api.Hexagon;
//import org.codetome.hexameter.core.api.HexagonalGrid;
//import org.codetome.hexameter.core.backport.Optional;
//import rx.functions.Action1;
//
//import java.util.ArrayList;
//import java.util.Random;

import com.game.Components.GameLogic.Action;
import com.game.Components.GameLogic.GameState;

public class MctsStrategy implements Strategy {
    @Override
    public Action decideMove(GameState currentState) {
        return null;
    }
//        private Random randomGenerator = new Random();
//        private Node rootNode;
//        private double explorationConstant = Math.sqrt(2.0);
//        private double pessimisticBias;
//        private double optimisticBias;
//
//        //private boolean scoreBounds;
//        private boolean trackTime; // display thinking time used
//       // private FinalSelectionPolicy finalSelectionPolicy;
//
//       // private HeuristicFunction heuristic;
//
//        public MCTS() {
//            //random = new Random();
//        }
//
//        /**
//         * Run a UCT-MCTS simulation for a number of iterations.
//         *
//         * @param startingBoard starting board
//         * @param runs how many iterations to think
//         * @param bounds enable or disable score bounds.
//         * @return
//         */
//        public Move runMCTS(Board startingBoard, int runs, boolean bounds) {
//            scoreBounds = bounds;
//            rootNode = new Node(startingBoard);
//
//            long startTime = System.nanoTime();
//
//            for (int i = 0; i < runs; i++) {
//                select(startingBoard.duplicate(), rootNode);
//            }
//
//            long endTime = System.nanoTime();
//
//            if (this.trackTime) {
//                System.out.println("Making choice for player: " + rootNode.player);
//                System.out.println("Thinking time per move in milliseconds: " + (endTime - startTime) / 1000000);
//            }
//
//            return finalMoveSelection(rootNode);
//        }
//
//        /**
//         * This represents the select stage, or default policy, of the algorithm.
//         * Traverse down to the bottom of the tree using the selection strategy
//         * until you find an unexpanded child node. Expand it. Run a random playout.
//         * Backpropagate results of the playout.
//         *
//         * @param node
//         *            Node from which to start selection
//         * @param brd
//         * 			  Board state to work from.
//         */
//        private void select(Board currentBoard, Node currentNode){
//            // Begin tree policy. Traverse down the tree and expand. Return
//            // the new node or the deepest node it could reach. Return too
//            // a board matching the returned node.
//            Map.Entry<Board, Node> boardNodePair = treePolicy(currentBoard, currentNode);
//
//            // Run a random playout until the end of the game.
//            double[] score = playout(boardNodePair.getValue(), boardNodePair.getKey());
//
//            // Backpropagate results of playout.
//            Node n = boardNodePair.getValue();
//            n.backPropagateScore(score);
//            if (scoreBounds) {
//                n.backPropagateBounds(score);
//            }
//        }
//
//        private Map.Entry<Board, Node> treePolicy(Board b, Node node) {
//            while(!b.gameOver()) {
//                if (node.unvisitedChildren == null) {
//                    node.expandNode(b);
//                }
//
//                if (!node.unvisitedChildren.isEmpty()) {
//                    Node temp = node.unvisitedChildren.remove(random.nextInt(node.unvisitedChildren.size()));
//                    node.children.add(temp);
//                    b.makeMove(temp.move);
//                    return new AbstractMap.SimpleEntry<>(b, temp);
//                } else {
//                    ArrayList<Node> bestNodes = findChildren(node, b, optimisticBias, pessimisticBias, explorationConstant);
//
//                    if (bestNodes.size() == 0){
//                        // We have failed to find a single child to visit
//                        // from a non-terminal node, so we conclude that
//                        // all children must have been PRUNED, and that
//                        // therefore there is no reason to continue.
//                        return new AbstractMap.SimpleEntry<>(b, node);
//                    }
//                    //System.out.println("Tree Policy : there are " + bestNodes.size() + " moves");
//                    Node finalNode = bestNodes.get(random.nextInt(bestNodes.size()));
//                    node = finalNode;
//                    b.makeMove(finalNode.move);
//                }
//            }
//
//            return new AbstractMap.SimpleEntry<>(b, node);
//        }
//
//
//        /**
//         * This is the final step of the algorithm, to pick the best move to
//         * actually make.
//         *
//         * @param n
//         *            this is the node whose children are considered
//         * @return the best Move the algorithm can find
//         */
//        private Move finalMoveSelection(Node n) {
//            Node r = null;
//
//            switch (finalSelectionPolicy) {
//                case maxChild:
//                    r = maxChild(n);
//                    break;
//                case robustChild:
//                    r = robustChild(n);
//                    break;
//                default:
//                    r = robustChild(n);
//                    break;
//            }
//
//            return r.move;
//        }
//
//        /**
//         * Select the most visited child node
//         * @param n
//         * @return
//         */
//        private Node robustChild(Node n){
//            double bestValue = Double.NEGATIVE_INFINITY;
//            double tempBest;
//            ArrayList<Node> bestNodes = new ArrayList<Node>();
//
//            for (Node s : n.children) {
//                tempBest = s.games;
//                //tempBest += s.opti[n.player] * optimisticBias;
//                //tempBest += s.pess[n.player] * pessimisticBias;
//                if (tempBest > bestValue) {
//                    bestNodes.clear();
//                    bestNodes.add(s);
//                    bestValue = tempBest;
//                } else if (tempBest == bestValue) {
//                    bestNodes.add(s);
//                }
//            }
//            //System.out.println("Robust : there are " + bestNodes.size() + " moves");
//            Node finalNode = bestNodes.get(random.nextInt(bestNodes.size()));
//
//            return finalNode;
//        }
//
//        /**
//         * Select the child node with the highest score
//         * @param n
//         * @return
//         */
//        private Node maxChild(Node n){
//            double bestValue = Double.NEGATIVE_INFINITY;
//            double tempBest;
//            ArrayList<Node> bestNodes = new ArrayList<Node>();
//
//            for (Node s : n.children) {
//                tempBest = s.score[n.player];
//                if (tempBest > bestValue) {
//                    bestNodes.clear();
//                    bestNodes.add(s);
//                    bestValue = tempBest;
//                } else if (tempBest == bestValue) {
//                    bestNodes.add(s);
//                }
//            }
//            //System.out.println("Max : there are " + bestNodes.size() + " moves");
//            Node finalNode = bestNodes.get(random.nextInt(bestNodes.size()));
//
//            return finalNode;
//        }
//
//
//
//        /**
//         * Playout function for MCTS
//         *
//         * @param state
//         * @return
//         */
//        private double[] playout(Node state, Board board) {
//            ArrayList<Move> moves;
//            Move mv;
//            Board brd = board.duplicate();
//
//            // Start playing random moves until the game is over
//            while (!brd.gameOver()) {
//                moves = brd.getMoves(CallLocation.treePolicy);
//                if (brd.getCurrentPlayer() >= 0) {
//                    // make random selection normally
//                    mv = moves.get(random.nextInt(moves.size()));
//                } else {
//				/*
//				 * This situation only occurs when a move
//				 * is entirely random, for example a die
//				 * roll. We must consider the random weights
//				 * of the moves.
//				 */
//                    mv = moves.get(random.nextInt(moves.size()));
//                    //mv = getRandomMove(brd, moves);
//                }
//
//                brd.makeMove(mv);
//            }
//
//            return brd.getScore();
//        }
//
//        private Move getRandomMove(Board board, ArrayList<Move> moves) {
//            double []weights = board.getMoveWeights();
//
//            double totalWeight = 0.0d;
//            for (int i = 0; i < weights.length; i++)
//            {
//                totalWeight += weights[i];
//            }
//
//            int randomIndex = -1;
//            double random = Math.random() * totalWeight;
//            for (int i = 0; i < weights.length; ++i)
//            {
//                random -= weights[i];
//                if (random <= 0.0d)
//                {
//                    randomIndex = i;
//                    break;
//                }
//            }
//
//            return moves.get(randomIndex);
//        }
//
//        /**
//         * Produce a list of viable nodes to visit. The actual
//         * selection is done in runMCTS
//         * @param optimisticBias
//         * @param pessimisticBias
//         * @param explorationConstant
//         * @return
//         */
//        public ArrayList<Node> findChildren(Node n, Board b, double optimisticBias, double pessimisticBias, double explorationConstant){
//            double bestValue = Double.NEGATIVE_INFINITY;
//            ArrayList<Node> bestNodes = new ArrayList<Node>();
//            for (Node s : n.children) {
//                // Pruned is only ever true if a branch has been pruned
//                // from the tree and that can only happen if bounds
//                // propagation mode is enabled.
//                if (s.pruned == false) {
//                    double tempBest = s.upperConfidenceBound(explorationConstant)
//                            +optimisticBias * s.opti[n.player]
//                            +pessimisticBias * s.pess[n.player];
//
//                    if (heuristic != null){
//                        tempBest += heuristic.h(b);
//                    }
//
//                    if (tempBest > bestValue) {
//                        // If we found a better node
//                        bestNodes.clear();
//                        bestNodes.add(s);
//                        bestValue = tempBest;
//                    } else if (tempBest == bestValue) {
//                        // If we found an equal node
//                        bestNodes.add(s);
//                    }
//                }
//            }
//
//            return bestNodes;
//        }
//
//        /**
//         * Sets the exploration constant for the algorithm. You will need to find
//         * the optimal value through testing. This can have a big impact on
//         * performance. Default value is sqrt(2)
//         *
//         * @param exp
//         */
//        public void setExplorationConstant(double exp) {
//            explorationConstant = exp;
//        }
//
//        public void setMoveSelectionPolicy(FinalSelectionPolicy policy){
//            finalSelectionPolicy = policy;
//        }
//
//        public void setHeuristicFunction(HeuristicFunction h){
//            heuristic = h;
//        }
//
//        /**
//         * This is multiplied by the pessimistic bounds of any
//         * considered move during selection.
//         * @param b
//         */
//        public void setPessimisticBias(double b) {
//            pessimisticBias = b;
//        }
//
//        /**
//         * This is multiplied by the optimistic bounds of any
//         * considered move during selection.
//         * @param b
//         */
//        public void setOptimisticBias(double b) {
//            optimisticBias = b;
//        }
//
//        public void setTimeDisplay(boolean displayTime) {
//            this.trackTime = displayTime;
//        }
//

 /*
    1) create a new tree with the current state as root
    2) build the first tree level with possible moves for the gaming player...
        -for each tile find the placement with highest gain and create a new node (gain is node weigth) with that placement


    */
  /*
    1) create a new tree with the current state as root
    2) build the first tree level with possible moves for the gaming player...
        -for each tile find the placement with highest gain and create a new node (gain is node weigth) with that placement


    */

//    Tree tree;
//
//    private Action bestTilePlacement(Tile tile, GameView currentState, Player player) {
//        ArrayList<Action> possibleActions = new ArrayList<>();
//        HexagonalGrid grid = currentState.getBoard().getGrid();
//        String color;
//        if (player.lowestColors().contains(tile.getActors()[0].getHexColor())){
//            color = tile.getActors()[0].getHexColor();
//        } else if (player.lowestColors().contains(tile.getActors()[1].getHexColor())) {
//            color = tile.getActors()[1].getHexColor();
//        } else {
//            color = tile.getActors()[0].getHexColor();
//        }

//        //ITERATE ALL OVER THE CURRENT BOARD
//        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
//            @Override
//            public void call(Hexagon hexagon) {
//                //FOR EACH HEXAGON
//                if (hexagon.getSatelliteData().isPresent()) {
//                    Link hexLink = (Link) hexagon.getSatelliteData().get();
//                    HexagonActor currentHexActor = hexLink.getActor();
//                    //IF THE RELATED HEXACTOR'S COLOR IS EQAUL TO ONE IN THE TILE
//                    if (currentHexActor.getHexColor().equals(color)) {
//                        if (color.equals(tile.getActors()[0].getHexColor())) {
//                            tile.setFirst(tile.getActors()[0]);
//                        } else {
//                            tile.setFirst(tile.getActors()[1]);
//                        }
//                        //FIND ALL POSSIBLE PLACEMENTS AROUND THAT TILE
//                        Hexagon[][] possiblePlacements = new Hexagon[7][6];
//                        int c = 0;
//                        for (Object hex : grid.getNeighborsOf(hexagon)) {
//                            if (hex instanceof Hexagon) {
//                                Hexagon currentNeighbor = (Hexagon) hex;
//
//                                if (currentNeighbor.getSatelliteData().isPresent()) {
//                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
//                                    HexagonActor neighHexActor = neighLink.getActor();
//
//                                    //THE FIRST ONE IS THE FIRST PLACEMENT
//                                    if (neighHexActor.getHexColor().equals("EMPTY")) {
//                                        possiblePlacements[0][c] = currentNeighbor;
//                                        int g = 1;
//                                        //LOOKING FOR FREE NEIGHBORS
//                                        for (Object hex2 : grid.getNeighborsOf(currentNeighbor)) {
//                                            if (hex2 instanceof Hexagon) {
//                                                Hexagon currentNeighbor2 = (Hexagon) hex2;
//
//                                                if (currentNeighbor2.getSatelliteData().isPresent()) {
//                                                    Link neighLink2 = (Link) currentNeighbor2.getSatelliteData().get();
//                                                    HexagonActor neighHexActor2 = neighLink2.getActor();
//
//                                                    if (neighHexActor2.getHexColor().equals("EMPTY")) {
//                                                        possiblePlacements[g][c] = currentNeighbor2;
//                                                        g++;
//
//                                                    }
//                                                }
//                                            }
//                                        }
//                                        c++;
//                                    }
//                                }
//
//                            }
//
//                        }
//                        for (int i = 0; i < 6; i++){
//                            if (possiblePlacements[0][i] != null){
//                                for (int j = 1; j < 7; j++){
//                                    if (possiblePlacements[j][i] != null){
//                                        possibleActions.add(new Action(possiblePlacements[0][i], possiblePlacements[j][i], tile));
//                                    }
//                                }
//                            }
//                        }
//
//
//                    }
//
//
//                }
//
//            }
//
//
//        });
//
//        if (possibleActions.size() == 0){
//            possibleActions.add(randomAction(tile, grid));
//
//        }
//
//        double bestGain = 0;
//        Action bestPlacement = null;
//        for (Action a : possibleActions){
//            double gain = a.actionGain(grid);
//            if (gain >= bestGain) {
//                bestGain = gain;
//                bestPlacement = a;
//            }
//        }
//        return bestPlacement;
//    }
//
//    private Action randomAction(Tile tile, HexagonalGrid grid) {
//        //System.out.println("No good moves, doing random action");
//        Action randomAction = new Action();
//        grid.getHexagons().forEach(new Action1<Hexagon<Link>>() {
//            @Override
//            public void call(Hexagon hexagon) {
//                //FOR EACH HEXAGON
//                if (hexagon.getSatelliteData().isPresent()) {
//                    Link hexLink = (Link) hexagon.getSatelliteData().get();
//                    HexagonActor currentHexActor = hexLink.getActor();
//
//                    if (currentHexActor.getHexColor().equals("EMPTY")) {
//                        for (Object hex : grid.getNeighborsOf(hexagon)) {
//                            if (hex instanceof Hexagon) {
//                                Hexagon currentNeighbor = (Hexagon) hex;
//
//                                if (currentNeighbor.getSatelliteData().isPresent()) {
//                                    Link neighLink = (Link) currentNeighbor.getSatelliteData().get();
//                                    HexagonActor neighHexActor = neighLink.getActor();
//
//                                    //THE FIRST ONE IS THE FIRST PLACEMENT
//                                    if (neighHexActor.getHexColor().equals("EMPTY")) {
//
//                                        randomAction.setH1(hexagon);
//                                        randomAction.setH2(currentNeighbor);
//                                        randomAction.setTile(tile);
//                                    }
//                                }
//
//                            }
//
//                        }
//
//                    }
//                }
//
//            }
//
//        });
//
//        return randomAction;
//
//    }
//
//    public void buildFirstLevel(Hand hand, GameView currentState, Player player){
//
//
//        for (Tile t : hand.getPieces()){
//            Action move = bestTilePlacement(t, currentState, player);
//            //System.out.println("New node: " + move.toString() + " || Gain: " + move.actionGain(currentState.getBoard().getGrid()));
//            tree.getRoot().setChild(move);
//
//        }
//
//
//    }
//
//    public Action decideMove(GameState currentState) {
//
//        GameView root = new GameView(currentState.getCurrentBoard());
//
//        tree = new Tree(root);
//        buildFirstLevel(currentState.getGamingPlayer().getHand(), tree.getRoot().getState(), currentState.getGamingPlayer());
//
//        double maxGain = 0;
//        Action bestAction = null;
//        for (Edge edge : tree.getRoot().getChildrenEdges()){
//            if (edge.getChildNode().getWeigth() >= maxGain){
//                maxGain = edge.getChildNode().getWeigth();
//                bestAction = edge.getAction();
//            }
//        }
//
//        Hexagon h1 = null;
//        Hexagon h2 = null;
//
//
//        Optional one = currentState.getCurrentBoard().getGrid().getByCubeCoordinate(bestAction.getH1().getCubeCoordinate());
//        Optional two = currentState.getCurrentBoard().getGrid().getByCubeCoordinate(bestAction.getH2().getCubeCoordinate());
//        if (one.isPresent() && two.isPresent()){
//            h1 = (Hexagon) one.get();
//            h2 = (Hexagon) two.get();
//
//        }
//
//        Action realAction = new Action(h1, h2, bestAction.getTile());
//
//        return realAction;
//    }
}
