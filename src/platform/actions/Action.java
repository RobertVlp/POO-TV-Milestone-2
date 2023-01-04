package platform.actions;

import platform.User;
import platform.movies.Movie;

import java.util.ArrayList;

public final class Action {
    private String type;
    private String page;
    private String feature;
    private User.Credentials credentials;
    private String startsWith;
    private String movie;
    private Integer rate;
    private String deletedMovie;
    private Movie addedMovie;
    private String subscribedGenre;

    public static final class Filters {
        public static final class Sort {
            private String rating;
            private String duration;

            public String getRating() {
                return rating;
            }

            public void setRating(final String rating) {
                this.rating = rating;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(final String duration) {
                this.duration = duration;
            }
        }

        public static final class Contains {
            private ArrayList<String> actors;
            private ArrayList<String> genre;

            public ArrayList<String> getActors() {
                return actors;
            }

            public void setActors(final ArrayList<String> actors) {
                this.actors = actors;
            }

            public ArrayList<String> getGenre() {
                return genre;
            }

            public void setGenre(final ArrayList<String> genre) {
                this.genre = genre;
            }
        }

        private Sort sort;
        private Contains contains;

        public Sort getSort() {
            return sort;
        }

        public void setSort(final Sort sort) {
            this.sort = sort;
        }

        public Contains getContains() {
            return contains;
        }

        public void setContains(final Contains contains) {
            this.contains = contains;
        }
    }

    private Filters filters;
    private Integer count;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public void setPage(final String page) {
        this.page = page;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public User.Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(final User.Credentials credentials) {
        this.credentials = credentials;
    }

    public String getStartsWith() {
        return startsWith;
    }

    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(final String count) {
        this.count = Integer.valueOf(count);
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(final Filters filters) {
        this.filters = filters;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(final String movie) {
        this.movie = movie;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(final Integer rate) {
        this.rate = rate;
    }

    public String getDeletedMovie() {
        return deletedMovie;
    }

    public void setDeletedMovie(final String deletedMovie) {
        this.deletedMovie = deletedMovie;
    }

    public Movie getAddedMovie() {
        return addedMovie;
    }

    public void setAddedMovie(final Movie addedMovie) {
        this.addedMovie = addedMovie;
    }

    public String getSubscribedGenre() {
        return subscribedGenre;
    }

    public void setSubscribedGenre(final String subscribedGenre) {
        this.subscribedGenre = subscribedGenre;
    }
}
