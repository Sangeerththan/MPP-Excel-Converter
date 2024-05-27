package org.example;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MPPToExcelConverter {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: MPPToExcelConverter <project.mpp> <output.xlsx>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            // Load MPP file using MPXJ
            System.out.println(inputFilePath);
            File inputFile = new File(inputFilePath);
            ProjectFile projectFile = new MPPReader().read(inputFile);

            // Create a new Excel workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Project Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("id");
            headerRow.createCell(1).setCellValue("name");
            headerRow.createCell(2).setCellValue("start");
            headerRow.createCell(3).setCellValue("end");
            headerRow.createCell(4).setCellValue("type");
            // Get tasks
            List<Task> tasks = projectFile.getTasks();
            int rowIndex = 1;

            // Iterate through the tasks and write to Excel
            for (Task task : tasks) {
                if (task.getID() != null) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(task.getID());
                    row.createCell(1).setCellValue(task.getName());
                    row.createCell(2).setCellValue(task.getStart() != null ? task.getStart().toString() : "");
                    row.createCell(3).setCellValue(task.getFinish() != null ? task.getFinish().toString() : "");
                    row.createCell(4).setCellValue(task.getType() != null ? task.getType().toString() : "");
                }
            }

            // Write the Excel file
            try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
                workbook.write(fileOut);
            }

            System.out.println("MPP file converted to Excel successfully.");

        } catch (IOException e) {
            System.err.println("Error processing file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error reading MPP file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
