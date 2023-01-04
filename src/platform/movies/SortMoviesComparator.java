package platform.movies;

import java.util.Comparator;

public final class SortMoviesComparator implements Comparator<Movie> {
    private final String duration;
    private final String rating;

    public SortMoviesComparator(final String duration, final String rating) {
        this.duration = duration;
        this.rating = rating;
    }

    @Override
    public int compare(final Movie o1, final Movie o2) {
        int compareDuration = o1.getDuration().compareTo(o2.getDuration());
        int compareRating = o1.getRating().compareTo(o2.getRating());

        if (duration != null && rating != null) {
            if (o1.getDuration().equals(o2.getDuration())) {
                if (rating.equals("decreasing")) {
                    return (-1) * compareRating;
                } else {
                    return compareRating;
                }
            } else {
                if (duration.equals("decreasing")) {
                    return (-1) * compareDuration;
                } else {
                    return compareDuration;
                }
            }
        } else {
            if (duration != null) {
                if (duration.equals("decreasing")) {
                    return (-1) * compareDuration;
                } else {
                    return compareDuration;
                }
            } else {
                if (rating.equals("decreasing")) {
                    return (-1) * compareRating;
                } else {
                    return compareRating;
                }
            }
        }
    }
}
