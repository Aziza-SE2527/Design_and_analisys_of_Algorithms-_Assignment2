import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Experiment
{
    public static void runALL()
    {
        String csvFile = "algorithm_experiment_result.csv";
        try(FileWriter writer =new FileWriter(csvFile))
        {
            writer.append("Algothm,InputType");

            int[] sizes={100,1000,10000};
            String[]types ={"Random", "Sorted","DuplicateHeavy"};
            Random rand = new Random(42);

            for(int size:sizes)
            {
                for(String type:types)
                {
                    int[] baseArray=generateArray(size,type,rand);
                    Point[] basePoints=generatePoints(size,rand);

                    //Merge
                    int[] arr1 = baseArray.clone();
                    MergeSorter mergeSorter =new MergeSorter();
                    long start = System.nanoTime();
                    mergeSorter.sort(arr1);
                    long duration= System.nanoTime()-start;
                    writer.append(String.format("MergeSorter,%s,%d,%d,%d,%d\n",type,size,duration,mergeSorter.getMaxRecursionDepth(),mergeSorter.getComparisons()));

                    //Quick

                    int[] arr2 = baseArray.clone();
                    QuickSorter quickSorter =new QuickSorter();
                    start = System.nanoTime();
                    quickSorter.sort(arr2);
                    duration = System.nanoTime()-start;
                    writer.append(String.format("QuickSorter,%s,%d,%d,%d,%d\n", type,size,duration,quickSorter.getMaxRecursiondepth(),quickSorter.getComparisons()));

                    //Deterministic
                    int[] arr3 = baseArray.clone();
                    DeterministicSelector selector =new DeterministicSelector();
                    start = System.nanoTime();
                    selector.select(arr3, arr3.length/2);
                    duration = System.nanoTime()-start;
                    writer.append(String.format("DeterministicSelect,%s,%d,%d,%d,%d\n", type,size,duration,selector.getMaxRecursionDepth(),selector.getComparisons()));

                    //Closest Pair
                    if(size<=10000)
                    {
                        ClosestPairSolver solver =new ClosestPairSolver();
                        start = System.nanoTime();
                        solver.findClosestPair(basePoints);
                        duration = System.nanoTime()-start;
                        writer.append(String.format("ClosestPair,%s,%d,%d,%d,%d\n",type,size,duration,solver.getMaxRecursionDepth(),solver.getComparisons()));
                    }
                }
            }
            System.out.println("Done" + csvFile);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    private static int[] generateArray(int size, String type, Random rand) {
    int[] arr = new int[size];
    switch (type) {
        case "Random":
            for (int i = 0; i < size; i++) arr[i] = rand.nextInt(100000);
            break;
        case "Sorted":
            for (int i = 0; i < size; i++) arr[i] = i;
            break;
        case "ReverseSorted":
            for (int i = 0; i < size; i++) arr[i] = size - i;
            break;
        case "DuplicateHeavy":
            for (int i = 0; i < size; i++) arr[i] = rand.nextInt(10);
            break;
    }
    return arr;
}

    private static Point[] generatePoints(int size, Random rand) {
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }
        return points;
    }
}

