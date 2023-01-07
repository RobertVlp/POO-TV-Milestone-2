package strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.Invoker;

public final class Back implements DatabaseStrategy {
    @Override
    public void performOperation(
            final Invoker invoker,
            final Command command,
            final ObjectNode jsonObject
    ) throws JsonProcessingException {
        invoker.runCommand(command, jsonObject);
    }
}
