package strategies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import commands.Command;
import commands.Invoker;

public interface DatabaseStrategy {
    /**
     * Performs the specified operation on the database
     * @param invoker for the invoker
     * @param command for the command that will be performed
     * @param jsonObject for the json object
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    void performOperation(
            Invoker invoker,
            Command command,
            ObjectNode jsonObject
    ) throws JsonProcessingException;
}
