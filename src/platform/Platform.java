package platform;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import platform.actions.Action;
import platform.movies.Movie;
import platform.observer.Observable;
import platform.observer.Observer;
import platform.visitor.Visitable;
import platform.visitor.Visitor;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public final class Platform implements Visitable, Observable {
    private ArrayList<User> users;
    private ArrayList<Movie> movies;
    private ArrayList<Action> actions;
    private String currentPage;
    private User currentUser;
    private Movie searchedMovie;
    private final ArrayList<Observer> observers;
    private String performedAction;

    public Platform() {
        setCurrentPage("homepage neautentificat");
        observers = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<Action> actions) {
        this.actions = actions;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    public Movie getSearchedMovie() {
        return searchedMovie;
    }

    @Override
    public void acceptChangePage(
            final Visitor visitor,
            final Action action,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.changePage(action.getPage());

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
            return;
        } else if (action.getPage().equals("movies")) {
            updateAvailableMovies();
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        } else if (action.getPage().equals("see details")) {
            searchedMovie = null;

            for (Movie movie : currentUser.getAvailableMovies()) {
                if (movie.getName().equals(action.getMovie())) {
                    searchedMovie = movie;
                    break;
                }
            }

            if (searchedMovie != null) {
                parseMovieOutput(jsonObject, objectMapper, searchedMovie, currentUser);
            } else {
                parseErrorOutput(jsonObject, objectMapper);
                setCurrentPage("movies");
                return;
            }
        }

        if (currentUser != null) {
            currentUser.getPages().push(currentPage);
        }

        if (action.getPage().equals("logout")) {
            assert currentUser != null;
            currentUser.getPages().clear();
            setCurrentUser(null);
            setCurrentPage("homepage neautentificat");
        }
    }

    @Override
    public void acceptLogin(
            final Visitor visitor,
            final User.Credentials credentials,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.login(credentials);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);

            if (!currentPage.equals("homepage autentificat")) {
                setCurrentPage("homepage neautentificat");
            }
        } else {
            currentUser.getAvailableMovies().clear();
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
            currentUser.getPages().push(currentPage);
        }
    }

    private void updateAvailableMovies() {
        currentUser.getAvailableMovies().clear();

        for (Movie movie : movies) {
            if (!movie.getCountriesBanned().contains(currentUser.getCredentials().getCountry())) {
                currentUser.getAvailableMovies().add(movie);
            }
        }
    }

    @Override
    public void acceptRegister(
            final Visitor visitor,
            final User.Credentials credentials,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.register(credentials);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        }
    }

    /**
     * Adds a user in the platform's database
     * @param credentials for credentials of the new user
     * @return the newly added user
     */
    public User addUser(final User.Credentials credentials) {
        User newUser = new User();

        newUser.setCredentials(credentials);
        users.add(newUser);

        return newUser;
    }

    @Override
    public void acceptSearch(
            final Visitor visitor,
            final String startsWith,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.search(startsWith);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        }

        updateAvailableMovies();
    }

    @Override
    public void acceptFilter(
            final Visitor visitor,
            final Action.Filters filters,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        updateAvailableMovies();
        String error = visitor.filter(filters);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseSuccessOutput(jsonObject, objectMapper, currentUser);
        }
    }

    @Override
    public void acceptBuyTokens(
            final Visitor visitor,
            final Integer count,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.buyTokens(count);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        }
    }

    @Override
    public void acceptBuyPremiumAccount(
            final Visitor visitor,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.buyPremiumAccount();

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        }
    }

    @Override
    public void acceptPurchaseMovie(
            final Visitor visitor,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.purchaseMovie();

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseMovieOutput(jsonObject, objectMapper, searchedMovie, currentUser);
        }
    }

    @Override
    public void acceptWatchMovie(
            final Visitor visitor,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.watchMovie();

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseMovieOutput(jsonObject, objectMapper, searchedMovie, currentUser);
        }
    }

    @Override
    public void acceptLikeMovie(
            final Visitor visitor,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.likeMovie();

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseMovieOutput(jsonObject, objectMapper, searchedMovie, currentUser);
        }
    }

    @Override
    public void acceptRateMovie(
            final Visitor visitor,
            final Integer rate,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        String error = visitor.rateMovie(rate);

        if (error != null) {
            parseErrorOutput(jsonObject, objectMapper);
        } else {
            parseMovieOutput(jsonObject, objectMapper, searchedMovie, currentUser);
        }
    }

    @Override
    public void addObserver(final Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void modifyState(
            final String action,
            final Movie movie,
            final String movieName
    ) {
        performedAction = action;
        notifyObservers(movie, movieName);
    }

    @Override
    public void notifyObservers(
            final Movie movie,
            final String movieName
    ) {
        for (Observer observer : observers) {
            if (performedAction.equals("ADD")) {
                observer.updateAddedMovie(movie);
            } else {
                observer.updateDeletedMovie(movieName);
            }
        }
    }

    /**
     * Current logged-in user subscribes to a specified genre
     * @param subscribedGenre for the subscribed genre
     * @param jsonObject for the json object
     * @param objectMapper for the object mapper
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    public void subscribe(
            final String subscribedGenre,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        if (!currentPage.equals("see details")) {
            parseErrorOutput(jsonObject, objectMapper);
            return;
        }

        if (currentUser.getSubscribedGenres().contains(subscribedGenre)) {
            parseErrorOutput(jsonObject, objectMapper);
            return;
        }

        if (searchedMovie.getGenres().contains(subscribedGenre)) {
            currentUser.getSubscribedGenres().add(subscribedGenre);
            addObserver(currentUser);
        } else {
            parseErrorOutput(jsonObject, objectMapper);
        }
    }

    /**
     * Adds a specified movie to the database
     * @param addedMovie for the added movie
     * @param jsonObject for the json object
     * @param objectMapper for the object mapper
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    public void addMovie(
            final Movie addedMovie,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        for (Movie movie : movies) {
            if (movie.getName().equals(addedMovie.getName())) {
                parseErrorOutput(jsonObject, objectMapper);
                return;
            }
        }

        getMovies().add(addedMovie);
        modifyState("ADD", addedMovie, null);
    }

    /**
     * Deletes a specified movie from the database
     * @param deletedMovie for the name of the movie
     * @param jsonObject for the json object
     * @param objectMapper for the object mapper
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    public void deleteMovie(
            final String deletedMovie,
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        for (Movie movie : movies) {
            if (movie.getName().equals(deletedMovie)) {
                movies.remove(movie);
                modifyState("DELETE", null, deletedMovie);
                return;
            }
        }

        parseErrorOutput(jsonObject, objectMapper);
    }

    /**
     * Navigates to the previous page that the user accessed
     * @param jsonObject for the json object
     * @param objectMapper for the object mapper
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    public void navigateToPreviousPage(
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
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

            setCurrentPage(lastAccessedPage);

            if (lastAccessedPage.equals("see details")) {
                parseMovieOutput(jsonObject, objectMapper, searchedMovie, currentUser);
            } else if (lastAccessedPage.equals("movies")) {
                parseSuccessOutput(jsonObject, objectMapper, currentUser);
            }

            currentUser.getPages().push(lastAccessedPage);
        }
    }

    /**
     * Recommends a movie to the current user if he/she has a premium account
     * @param jsonObject for json object
     * @param objectMapper for object mapper
     * @throws JsonProcessingException in case of exceptions when processing the json object
     */
    public void recommendMovie(
            final ObjectNode jsonObject,
            final ObjectMapper objectMapper
    ) throws JsonProcessingException {
        if (currentUser.getLikedMovies().isEmpty()) {
            currentUser.getNotifications().add(
                    new User.Notifications("No recommendation", "Recommendation")
            );
            parseRecommendOutput(jsonObject, objectMapper, currentUser);
            return;
        }

        TreeMap<String, Integer> preferredGenres = new TreeMap<>();

        for (Movie likedMovie : currentUser.getLikedMovies()) {
            for (String genre : likedMovie.getGenres()) {
                if (preferredGenres.containsKey(genre)) {
                    int numLikes = preferredGenres.get(genre);
                    preferredGenres.replace(genre, numLikes, numLikes + 1);
                } else {
                    preferredGenres.put(genre, 1);
                }
            }
        }

        movies.sort((o1, o2) -> Integer.compare(o2.getNumLikes(), o1.getNumLikes()));

        while (!preferredGenres.isEmpty()) {
            String mostLikedGenre = null;
            int maxLikes = 0;

            for (Map.Entry<String, Integer> entry : preferredGenres.entrySet()) {
                if (entry.getValue() > maxLikes) {
                    maxLikes = entry.getValue();
                    mostLikedGenre = entry.getKey();
                }
            }

            for (Movie movie : movies) {
                if (!movie.getCountriesBanned().contains(currentUser.getCredentials().getCountry())) {
                    if (movie.getGenres().contains(mostLikedGenre)
                            && !currentUser.getWatchedMovies().contains(movie)) {
                        currentUser.getNotifications().add(
                                new User.Notifications(movie.getName(), "Recommendation")
                        );
                        parseRecommendOutput(jsonObject, objectMapper, currentUser);
                        return;
                    }
                }
            }

            preferredGenres.remove(mostLikedGenre);
        }

        currentUser.getNotifications().add(
                new User.Notifications("No recommendation", "Recommendation")
        );
        parseRecommendOutput(jsonObject, objectMapper, currentUser);
    }
}
