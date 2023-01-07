package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;

public final class RecommendCommand implements Command {
    private final Platform platform;
    private final ObjectMapper objectMapper;

    public RecommendCommand(final Platform platform) {
        this.platform = platform;
        objectMapper = PlatformConstants.OBJECT_MAPPER;
    }

    @Override
    public void execute(final ObjectNode jsonObject) throws JsonProcessingException {
        platform.recommendMovie(jsonObject, objectMapper);
    }
}
