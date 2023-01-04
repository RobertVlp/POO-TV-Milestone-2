package platform.observer;

import platform.movies.Movie;

public interface Observer {
    /**
     * Updates all users that subscribed to at least one of the genres of the newly added movie
     * @param addedMovie for the added movie
     */
    void updateAddedMovie(Movie addedMovie);

    /**
     * Updates all users that bought the movie that would be deleted
     * @param deletedMovie for the name of the movie
     */
    void updateDeletedMovie(String deletedMovie);
}
