import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import platform.actions.ActionsWrapper;
import platform.Platform;
import visitor.PlatformVisitor;

import java.io.File;
import java.io.IOException;

public final class Main {
    /**
     * Method called by the checker
     * @param args input arguments
     * @throws IOException exception for reading/writing
     */
    public static void main(final String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Platform platform = objectMapper.readValue(new File(args[0]), Platform.class);

        ArrayNode output = objectMapper.createArrayNode();
        PlatformVisitor platformVisitor = new PlatformVisitor(platform);

        ActionsWrapper actions = new ActionsWrapper(platform, platform.getActions());
        actions.performActions(platformVisitor, objectMapper, output);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(args[1]), output);
    }
}
