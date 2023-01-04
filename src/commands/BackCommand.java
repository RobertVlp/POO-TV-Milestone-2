package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;
import platform.User;

import java.io.IOException;

public final class BackCommand implements Command {
    private final Platform platform;
    private final ObjectMapper objectMapper;

    public BackCommand() throws IOException {
        platform = Platform.getInstance();
        objectMapper = PlatformConstants.OBJECT_MAPPER;
    }

    @Override
    public void execute(final ObjectNode jsonObject) throws JsonProcessingException {
        User currentUser = platform.getCurrentUser();

        if (currentUser == null) {
            parseErrorOutput(jsonObject, objectMapper);
            return;
        }

        if (currentUser.getPages().isEmpty()) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            String lastAccessedPage = currentUser.getPages().pop();

            platform.setCurrentPage(lastAccessedPage);

            if (lastAccessedPage.equals("see details")) {
                parseMovieOutput(jsonObject, objectMapper,
                        platform.getSearchedMovie(), currentUser);
            } else if (lastAccessedPage.equals("movies")) {
                parseSuccessOutput(jsonObject, objectMapper, currentUser);
            }
        }
    }
}
