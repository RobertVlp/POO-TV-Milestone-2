package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.OutputParser;

public interface Command extends OutputParser {
    void execute(ObjectNode jsonObject) throws JsonProcessingException;
}
