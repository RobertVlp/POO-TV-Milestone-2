package platform;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class PlatformConstants {
    private PlatformConstants() {
    }

    private static String inputFile;
    private static String outputFile;

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String getInputFile() {
        return inputFile;
    }

    public static void setInputFile(String inputFile) {
        PlatformConstants.inputFile = inputFile;
    }

    public static String getOutputFile() {
        return outputFile;
    }

    public static void setOutputFile(String outputFile) {
        PlatformConstants.outputFile = outputFile;
    }
}
