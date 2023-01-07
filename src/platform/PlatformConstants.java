package platform;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class PlatformConstants {
    private PlatformConstants() {
    }

    private static String inputFile;
    private static String outputFile;
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final Integer MAX_MOVIE_RATING = 5;
    public static final Integer PREMIUM_TOKENS = 10;
    public static final Integer NUM_FREE_MOVIES = 15;

    public static String getInputFile() {
        return inputFile;
    }

    public static void setInputFile(final String inputFile) {
        PlatformConstants.inputFile = inputFile;
    }

    public static String getOutputFile() {
        return outputFile;
    }

    public static void setOutputFile(final String outputFile) {
        PlatformConstants.outputFile = outputFile;
    }
}
