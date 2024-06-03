package org.mpp;

import net.sf.mpxj.ProjectFile;

public class ProjectToOutputConverter {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: MPPToOutputConverter <project.mpp|project.mpt> <output> <excel|image> [imageFormat]");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String outputType = args[2];
        String imageFormat = args.length > 3 ? args[3] : null;

        try {
            ProjectFile projectFile = ProjectFileReaderFactory.readProjectFile(inputFilePath);
            OutputFactory outputFactory;

            if ("image".equalsIgnoreCase(outputType) && imageFormat != null) {
                outputFactory = OutputFactoryProvider.getFactory(outputType, imageFormat);
            } else {
                outputFactory = OutputFactoryProvider.getFactory(outputType);
            }

            outputFactory.createOutput(projectFile, outputFilePath);
            System.out.println("Project file converted successfully.");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
