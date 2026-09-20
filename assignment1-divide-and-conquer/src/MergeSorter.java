public class MergeSorter
{
    private long comparisons;
    private long moves;
    private int maxRecursionDepth;
    private int currentDepth;
    private static final int CUTOFF=15;

    public void sort(int[] arr)
    {
        comparisons=0;
        moves=0;
        currentDepth=0;
        maxRecursionDepth=0;
        if (arr==null||arr.length<=1)
            return;
        int[] aux=new int[arr.length];
        sort(arr ,aux, 0 , arr.length-1);
    }

    private void sort(int[] arr, int[] aux, int lo, int hi)
    {
        currentDepth++;
        if(currentDepth>maxRecursionDepth)
        {
            maxRecursionDepth=currentDepth;
        }

        if(hi -lo<=CUTOFF)
        {
            insertionSort(arr,lo,hi);
            currentDepth--;
            return;
        }
        int mid = lo + (hi-lo)/2;
        sort(arr,aux,lo,mid);
        sort(arr,aux,mid+1,hi);
        merge(arr,aux,lo,mid,hi);
        currentDepth--;
    }

    private void merge(int[] arr, int[] aux, int lo, int mid, int hi)
    {
        for(int k=lo; k<=hi; k++)
        {
            aux[k]=arr[k];
        }
        int i=lo,j=mid+1;
        for(int k=lo; k<=hi; k++) {
            if (i > mid) {
                arr[k] = aux[j++];
            } else if (j > hi) {
                arr[k] = aux[i++];
            } else {
                comparisons++;
                if (aux[i] > aux[j]) {
                    arr[k] = aux[j++];
                } else {
                    arr[k] = aux[i++];
                }
            }
        }
    }
    private void insertionSort(int[] arr, int lo, int hi)
    {
        for(int i = lo+1;i<=hi;i++)
        {
            int key = arr[i];
            int j=i-1;
            while(j>=lo)
            {
                if(arr[j]>key)
                {
                    arr[j+1]=arr[j];
                    j--;
                }
                else
                {
                    break;
                }
            }
            arr[j+1]=key;
        }
    }

    public long  getComparisons()
    {
        return comparisons;
    }
    public long getMaxRecursionDepth()
    {
        return maxRecursionDepth;
    }
}
