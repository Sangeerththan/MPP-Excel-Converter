package org.mpp;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.mpp.MPPReader;
import java.io.File;

public class ProjectFileReaderFactory {

    public static ProjectFile readProjectFile(String inputFilePath) throws Exception {
        File inputFile = new File(inputFilePath);
        return new MPPReader().read(inputFile);
    }
}
