package com.game.Components.CSVTools;

import com.game.Components.Tools.SimulationResults;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CSVFileWriter {

    //Delimiter used in CSV file
    private final String COMMA_DELIMITER = ",";
    private final String NEW_LINE_SEPARATOR = "\n";
    private SimulationResults results;
    //CSV file header
    private static final String FILE_HEADER = "id,firstName,lastName,gender,age";

    public void writeCsvFile(SimulationResults results, String fileName) {

//        //Create new students objects
//        this.results = results;
//
//        //Create a new list of student objects
//        List results = new ArrayList();
//        students.add(student1);
//        students.add(student2);
//        students.add(student3);
//        students.add(student4);
//        students.add(student5);
//        students.add(student6);
//
//        FileWriter fileWriter = null;
//
//        try {
//            fileWriter = new FileWriter(fileName);
//
//            //Write the CSV file header
//            fileWriter.append(FILE_HEADER.toString());
//
//            //Add a new line separator after the header
//            fileWriter.append(NEW_LINE_SEPARATOR);
//
//            //Write a new student object list to the CSV file
//            for (Student student : students) {
//                fileWriter.append(String.valueOf(student.getId()));
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(student.getFirstName());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(student.getLastName());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(student.getGender());
//                fileWriter.append(COMMA_DELIMITER);
//                fileWriter.append(String.valueOf(student.getAge()));
//                fileWriter.append(NEW_LINE_SEPARATOR);
//            }



//            System.out.println("CSV file was created successfully !!!");
//
//        } catch (Exception e) {
//            System.out.println("Error in CsvFileWriter !!!");
//            e.printStackTrace();
//        } finally {
//
//            try {
//                fileWriter.flush();
//                fileWriter.close();
//            } catch (IOException e) {
//                System.out.println("Error while flushing/closing fileWriter !!!");
//                e.printStackTrace();
//            }
//
//        }
    }
}
