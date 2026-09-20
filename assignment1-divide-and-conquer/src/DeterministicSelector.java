public class DeterministicSelector
{
    private long comparisons;
    private int maxRecursionDepth;

    public int select(int[] arr,int k)
    {
        comparisons = 0;
        maxRecursionDepth = 0;
        if (arr==null || k < 0 ||k>=arr.length)
        {
            throw new IllegalArgumentException("Invalid input or k");
        }
        int[] copy=arr.clone();
        int index=selectIndex(copy,0,copy.length - 1, k, 1);
        return copy[index];
    }

    private int selectIndex(int[] arr,int left,int right,int k,int depth)
    {
        if (depth>maxRecursionDepth)
        {
            maxRecursionDepth=depth;
        }

        if (left==right)
        {
            return left;
        }

        int pivotIndex= medianOfMediansIndex(arr, left, right, depth + 1);
        int storeIndex= partition(arr, left, right, pivotIndex);

        if (k==storeIndex)
        {
            return storeIndex;
        }
        else if (k<storeIndex)
        {
            return selectIndex(arr,left,storeIndex - 1,k, depth + 1);
        }
        else
        {
            return selectIndex(arr,storeIndex + 1, right,k,depth + 1);
        }
    }

    private int medianOfMediansIndex(int[] arr,int left,int right,int depth)
    {
        int n= right-left+ 1;
        if (n<=5)
        {
            insertionSort(arr,left,right);
            return left+n/2;
        }

        int numGroups=(n + 4)/5;
        for (int i= 0;i<numGroups;i++)
        {
            int subLeft=left+i * 5;
            int subRight =Math.min(subLeft+ 4, right);
            insertionSort(arr,subLeft,subRight);
            int medianIndex= subLeft+(subRight- subLeft)/2;
            swap(arr,left + i,medianIndex);
        }

        int targetMedianIndex=left+ numGroups/2;
        return selectIndex(arr,left,left+numGroups - 1,targetMedianIndex, depth + 1);
    }

    private void insertionSort(int[] arr,int left,int right)
    {
        for (int i=left+1;i<= right;i++)
        {
            int key=arr[i];
            int j=i-1;
            while (j>=left)
            {
                comparisons++;
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

    private int partition(int[] arr,int left,int right,int pivotIndex)
    {
        int pivotValue= arr[pivotIndex];
        swap(arr,pivotIndex,right);
        int storeIndex=left;
        for(int i=left;i< right;i++)
        {
            comparisons++;
            if (arr[i]<pivotValue)
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
        if (i !=j)
        {
            int temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
        }
    }

    public long getComparisons() { return comparisons;
    }
    public int getMaxRecursionDepth() { return maxRecursionDepth;
    }
}