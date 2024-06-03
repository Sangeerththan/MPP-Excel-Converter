package org.mpp;

import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.transcoder.*;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.io.*;
import java.util.List;

public class ImageOutputFactory implements OutputFactory {

    private String outputFormat;

    public ImageOutputFactory(String outputFormat) {
        this.outputFormat = outputFormat.toLowerCase();
    }

    @Override
    public void createOutput(ProjectFile projectFile, String outputFilePath) throws Exception {
        // Generate SVG
        DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();
        String svgNS = "http://www.w3.org/2000/svg";
        Document document = domImpl.createDocument(svgNS, "svg", null);
        SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

        List<Task> tasks = projectFile.getTasks();
        int y = 20;
        for (Task task : tasks) {
            if (task.getID() != null) {
                svgGenerator.drawString(task.getName(), 10, y);
                y += 20;
            }
        }

        // Convert SVG to desired image format
        File tempSvgFile = File.createTempFile("project", ".svg");
        try (Writer out = new FileWriter(tempSvgFile)) {
            svgGenerator.stream(out);
        }

        TranscoderInput input = new TranscoderInput(new FileInputStream(tempSvgFile));

        OutputStream ostream = new FileOutputStream(outputFilePath);
        TranscoderOutput output = new TranscoderOutput(ostream);

        Transcoder transcoder;
        if ("png".equals(outputFormat)) {
            transcoder = new PNGTranscoder();
        } else if ("jpg".equals(outputFormat) || "jpeg".equals(outputFormat)) {
            transcoder = new JPEGTranscoder();
            ((JPEGTranscoder) transcoder).addTranscodingHint(JPEGTranscoder.KEY_QUALITY, 0.8f);
        } else {
            throw new IllegalArgumentException("Unsupported image format: " + outputFormat);
        }

        transcoder.transcode(input, output);
        ostream.close();
        tempSvgFile.delete();
    }
}
