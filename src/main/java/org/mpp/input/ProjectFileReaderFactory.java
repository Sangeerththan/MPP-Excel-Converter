package org.mpp.input;

import net.sf.mpxj.ProjectFile;

import net.sf.mpxj.reader.UniversalProjectReader;

import java.io.File;

public class ProjectFileReaderFactory {

    public static ProjectFile readProjectFile(String inputFilePath) throws Exception {
        File inputFile = new File(inputFilePath);
        return new UniversalProjectReader().read(inputFile);
    }
}
