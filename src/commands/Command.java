package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.OutputParser;

public interface Command extends OutputParser {
    /**
     * Executes the received command
     * @param jsonObject for json object, in case the command needs to write at the output
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    void execute(ObjectNode jsonObject) throws JsonProcessingException;
}
