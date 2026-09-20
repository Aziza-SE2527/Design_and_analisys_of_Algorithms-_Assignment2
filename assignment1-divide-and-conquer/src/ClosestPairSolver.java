import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ClosestPairSolver
{
    private long comparisons;
    private int maxRecursionDepth;

    public static class Result
    {
        public final Point p1;
        public final Point p2;
        public final double distance;

        public Result(Point p1, Point p2, double distance)
        {
            this.p1 = p1;
            this.p2 = p2;
            this.distance = distance;
        }
    }

    public Result findClosestPair(Point[] points)
    {
        comparisons = 0;
        maxRecursionDepth = 0;

        if (points == null || points.length < 2)
        {
            throw new IllegalArgumentException("At least 2 points expected");
        }

        Point[] pointsSortedX = points.clone();
        Arrays.sort(pointsSortedX);

        Point[] pointsSortedY = points.clone();
        Arrays.sort(pointsSortedY, (a, b) -> Double.compare(a.getY(), b.getY()));
        return closestRobust(pointsSortedX, pointsSortedY, 0, points.length - 1, 1);
    }

    private Result closestRobust(Point[] px, Point[] py, int lo, int hi, int depth)
    {
        if (depth > maxRecursionDepth)
        {
            maxRecursionDepth = depth;
        }
        if (hi - lo <= 3)
        {
            return bruteForce(px, lo, hi);
        }

        int mid = lo + (hi - lo) / 2;
        Point midPoint = px[mid];
        Point[] pyl = new Point[mid - lo + 1];
        Point[] pyr = new Point[hi - mid];
        int li = 0, ri = 0;
        for (Point p : py)
        {
            comparisons++;
            if (p.getX() < midPoint.getX() || (p.getX() == midPoint.getX() && li < pyl.length))
            {
                if (li < pyl.length)
                {
                    pyl[li++] = p;
                }
                else
                {
                    pyr[ri++] = p;
                }
            }
            else
            {
                if (ri < pyr.length)
                {
                    pyr[ri++] = p;
                }
                else
                {
                    pyl[li++] = p;
                }
            }
        }
        Result delta1=closestRobust(px,pyl,lo,mid,depth+1);
        Result delta2=closestRobust(px,pyr,lo,mid+1,depth+1);
        Result d= (delta1.distance<delta2.distance) ? delta1:delta2;
        double minDist=d.distance;

        List<Point> stripList =new ArrayList<>();
        for(Point p:py)
        {
            comparisons++;
            if (Math.abs(p.getX()-midPoint.getX() <minDist))
            {
                stripList.add(p);
            }
        }
        for (int i=0;i<stripList.size();i++)
        {
            for(int j=i+1; j<stripList.size() &&(stripList.get(j).getY()-stripList.get(i).getY())<minDist ;j++)
            {
                comparisons++;
                Point p1=stripList.get(i);
                Point p2=stripList.get(j);
                double dist=p1.distanceTo(p2);
                if(dist<minDist)
                {
                    minDist=dist;
                    d=new Result(p1,p2,minDist);
                }
            }
        }
        return d;

    }
    private Result bruteForce(Point[] points, int lo, int hi)
    {
        double minDist=Double.MAX_VALUE;
        Point p1=null;
        Point p2=null;
        for (int i=lo; i<hi; i++)
        {
            for(int j=i+1; j<hi; j++)
            {
                comparisons++;
                double dist = points[i].distanceTo(points[j]);
                if(dist<minDist)
                {
                    minDist=dist;
                    p1=points[i];
                    p2=points[j];
                }
            }
        }
        return new Result(p1,p2,minDist);
    }
    public Result bruteForceAll(Point[] points)
    {
        double minDist = Double.MAX_VALUE;
        Point p1=null;
        Point p2=null;
        for (int i=0;i<points.length;i++)
        {
            for(int j=i;j<points.length;j++)
            {
                double dist=points[i].distanceTo(points[j]);
                if(dist<minDist)
                {
                    minDist=dist;
                    p1=points[i];
                    p2=points[j];
                }
            }
        }
        return new Result(p1,p2,minDist);
    }
    public long getComparisons()
    {
        return comparisons;
    }
    public int getMaxRecursionDepth()
    {
        return maxRecursionDepth;
    }

}
