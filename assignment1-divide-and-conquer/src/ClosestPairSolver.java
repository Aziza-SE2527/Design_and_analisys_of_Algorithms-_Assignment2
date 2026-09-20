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
        List<Point> pylList = new ArrayList<>();
        List<Point> pyrList = new ArrayList<>();

        int leftCount=mid- lo + 1;
        for(Point p : py)
        {
            comparisons++;
            if((p.getX() < midPoint.getX() || (p.getX()==midPoint.getX() && p.getY() <= midPoint.getY()))
                    && pylList.size() < leftCount)
            {
                pylList.add(p);
            }
            else
            {
                pyrList.add(p);
            }
        }

        Point[] pyl=pylList.toArray(new Point[0]);
        Point[] pyr=pyrList.toArray(new Point[0]);

        Result delta1=closestRobust(px,pyl,lo, mid,depth + 1);
        Result delta2 =closestRobust(px,pyr,mid + 1,hi, depth + 1);

        Result d=(delta1.distance < delta2.distance)? delta1 : delta2;
        double minDist=d.distance;

        List<Point> stripList=new ArrayList<>();
        for(Point p : py)
        {
            comparisons++;
            if (Math.abs(p.getX()-midPoint.getX())<minDist)
            {
                stripList.add(p);
            }
        }

        for(int i=0;i<stripList.size();i++)
        {
            for(int j=i+1;j<stripList.size() && (Math.abs(stripList.get(j).getY()-stripList.get(i).getY()))< minDist; j++)
            {
                comparisons++;
                Point p1=stripList.get(i);
                Point p2=stripList.get(j);
                double dist = p1.distanceTo(p2);
                if(dist<minDist)
                {
                    minDist=dist;
                    d=new Result(p1,p2, minDist);
                }
            }
        }

        return d;
    }

    private Result bruteForce(Point[] points,int low,int high)
    {
        double minDist= Double.MAX_VALUE;
        Point p1= null,p2 = null;
        for (int i=low;i<= high;i++)
        {
            for (int j =i + 1;j<= high;j++)
            {
                comparisons++;
                double dist = points[i].distanceTo(points[j]);
                if (dist< minDist)
                {
                    minDist= dist;
                    p1= points[i];
                    p2= points[j];
                }
            }
        }
        return new Result(p1,p2,minDist);
    }

    public Result bruteForceAll(Point[] points)
    {
        double minDist= Double.MAX_VALUE;
        Point p1= null,p2 = null;
        for (int i = 0;i < points.length;i++)
        {
            for (int j =i + 1; j < points.length; j++)
            {
                double dist = points[i].distanceTo(points[j]);
                if (dist < minDist)
                {
                    minDist= dist;
                    p1= points[i];
                    p2= points[j];
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