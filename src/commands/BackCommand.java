package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.Platform;
import platform.PlatformConstants;
import platform.User;

public final class BackCommand implements Command {
    private final Platform platform;
    private final ObjectMapper objectMapper;

    public BackCommand(final Platform platform) {
        this.platform = platform;
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
            String currentPage = currentUser.getPages().pop();

            if (currentPage.equals("homepage autentificat")) {
                parseErrorOutput(jsonObject, objectMapper);
                return;
            }

            if (currentUser.getPages().isEmpty()) {
                return;
            }

            String lastAccessedPage = currentUser.getPages().pop();

            platform.setCurrentPage(lastAccessedPage);

            if (lastAccessedPage.equals("see details")) {
                parseMovieOutput(jsonObject, objectMapper,
                        platform.getSearchedMovie(), currentUser);
            } else if (lastAccessedPage.equals("movies")) {
                parseSuccessOutput(jsonObject, objectMapper, currentUser);
            }

            currentUser.getPages().push(lastAccessedPage);
        }
    }
}
