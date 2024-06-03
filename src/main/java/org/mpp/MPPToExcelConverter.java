package org.mpp;

import net.sf.mpxj.ProjectFile;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class MPPToExcelConverter {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: MPPToExcelConverter <project.mpp> <output.xlsx>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];

        try {
            // Load MPP file using the ProjectFileReaderFactory
            ProjectFile projectFile = ProjectFileReaderFactory.readProjectFile(inputFilePath);

            // Create a new Excel workbook using the ExcelWorkbookFactory
            Workbook workbook = ExcelWorkbookFactory.createWorkbookFromProjectFile(projectFile);

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
