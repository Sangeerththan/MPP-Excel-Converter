package org.mpp.plugin;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mpp.MPPReader;
import net.sf.mpxj.mpx.MPXReader;

import java.io.File;

public class ProjectFileReaderFactory {

    public static ProjectFile readProjectFile(String inputFilePath) throws Exception {
        File inputFile = new File(inputFilePath);
        String fileExtension = getFileExtension(inputFile);

        switch (fileExtension.toLowerCase()) {
            case "mpp":
                return new MPPReader().read(inputFile);
            case "mpx":
                return new MPXReader().read(inputFile);
            default:
                throw new IllegalArgumentException("Unsupported file format: " + fileExtension);
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
