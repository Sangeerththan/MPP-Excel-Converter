package org.mpp;

import net.sf.mpxj.ProjectFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import net.sf.mpxj.Task;
import java.util.List;

public class ExcelWorkbookFactory {

    public static Workbook createWorkbookFromProjectFile(ProjectFile projectFile) {
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

        return workbook;
    }
}
