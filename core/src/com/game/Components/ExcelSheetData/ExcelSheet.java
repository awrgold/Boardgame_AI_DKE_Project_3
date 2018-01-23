package com.game.Components.ExcelSheetData;

import jxl.*;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;

import java.util.Locale;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExcelSheet {
    private String EXCEL_FILE_LOCATION = "D:\\GDXProjects\\Project-2.1-v2\\core\\assets\\DataSheets";
    private String filename = "GamesData.xls";
    private ArrayList<Long> turnTimes = new ArrayList<Long>();
    private ArrayList<Integer> turnScores = new ArrayList<Integer>();
    private String title;
    private String gn;
    private int turns;

    public ExcelSheet(String gn, String test, int turns, ArrayList<Integer> turnScores, ArrayList<Long> turnTimes){
this.turns=turns;
this.title=test;
this.gn = gn;
this.turnTimes = turnTimes;
this.turnScores = turnScores;

    }


    public void printSheet() {




            try {

                //create a new workbook to write
                WritableWorkbook workbook = Workbook.createWorkbook(new File(EXCEL_FILE_LOCATION,filename));
                //add a new sheet to the work book
                WritableSheet sheet = workbook.createSheet(title, 0);
                //add test Id to col 0 row 0
                Label gamelabel = new Label(0, 0, gn);


                //add to the cells
                sheet.addCell(gamelabel);

               for(int i=0; i<=turns;i++){
                 Number timenum = new Number(0,i+1,turnTimes.get(i));
                 sheet.addCell(timenum);
               }

                // All sheets and cells added. Now write out the workbook
                workbook.write();
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RowsExceededException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }

    public void setTimes(ArrayList<Long> times) {
        this.turnTimes = times;
    }

    public void setScores(ArrayList<Integer> scores) {
        this.turnScores = scores;
    }

    public void setTurns(int turns) {
        this.turns = turns;
    }

    public void setTitle(String title) {
        this.title = title;
    System.out.println("i fly away");
    }

    public void setgn(String gn) {
        this.gn = gn;
    }
}

//}

