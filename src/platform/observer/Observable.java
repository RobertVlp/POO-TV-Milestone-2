package platform.observer;

import platform.movies.Movie;

public interface Observable {
    /**
     * Adds an observer to the current object
     * @param observer for the observer
     */
    void addObserver(Observer observer);

    /**
     * Modifies the state of the object, updating all of it's observers
     * @param action for the performed action
     * @param movie for movie, in case a new movie has been added
     * @param movieName for the name of the movie, in case a movie has been deleted
     */
    void modifyState(String action, Movie movie, String movieName);

    /**
     * Notifies all the observers according to the action that was performed
     * @param movie for movie, in case a new movie has been added
     * @param movieName for the name of the movie, in case a movie has been deleted
     */
    void notifyObservers(Movie movie, String movieName);
}
