import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import platform.PlatformConstants;
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
        PlatformConstants.setInputFile(args[0]);
        PlatformConstants.setOutputFile(args[1]);

        Platform platform = Platform.getInstance();

        ArrayNode output = PlatformConstants.OBJECT_MAPPER.createArrayNode();
        PlatformVisitor platformVisitor = new PlatformVisitor();

        ActionsWrapper actions = new ActionsWrapper(platform, platform.getActions());
        actions.performActions(platformVisitor, output);

        ObjectWriter objectWriter = PlatformConstants.OBJECT_MAPPER.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(PlatformConstants.getOutputFile()), output);
    }
}
