package strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.Invoker;

public final class Context {
    private final DatabaseStrategy strategy;
    private final Invoker invoker;
    private final ObjectNode jsonObject;
    private final Command command;

    public Context(
            final DatabaseStrategy strategy,
            final Invoker invoker,
            final Command command,
            final ObjectNode jsonObject
    ) {
        this.strategy = strategy;
        this.invoker = invoker;
        this.command = command;
        this.jsonObject = jsonObject;
    }

    /**
     * Executes the specified strategy
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    public void executeStrategy() throws JsonProcessingException {
        strategy.performOperation(invoker, command, jsonObject);
    }
}
