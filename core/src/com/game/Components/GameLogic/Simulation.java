package com.game.Components.GameLogic;

import java.util.ArrayList;

public class Simulation implements Runnable {
   private  int n = 10;
   private ArrayList<Long> gameTimes = new ArrayList<Long>();
   private  long startTime = System.currentTimeMillis();
   private  GameManager manager;

   private int player1Win = 0;
   private int player2Win = 0;
   private boolean end= false;
    //
    public Simulation(GameManager manager){
        this.manager = manager;
    }
    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            //  SimulationResults gameResults = new SimulationResults();
            long sTime = System.currentTimeMillis();
            manager.setCurrentState(new GameState());

            int turns = 0;
            System.out.println("Game " + i);
            while (!manager.getBoard().gameOver()) {
                turns++;

                Action AiMove = manager.getGamingPlayer().applyStrategy(manager.getCurrentState());
                System.out.println(AiMove.toString());
                manager.setCurrentState(manager.getCurrentState().applyAction(AiMove));
                System.out.println("Gaming Player: " + manager.getGamingPlayer().getPlayerNo() + "  Score: " + manager.getGamingPlayer().scoreToString());

            }
            if (manager.getBoard().gameOver()) {
                //System.out.println("GAME OVER");
                //exSheet = ExcelSheet.createSheet(2, 100, 46, 54, 123465875, 234 , 245, 130, 132);
                //ExcelSheet.printSheet(exSheet);
                System.out.println(" Number of turns : " + turns);
                long eTime = System.currentTimeMillis();
                long tTime = eTime - sTime;
                gameTimes.add(tTime);
                System.out.println(tTime + " ms");
                System.out.println("The winner is: Player " + manager.getCurrentState().getWinner().getPlayerNo());
                if (manager.getCurrentState().getWinner().getPlayerNo() == 1) player1Win++;
                else player2Win++;
            }
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        long averageTime = getAverage(gameTimes);
        long minTime = getMin(gameTimes);
        long maxTime = getMax(gameTimes);
        System.out.println("Average : " + averageTime + " ms");
        System.out.println("Smallest : " + minTime + " ms");
        System.out.println("Largest : " + maxTime + " ms");
        System.out.println(" Total : " + totalTime + " ms");
        System.out.println("Player 1 won: " + player1Win + " times");
        System.out.println("Player 2 won: " + player2Win + " times");
        this.end = true;
    }
    private long getMin(ArrayList<Long> gameTimes) {
        long min=gameTimes.get(0);
        for (int i=1;i<gameTimes.size();i++){
            if(min>gameTimes.get(i)){
                min = gameTimes.get(i);
            }

        }
        return min;
    }
    private long getMax(ArrayList<Long> gameTimes) {
        long max=gameTimes.get(0);
        for (int i=1;i<gameTimes.size();i++){
            if(max<gameTimes.get(i)){
                max = gameTimes.get(i);
            }

        }
        return max;
    }

    private long getAverage(ArrayList<Long> gameTimes){
        long ave = 0;
        long sum=0;
        for (int i=0;i<gameTimes.size();i++){
            sum +=gameTimes.get(i);

        }
        ave = sum/gameTimes.size();
        return ave;
    }
}
