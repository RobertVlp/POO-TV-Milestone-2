package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Invoker {
    public void runCommand(
            final Command command,
            final ObjectNode jsonObject
    ) throws JsonProcessingException {
        command.execute(jsonObject);
    }
}
