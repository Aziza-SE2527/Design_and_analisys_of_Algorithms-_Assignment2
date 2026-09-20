public class DeterministicSelector
{
    private long comparisons;
    private int maxRecursionDepth;

    public int select(int[] arr, int k)
    {
        comparisons = 0;
        maxRecursionDepth = 0;
        if(arr==null || k<0 ||k>=arr.length)
        {
            throw new IllegalArgumentException("Invalid input or k");
        }
        int[] copy=arr.clone();
        return select(copy,0,copy.length-1,k,1);
    }
    private int select(int[] arr, int left,int right,int k,int depth)
    {
        if(depth> maxRecursionDepth)
        {
            maxRecursionDepth = depth;
        }
        if(left==right)
        {
            return arr[left];
        }
        int pivotIndex =medianOfMedians(arr,left,right,depth+1);
        pivotIndex=partition(arr,left,right,pivotIndex);

        if(k==pivotIndex)
        {
            return arr[k];
        }
        else if(k<pivotIndex)
        {
            return select(arr,left,pivotIndex-1,k,depth+1);
        }
        else
        {
            return select(arr,pivotIndex+1,right,k,depth+1);
        }
    }

    private int medianOfMedians(int[] arr,int left,int right,int k,int depth)
    {
        int numElements=right-left+1;
        if(numElements<=5)
        {
            insertionSort(arr,left,right);
            return left+numElements;
        }
        int numGroups=(numElements+4)/5;
        for(int i=0; i<numGroups; i++)
        {
            int subLeft=left+i*5;
            int subRight = Math.min(subLeft+4,right);
            insertionSort(arr,subLeft,subRight);
            int medianIndex = subLeft +(subRight-subLeft)/2;
            swap(arr,left+i,medianIndex);
        }
        return select(arr,left,left+numGroups-1,left+numGroups/2,depth+1);
    }
    private void insertionSort(int[] arr,int left,int right)
    {
        for(int i=left+1; i<=right;i++)
        {
            int key=arr[i];
            int j=i-1;
            while (j>=left)
            {
                comparisons++;
                if (arr[j] > key)
                {
                    arr[j+1] = arr[j];
                    j--;
                }
                else
                {
                    break;
                }
            }
            arr[j+1] = key;
        }
    }
    private int partition(int[] arr,int left,int right,int pivotIndex)
    {
        int pivotValue=arr[pivotIndex];
        swap(arr,pivotIndex,right);
        int storeIndex=left;
        for(int i=left+1; i<right;i++)
        {
            comparisons++;
            if(arr[i]>pivotValue)
            {
                swap(arr,storeIndex,i);
                storeIndex++;
            }
        }
        swap(arr,right,storeIndex);
        return storeIndex;
    }
    private void swap(int[] arr,int i,int j)
    {
        if(i != j)
        {
            int temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
        }
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
