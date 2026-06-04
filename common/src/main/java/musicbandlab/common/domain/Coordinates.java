package musicbandlab.common.domain;

/**
 * Класс, представляющий координаты музыкальной группы.
 * Содержит координаты "x" и "y" с ограничениями по значениям.
 * Реализует Comparable для сравнения координат.
 */
public class Coordinates implements Comparable<Coordinates>, java.io.Serializable {
    private int x;
    private double y;

    private Coordinates() { }

    public Coordinates(int x, double y){
        if(x > 254)
            throw new IllegalArgumentException("x should be less than or equal to 254");

        if(y > 93)
            throw new IllegalArgumentException("y should be less than or equal to 93");

        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int compareTo(Coordinates o) {
        if (o == null)
            return 1;

        int xCompare = Integer.compare(this.x, o.x);
        if (xCompare != 0) {
            return xCompare;
        }

        return Double.compare(this.y, o.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Coordinates that = (Coordinates) obj;

        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Double.hashCode(y);
        return result;
    }

    @Override
    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
}