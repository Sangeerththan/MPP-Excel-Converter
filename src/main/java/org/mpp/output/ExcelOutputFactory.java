package org.mpp.output;

import net.sf.mpxj.ProjectFile;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;

public class ExcelOutputFactory implements OutputFactory {

    @Override
    public void createOutput(ProjectFile projectFile, String outputFilePath) throws Exception {
        Workbook workbook = ExcelWorkbookFactory.createWorkbookFromProjectFile(projectFile);
        try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
            workbook.write(fileOut);
        }
    }
}