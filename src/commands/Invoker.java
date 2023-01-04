package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class Invoker {
    /**
     * Runs the current command
     * @param command for the command that needs to be executed
     * @param jsonObject for json object, in case the current command has an output
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    public void runCommand(
            final Command command,
            final ObjectNode jsonObject
    ) throws JsonProcessingException {
        command.execute(jsonObject);
    }
}
