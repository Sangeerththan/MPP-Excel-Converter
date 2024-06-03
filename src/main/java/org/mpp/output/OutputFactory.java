package org.mpp.output;

import net.sf.mpxj.ProjectFile;

public interface OutputFactory {
    void createOutput(ProjectFile projectFile, String outputFilePath) throws Exception;
}
