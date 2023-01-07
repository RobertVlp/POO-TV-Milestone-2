package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;

public final class SubscribeCommand implements Command {
    private final Platform platform;
    private final String subscribedGenre;
    private final ObjectMapper objectMapper;

    public SubscribeCommand(final String subscribedGenre, final Platform platform) {
        this.platform = platform;
        objectMapper = PlatformConstants.OBJECT_MAPPER;
        this.subscribedGenre = subscribedGenre;
    }

    @Override
    public void execute(final ObjectNode jsonObject) throws JsonProcessingException {
        platform.subscribe(subscribedGenre, jsonObject, objectMapper);
    }
}
