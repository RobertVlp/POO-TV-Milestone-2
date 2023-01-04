package platform;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import platform.movies.Movie;
import platform.observer.Observer;

import java.util.ArrayList;
import java.util.Stack;

@JsonIgnoreProperties({"availableMovies", "subscribedGenres", "pages"})
public final class User implements Observer {
    public static final class Credentials {
        private String name;
        private String password;
        private String accountType;
        private String country;
        private String balance;

        public String getName() {
            return name;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(final String password) {
            this.password = password;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(final String accountType) {
            this.accountType = accountType;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(final String country) {
            this.country = country;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(final String balance) {
            this.balance = balance;
        }
    }

    private Credentials credentials;
    private Integer tokensCount;
    private Integer numFreePremiumMovies;
    private final ArrayList<Movie> purchasedMovies;
    private final ArrayList<Movie> watchedMovies;
    private final ArrayList<Movie> likedMovies;
    private final ArrayList<Movie> ratedMovies;
    private final ArrayList<Movie> availableMovies;
    private final ArrayList<String> subscribedGenres;
    private final Stack<String> pages;
    private final ArrayList<Notifications> notifications;

    private record Notifications(String movieName, String message) {
    }

    public User() {
        availableMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        purchasedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
        subscribedGenres = new ArrayList<>();
        pages = new Stack<>();
        notifications = new ArrayList<>();
        setNumFreePremiumMovies(15);
        setTokensCount(0);
    }

    @Override
    public void updateAddedMovie(final Movie addedMovie) {
        if (addedMovie.getCountriesBanned().contains(credentials.country)) {
            return;
        }

        for (String genre : addedMovie.getGenres()) {
            if (subscribedGenres.contains(genre)) {
                notifications.add(new Notifications(addedMovie.getName(), "ADD"));
                break;
            }
        }
    }

    @Override
    public void updateDeletedMovie(final String deletedMovie) {
        for (Movie movie : purchasedMovies) {
            if (movie.getName().equals(deletedMovie)) {
                notifications.add(new Notifications(deletedMovie, "DELETE"));

                purchasedMovies.remove(movie);
                watchedMovies.remove(movie);
                ratedMovies.remove(movie);
                likedMovies.remove(movie);

                if (credentials.getAccountType().equals("premium")) {
                    numFreePremiumMovies++;
                } else {
                    tokensCount += 2;
                }

                break;
            }
        }
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final Credentials credentials) {
        this.credentials = credentials;
    }

    public Integer getTokensCount() {
        return tokensCount;
    }

    public void setTokensCount(final Integer tokensCount) {
        this.tokensCount = tokensCount;
    }

    public Integer getNumFreePremiumMovies() {
        return numFreePremiumMovies;
    }

    public void setNumFreePremiumMovies(final Integer numFreePremiumMovies) {
        this.numFreePremiumMovies = numFreePremiumMovies;
    }

    public ArrayList<Movie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public ArrayList<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public ArrayList<Movie> getLikedMovies() {
        return likedMovies;
    }

    public ArrayList<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public ArrayList<Movie> getAvailableMovies() {
        return availableMovies;
    }

    public ArrayList<String> getSubscribedGenres() {
        return subscribedGenres;
    }

    public Stack<String> getPages() {
        return pages;
    }

    public ArrayList<Notifications> getNotifications() {
        return notifications;
    }
}
