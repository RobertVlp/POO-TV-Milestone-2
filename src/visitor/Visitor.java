package visitor;

import platform.actions.Action;
import platform.User;

public interface Visitor {
    /**
     * Changes the current page to the destination page
     * @param destinationPage for the destination page
     * @return "Error" in case of error
     */
    String changePage(String destinationPage);

    /**
     * Logs in the user whose credentials are received as parameter
     * @param credentials for user's credentials
     * @return "Error" in case of error
     */
    String login(User.Credentials credentials);

    /**
     * Registers in the platform the user received as parameter
     * @param credentials for user's credentials
     * @return "Error" in case of error
     */
    String register(User.Credentials credentials);

    /**
     * Searches for the movies whose names starts with the specified characters
     * @param startsWith for characters to check for
     * @return "Error" in case of error
     */
    String search(String startsWith);

    /**
     * Filters the available movies for the current user
     * @param filters for filtering parameters
     * @return "Error" in case of error
     */
    String filter(Action.Filters filters);

    /**
     * Buys the specified amount of tokens for the current user
     * @param count for number of tokens
     * @return "Error" in case of error
     */
    String buyTokens(Integer count);

    /**
     * Buys premium account for the current user
     * @return "Error" in case of error
     */
    String buyPremiumAccount();

    /**
     * Adds the selected movie for the current user in the purchased movies list
     * @return "Error" in case of error
     */
    String purchaseMovie();

    /**
     * Adds the selected movie for the current user in the watched movies list
     * @return "Error" in case of error
     */
    String watchMovie();

    /**
     * Adds the selected movie for the current user in the liked movies list
     * @return "Error" in case of error
     */
    String likeMovie();

    /**
     * Rates the selected movie for the current user
     * @param rate for the rate
     * @return "Error" in case of error
     */
    String rateMovie(Integer rate);
}
