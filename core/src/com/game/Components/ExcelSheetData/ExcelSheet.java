package com.game.Components.ExcelSheetData;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;

import java.io.File;
import java.io.IOException;

import java.util.Random;

public class ExcelSheet {
/* to call:

        ExcelSheet exSheet = ExcelSheet.createSheet(2, 100, 46, 54, 123465875, 234 , 245, 130, 132);
        ExcelSheet.printSheet(exSheet);
 */

    int treeDepth;
    int gamesPlayed;
    int gamesWon1;
    double winRate1;
    int gamesWon2;
    double winRate2;

    int gameTime;
    double gameTimePerGame;

    int totalTurnsPlayed1;
    int totalTurnsPlayed2;
    static int totalTurnsPlayed;
    double totalTurnsPlayedPerGame;

    int ingenious1;
    int ingenious2;

    public int getTreeDepth() {
        return treeDepth;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon1() {
        return gamesWon1;
    }

    public double getWinRate1() {
        return winRate1;
    }

    public int getGamesWon2() {
        return gamesWon2;
    }

    public double getWinRate2() {
        return winRate2;
    }

    public int getGameTime() {
        return gameTime;
    }

    public double getGameTimePerGame() {
        return gameTimePerGame;
    }

    public int getTotalTurnsPlayed1() {
        return totalTurnsPlayed1;
    }

    public int getTotalTurnsPlayed2() {
        return totalTurnsPlayed2;
    }

    public int getTotalTurnsPlayed() {
        return totalTurnsPlayed;
    }

    public double getTotalTurnsPlayedPerGame() {
        return totalTurnsPlayedPerGame;
    }

    public int getIngenious1() {
        return ingenious1;
    }

    public int getIngenious2() {
        return ingenious2;
    }

    int[][] scoreGain;

    // Used for testing the second sheet for writing the score gain.
    public static int[][] randomScoreGain()
    {

        Random rand = new Random();
        int[][] scoreGain = new int[10][10];

        int temp = 0;

        for(int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                temp = rand.nextInt(50) + 1;
                scoreGain[i][j] = temp;
            }
        }
        return scoreGain;
    }


    public static ExcelSheet createSheet(int treeDepth, int gamesPlayed, int gamesWon1, int gamesWon2, int gameTime, int totalTurnsPlayed1, int totalTurnsPlayed2, int ingenious1, int ingenious2)
    {
        ExcelSheet sheet = new ExcelSheet();
        sheet.treeDepth = treeDepth;
        sheet.gamesPlayed = gamesPlayed;
        sheet.gamesWon1 = gamesWon1;
        sheet.gamesWon2 = gamesWon2;
        sheet.gameTime = gameTime;
        sheet.totalTurnsPlayed1 = totalTurnsPlayed1;
        sheet.totalTurnsPlayed2 = totalTurnsPlayed2;
        sheet.ingenious1 = ingenious1;
        sheet.ingenious2 = ingenious2;

        // Define possible variables to use using existing data:
        sheet.winRate1 = gamesWon1 / gamesPlayed;
        sheet.winRate2 = gamesWon2 / gamesPlayed;
        sheet.gameTimePerGame = gameTime / gamesPlayed;
        sheet.totalTurnsPlayed = totalTurnsPlayed1 + totalTurnsPlayed2;
        sheet.totalTurnsPlayedPerGame = totalTurnsPlayed / gamesPlayed;
        sheet.scoreGain = randomScoreGain();

        return sheet;
    }

    private static final String EXCEL_FILE_LOCATION = "D:\\Github\\ExcelTesting\\GameData.xls";

    public static void printSheet(ExcelSheet exSheet) {

        //1. Create an Excel file
        WritableWorkbook wWbook = null;
        try {

            wWbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION));

            // create an Excel sheet
            WritableSheet excelSheet = wWbook.createSheet("General Information", 0);

            // add something into the Excel sheet
            Label label = new Label(0, 1, "Tree depth:");
            excelSheet.addCell(label);

            Number number = new Number(1, 1, exSheet.getTreeDepth());
            excelSheet.addCell(number);

            label = new Label(0, 3, "Games played:");
            excelSheet.addCell(label);

            number = new Number(1, 3, exSheet.getGamesPlayed());
            excelSheet.addCell(number);

            label = new Label(0, 4, "Games won by player 1:");
            excelSheet.addCell(label);

            number = new Number(1, 4, exSheet.getGamesWon1());
            excelSheet.addCell(number);

            label = new Label(0, 5, "Games won by player 2:");
            excelSheet.addCell(label);

            number = new Number(1, 5, exSheet.getGamesWon2());
            excelSheet.addCell(number);

            label = new Label(0, 6, "Average time per game:");
            excelSheet.addCell(label);

            number = new Number(1, 6, exSheet.getGameTimePerGame());
            excelSheet.addCell(number);

            label = new Label(0, 7, "Average amount of moves per game:");
            excelSheet.addCell(label);

            number = new Number(1, 7, exSheet.getTotalTurnsPlayedPerGame());
            excelSheet.addCell(number);

            label = new Label(0, 8, "Average amount of Ingeniouses per game:");
            excelSheet.addCell(label);

            number = new Number(1, 8, (exSheet.getIngenious1() + exSheet.getIngenious2()) / exSheet.getGamesPlayed());
            excelSheet.addCell(number);

            // Create the second sheet with the score gains
            WritableSheet excelSheet2 = wWbook.createSheet("Score Gains", 1);
            for (int i = 0; i < exSheet.scoreGain.length; i++)
            {
                for (int j = 0; j < exSheet.scoreGain[i].length; j++) {
                    number = new Number(i, j, exSheet.scoreGain[i][j]);
                    excelSheet2.addCell(number);
                }
            }
            wWbook.write();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } finally {

            if (wWbook != null) {
                try {
                    wWbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    // Use this to count the amount of ingeniouses in each game
    private static int getIngeniousCount(boolean[] ingenious)
    {
        int count = 0;
        for (int i = 0; i < ingenious.length; i++) {
            if (ingenious[i] == true) count++;
        }
        return count;
    }

}

