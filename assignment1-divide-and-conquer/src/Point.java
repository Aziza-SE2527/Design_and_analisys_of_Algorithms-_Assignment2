public class Point implements Comparable<Point>
{
    private double x;
    private double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }


    @Override
    public int compareTo(Point other)
    {
        int cmp = Double.compare(this.x, other.x);
        if (cmp == 0)
        {
            cmp = Double.compare(this.y, other.y);
        }
        return Double.compare(this.y,other.y);
    }

    public double distanceTo(Point other)
    {
        double dx = x - other.x;
        double dy = y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    @Override
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }
}
