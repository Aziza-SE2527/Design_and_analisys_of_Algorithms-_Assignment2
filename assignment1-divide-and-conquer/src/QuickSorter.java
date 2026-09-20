import java.util.Random;
public class QuickSorter
{
    private long comparisons;
    private int maxRecursiondepth;
    private final Random rand = new Random();

    public void sort(int[] arr)
    {
        comparisons = 0;
        maxRecursiondepth = 0;
        if (arr==null || arr.length<=1)
            return;
        sortIterative(arr,0,arr.length-1,1);
    }

    private void sortIterative(int[] arr, int lo, int hi, int depth)
    {
        if(depth>maxRecursiondepth)
        {
            maxRecursiondepth = depth;
        }
        while(lo<hi) {
            int pivotIndex = lo + rand.nextInt(hi - lo + 1);
            int p = partition(arr, lo, hi, pivotIndex);

            if (p - lo < hi - p) {
                sortIterative(arr, lo, p - 1, depth + 1);
                lo = p + 1;
            } else {
                sortIterative(arr, p + 1, hi, depth + 1);
                hi = p - 1;

            }
        }
    }

    private int partition(int[] arr, int lo, int hi, int pivotIndex)
    {
        int pivot =arr[pivotIndex];
        swap(arr,pivotIndex,hi);

        int i = lo;
        for (int j=lo;j<hi; j++)
        {
            comparisons++;
            if (arr[j]<=pivot)
            {
                swap(arr,i,j);
                i++;
            }
        }
        swap(arr,i,hi);
        return i;
    }
    private void swap(int[] arr, int i, int j)
    {
        if(i!= j) {
            int temp = arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
        }
    }
    public long getComparisons() {
        return comparisons;
    }
    public int getMaxRecursiondepth()
    {
        return maxRecursiondepth;
    }

}
