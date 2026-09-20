A. Project Overview
Purpose of the assignment: To implement, test, and analyze four classic divide-and-conquer algorithms in Java, measure their performance, and verify correctness against reference methods.
Implemented algorithms: MergeSort, QuickSort, Deterministic Select, and Closest Pair of Points.
B. Algorithm Analysis
MergeSort
How it works: Splits the array into two halves recursively, sorts small sub-arrays using Insertion Sort when size is small, and merges them back together using a reusable auxiliary buffer.
Complexity: Time is Theta(n log n), Space is Theta(n) for the buffer.
Recurrence and analysis: T(n) = 2T(n/2) + Theta(n). By Master Theorem Case 2, the running time is Theta(n log n).
QuickSort
How it works: Picks a random pivot, partitions the array in-place, and uses smaller-first recursion to keep the stack shallow.
Complexity: Time is O(n log n) average and O(n^2) worst case, Space is O(log n).
Recurrence and analysis: T(n) = 2T(n/2) + Theta(n), leading to O(n log n) on average.
Deterministic Select (Median-of-Medians)
How it works: Divides elements into groups of 5, finds each group's median, recursively finds the median of those medians to use as a good pivot, and searches only the necessary partition.
Complexity: Time is Theta(n) worst-case, Space is O(log n).
Recurrence and analysis: T(n) <= T(n/5) + T(7n/10) + Theta(n). Because the subproblems sum to less than n, the linear work dominates, giving Theta(n).
Closest Pair of Points
How it works: Sorts points by x-coordinate, splits them into left and right halves, finds the closest distances in each half, and checks a narrow y-ordered strip around the middle line.
Complexity: Time is Theta(n log n), Space is Theta(n).
Recurrence and analysis: T(n) = 2T(n/2) + Theta(n). By Master Theorem Case 2, it runs in Theta(n log n) time.
C. Experimental Results
Experiments were run for different input sizes (n = 100, 1,000, 10,000) and input types (Random, Sorted, Reverse-Sorted, Duplicate-Heavy). Results are saved in algorithm_experiment_results.csv.
Execution time and recursion depth: MergeSort and Closest Pair showed steady Theta(n log n) growth. QuickSort performed very fast on random inputs, and Deterministic Select showed linear growth as expected.
Plots: Time vs n shows curves for MergeSort, QuickSort, and Closest Pair, and a straight line for Deterministic Select. Recursion depth vs n shows logarithmic growth for all algorithms.
D. Discussion
Do the results match theoretical complexity? Yes, execution times and comparison counts match our theoretical expectations.
How does input structure affect performance? MergeSort is steady regardless of input order. QuickSort with a random pivot avoids worst-case slow-downs on sorted data. Deterministic Select stays consistently linear.
Why does smaller-first recursion help QuickSort? Recursing on the smaller side first ensures the recursion stack never goes deeper than O(log n), preventing stack overflow.
Why does Median-of-Medians guarantee O(n)? Because the pivot chosen is close to the true median, keeping total work linear.
Why is divide-and-conquer Closest Pair faster than O(n^2) for large inputs? Instead of checking every pair, it divides the space and only checks points within a small strip, reducing the combine step to linear time.
What practical factors affect performance? JVM JIT compilation speeds up code after warm-up, and using pre-allocated arrays helps avoid garbage collection slowdowns.
E. Reflection
Working on this assignment taught me a lot about how divide-and-conquer algorithms work in practice rather than just on paper. Writing custom code for sorting, selection, and closest pair helped me truly understand recursion and partitioning.
The hardest parts were handling edge cases like duplicate values during partitioning and keeping track of correct array indices. Overall, it was a great exercise in turning algorithmic theory into working Java code.
