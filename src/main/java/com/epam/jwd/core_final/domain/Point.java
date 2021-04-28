package com.epam.jwd.core_final.domain;

public class Point implements Comparable<Point>{
    private final double coordinateX;
    private final double coordinateY;

    /**
     * Constructs a new {@link Point} containing coordinates X and Y.
     */
    public Point(double coordinate_x, double getCoordinate_y) {
        coordinateX = coordinate_x;
        coordinateY = getCoordinate_y;
    }

    /**
     * Returns the coordinate X of the {@link Point}
     *
     * @return the value of the parameter coordinateX of this {@link Point}
     */
    public double getCoordinateX() {
        return coordinateX;
    }

    /**
     * Returns the coordinate Y of the {@link Point}
     *
     * @return the value of the parameter coordinateY of this {@link Point}
     */
    public double getCoordinateY() {
        return coordinateY;
    }

    /**
     * Returns 0 if X and Y coordinates of this {@link Point} and
     * of the {@link Point} to be compared with are equal.
     * Returns -1 if coordinate X of this {@link Point} less than coordinate X
     * of the {@link Point} to be compared with.
     * Returns -1 if coordinate X of this {@link Point} equal to
     * the coordinate X of the {@link Point} to be compared with and
     * coordinate Y of this {@link Point} less than coordinate Y
     * of the {@link Point} to be compared with.
     * Returns 1 otherwise.
     *
     * @param other is the {@link Point} the object to be compared with
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object
     */
    @Override
    public int compareTo(Point other) {
        if (this.coordinateX > other.coordinateX) {
            return 1;
        } else if (this.coordinateX < other.coordinateX) {
            return -1;
        } else if (this.coordinateY > other.coordinateY) {
            return 1;
        } else if (this.coordinateY < other.coordinateY) {
            return -1;
        } else return 0;
    }

    /**
     * Prints text view of the {@link Point}. The {@link Point} is represented
     * in the following way: (x1, y1), where x1 = coordinate X
     * of this {@link Point} and y1 = coordinate Y of this {@link Point}.
     *
     * @return String, that represents tupel of the coordinates
     * of this {@link Point}
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        double x = this.getCoordinateX();
        double y = this.getCoordinateY();
        builder.append("(")
                .append(Math.round(x))
                .append(", ")
                .append(Math.round(y))
                .append(")");
        return builder.toString();
    }
}
