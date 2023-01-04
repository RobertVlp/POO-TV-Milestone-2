package platform;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import platform.movies.Movie;

import java.util.ArrayList;

@JsonIgnoreProperties({"availableMovies"})
public final class User {
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

    public User() {
        availableMovies = new ArrayList<>();
        watchedMovies = new ArrayList<>();
        purchasedMovies = new ArrayList<>();
        likedMovies = new ArrayList<>();
        ratedMovies = new ArrayList<>();
        setNumFreePremiumMovies(15);
        setTokensCount(0);
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
}
