package com.game;


import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
public class Pieces {
    //Creates the "bag" containing all the different pieces
    public static ArrayList<String> createBagPieces() {
        ArrayList<String> allPieces = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            allPieces.add("BB");
            allPieces.add("RR");
            allPieces.add("YY");
            allPieces.add("PP");
            allPieces.add("OO");
            allPieces.add("GG");
        }
        for(int i = 0; i < 6; i++) {
            allPieces.add("BR");
            allPieces.add("BY");
            allPieces.add("BP");
            allPieces.add("BO");
            allPieces.add("BG");
            allPieces.add("RY");
            allPieces.add("RP");
            allPieces.add("RO");
            allPieces.add("RG");
            allPieces.add("YP");
            allPieces.add("YO");
            allPieces.add("YG");
            allPieces.add("PO");
            allPieces.add("PG");
            allPieces.add("OG");
        }
        return allPieces;
    }
    //Distributes 6 pieces to a player at the start of the game
    public static ArrayList<String> distributePieces(ArrayList<String> allPieces) {
        ArrayList<String> playerPieces = new ArrayList<>();
        for(int i = 0; i < 6; i++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, allPieces.size());
            String piece = allPieces.get(randomNumber);
            playerPieces.add(piece);
            allPieces.remove(piece);
        }
        return playerPieces;
    }
    //Fills the amount of pieces of a player back up to 6
    public static ArrayList<String> takePiece(ArrayList<String> allPieces, ArrayList<String> playerPieces){
        if(playerPieces.size() < 6){
            for(int i = playerPieces.size(); i < 6; i++ ){
                int randomNumber = ThreadLocalRandom.current().nextInt(0, allPieces.size());
                String piece = allPieces.get(randomNumber);
                playerPieces.add(piece);
                allPieces.remove(piece);
            }
        }
        return playerPieces;
    }
    //Discards all the pieces in the players hand and distributes new ones to the player
    public static ArrayList<String> discardPieces(ArrayList<String> allPieces, ArrayList<String> playerPieces){
        for(int i = 0; i < 6; i++){
            String piece = allPieces.get(i);
            allPieces.add(piece);
            playerPieces.remove(piece);
        }
        playerPieces = distributePieces(allPieces);
        return playerPieces;
    }
}
