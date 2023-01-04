package visitor;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import platform.actions.Action;
import platform.Platform;
import platform.User;
import platform.movies.Movie;
import platform.movies.SortMoviesComparator;

public final class PlatformVisitor implements Visitor {
    private final Platform platform;

    public PlatformVisitor(final Platform platform) {
        this.platform = platform;
    }

    @Override
    public String changePage(final String destinationPage) {
        switch (platform.getCurrentPage()) {
            case "homepage neautentificat" -> {
                if (!destinationPage.equals("login") && !destinationPage.equals("register")) {
                    return "Error";
                }
            }

            case "homepage autentificat" -> {
                if (!destinationPage.equals("movies") && !destinationPage.equals("upgrades")
                        && !destinationPage.equals("logout")) {
                    return "Error";
                }
            }

            case "movies" -> {
                if (!destinationPage.equals("homepage autentificat")
                        && !destinationPage.equals("see details")
                        && !destinationPage.equals("logout")
                        && !destinationPage.equals("movies")) {
                    return "Error";
                }
            }

            case "upgrades" -> {
                if (!destinationPage.equals("homepage autentificat")
                        && !destinationPage.equals("movies")
                        && !destinationPage.equals("logout")
                        && !destinationPage.equals("upgrades")) {
                    return "Error";
                }
            }

            case "see details" -> {
                if (!destinationPage.equals("homepage autentificat")
                        && !destinationPage.equals("movies")
                        && !destinationPage.equals("upgrades")
                        && !destinationPage.equals("logout")
                        && !destinationPage.equals("see details")) {
                    return "Error";
                }
            }

            default -> {
            }
        }

        platform.setCurrentPage(destinationPage);

        return null;
    }

    @Override
    public String login(final User.Credentials credentials) {
        if (platform.getCurrentUser() != null) {
            return "Error";
        }

        if (!platform.getCurrentPage().equals("login")) {
            return "Error";
        }

        User loggedUser = null;

        for (User user : platform.getUsers()) {
            if (user.getCredentials().getName().equals(credentials.getName())
                    && user.getCredentials().getPassword().equals(credentials.getPassword())) {
                loggedUser = user;
                break;
            }
        }

        if (loggedUser == null) {
            return "Error";
        }

        platform.setCurrentUser(loggedUser);
        platform.setCurrentPage("homepage autentificat");

        return null;
    }

    @Override
    public String register(final User.Credentials credentials) {
        if (!platform.getCurrentPage().equals("register")) {
            return "Error";
        }

        for (User user : platform.getUsers()) {
            if (user.getCredentials().getName().equals(credentials.getName())) {
                return "Error";
            }
        }

        platform.setCurrentUser(platform.addUser(credentials));
        platform.setCurrentPage("homepage autentificat");

        return null;
    }

    @Override
    public String search(final String startsWith) {
        if (!platform.getCurrentPage().equals("movies")) {
            return "Error";
        }

        ArrayList<Movie> movies = platform.getCurrentUser().getAvailableMovies();
        movies.removeIf(movie -> !movie.getName().startsWith(startsWith));

        return null;
    }

    @Override
    public String filter(final Action.Filters filters) {
        if (!platform.getCurrentPage().equals("movies")) {
            return "Error";
        }

        ArrayList<Movie> movies = platform.getCurrentUser().getAvailableMovies();

        if (filters.getSort() != null) {
            String duration = filters.getSort().getDuration();
            String rating = filters.getSort().getRating();
            SortMoviesComparator comparator = new SortMoviesComparator(duration, rating);

            movies.sort(comparator);
        }

        if (filters.getContains() != null) {
            if (filters.getContains().getActors() != null) {
                movies.removeIf(movie -> !movie.getActors().containsAll(filters.getContains().getActors()));
            }

            if (filters.getContains().getGenre() != null) {
                movies.removeIf(movie -> !movie.getGenres().containsAll(filters.getContains().getGenre()));
            }
        }

        return null;
    }

    @Override
    public String buyTokens(final Integer count) {
        if (!platform.getCurrentPage().equals("upgrades")) {
            return "Error";
        }

        int userBalance = Integer.parseInt(platform.getCurrentUser().getCredentials().getBalance());
        Integer userTokens = platform.getCurrentUser().getTokensCount();

        if (userBalance < count) {
            return "Error";
        }

        userBalance -= count;
        userTokens += count;

        platform.getCurrentUser().getCredentials().setBalance(String.valueOf(userBalance));
        platform.getCurrentUser().setTokensCount(userTokens);

        return null;
    }

    @Override
    public String buyPremiumAccount() {
        if (!platform.getCurrentPage().equals("upgrades")) {
            return "Error";
        }

        Integer userTokens = platform.getCurrentUser().getTokensCount();

        if (userTokens < 10) {
            return "Error";
        }

        userTokens -= 10;

        platform.getCurrentUser().setTokensCount(userTokens);
        platform.getCurrentUser().getCredentials().setAccountType("premium");

        return null;
    }

    @Override
    public String purchaseMovie() {
        if (!platform.getCurrentPage().equals("see details")) {
            return "Error";
        }

        if (platform.getSearchedMovie() == null) {
            return "Error";
        }

        User currentUser = platform.getCurrentUser();
        Integer numberOfTokens = currentUser.getTokensCount();

        if (currentUser.getCredentials().getAccountType().equals("premium")) {
            Integer numFreePremiumMovies = currentUser.getNumFreePremiumMovies();

            if (numFreePremiumMovies == 0) {
                if (numberOfTokens < 2) {
                    return "Error";
                }

                numberOfTokens -= 2;
            } else {
                numFreePremiumMovies -= 1;
                currentUser.setNumFreePremiumMovies(numFreePremiumMovies);
            }
        } else {
            if (numberOfTokens < 2) {
                return "Error";
            }

            numberOfTokens -= 2;
        }

        currentUser.setTokensCount(numberOfTokens);
        currentUser.getPurchasedMovies().add(platform.getSearchedMovie());

        return null;
    }

    @Override
    public String watchMovie() {
        if (!platform.getCurrentPage().equals("see details")) {
            return "Error";
        }

        if (platform.getSearchedMovie() == null) {
            return "Error";
        }

        User currentUser = platform.getCurrentUser();
        boolean isMoviePurchased = false;

        for (Movie movie : currentUser.getPurchasedMovies()) {
            if (movie != null) {
                if (movie.getName().equals(platform.getSearchedMovie().getName())) {
                    isMoviePurchased = true;
                    break;
                }
            }
        }

        if (isMoviePurchased) {
            currentUser.getWatchedMovies().add(platform.getSearchedMovie());
        } else {
            return "Error";
        }

        return null;
    }

    @Override
    public String likeMovie() {
        if (!platform.getCurrentPage().equals("see details")) {
            return "Error";
        }

        if (isMovieWatched(platform.getSearchedMovie())) {
            Integer numLikes = platform.getSearchedMovie().getNumLikes();
            numLikes += 1;

            platform.getSearchedMovie().setNumLikes(numLikes);
            platform.getCurrentUser().getLikedMovies().add(platform.getSearchedMovie());
        } else {
            return "Error";
        }

        return null;
    }

    @Override
    public String rateMovie(final Integer rate) {
        if (!platform.getCurrentPage().equals("see details")) {
            return "Error";
        }

        if (rate > 5) {
            return "Error";
        }

        Movie searchedMovie = platform.getSearchedMovie();

        if (searchedMovie == null) {
            return "Error";
        }

        if (isMovieWatched(searchedMovie)) {
            searchedMovie.getRatings().add(Double.valueOf(rate));
            Integer numRatings = searchedMovie.getNumRatings();
            numRatings += 1;

            AtomicReference<Double> totalRating = new AtomicReference<>(0D);

            searchedMovie.getRatings().forEach(rating -> totalRating.updateAndGet(v -> v + rating));
            searchedMovie.setNumRatings(numRatings);
            searchedMovie.setRating(totalRating.get() / numRatings);
            platform.getCurrentUser().getRatedMovies().add(searchedMovie);
        } else {
            return "Error";
        }

        return null;
    }

    private boolean isMovieWatched(final Movie movie) {
        for (Movie watchedMovie : platform.getCurrentUser().getWatchedMovies()) {
            if (watchedMovie != null) {
                if (watchedMovie.getName().equals(movie.getName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
