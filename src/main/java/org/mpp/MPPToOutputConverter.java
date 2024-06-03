package org.mpp;

import net.sf.mpxj.ProjectFile;

public class MPPToOutputConverter {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: MPPToOutputConverter <project.mpp|project.mpt> <output> <excel|image>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String outputType = args[2];

        try {
            ProjectFile projectFile = ProjectFileReaderFactory.readProjectFile(inputFilePath);
            OutputFactory outputFactory = OutputFactoryProvider.getFactory(outputType);
            outputFactory.createOutput(projectFile, outputFilePath);

            System.out.println("Project file converted successfully.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
