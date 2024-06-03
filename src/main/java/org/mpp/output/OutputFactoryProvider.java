package org.mpp.output;

public class OutputFactoryProvider {
    public static OutputFactory getFactory(String type) {
        if ("excel".equalsIgnoreCase(type)) {
            return new ExcelOutputFactory();
        } else if ("image".equalsIgnoreCase(type)) {
            throw new IllegalArgumentException("Image format must be specified separately");
        }
        throw new IllegalArgumentException("Unknown output type: " + type);
    }

}