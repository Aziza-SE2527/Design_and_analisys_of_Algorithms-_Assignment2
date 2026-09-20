# Design_and_analisys_of_Algorithms-_Assignment2
# Assignment 1: Design and Analysis of Algorithms

## A. Project Overview
* **Purpose of the assignment:** To implement, test, and analyze four classic divide-and-conquer algorithms in Java, measure their performance, and verify correctness against reference methods.
* **Implemented algorithms:**
  1. **MergeSort** (with auxiliary buffer and insertion sort cutoff)
  2. **QuickSort** (with randomized pivot and tail-recursion elimination)
  3. **Deterministic Select** (Median-of-Medians using groups of 5)
  4. **Closest Pair of Points** (divide-and-conquer with strip checking)

---

## B. Algorithm Analysis

### 1. MergeSort
* **How it works:** Splits the array into two halves recursively, sorts small sub-arrays using Insertion Sort when size is small, and merges them back together using a reusable auxiliary buffer.
* **Complexity:** Time $\Theta(n \log n)$, Space $\Theta(n)$ for the buffer.
* **Recurrence & Analysis:** $T(n) = 2T(n/2) + \Theta(n)$. By Master Theorem Case 2 ($a=2, b=2, d=1$), since $a = b^d$, the running time is $\Theta(n \log n)$.

### 2. QuickSort
* **How it works:** Picks a random pivot, partitions the array in-place, and uses smaller-first recursion (tail-recursion elimination) to keep the stack shallow.
* **Complexity:** Time $O(n \log n)$ average ($O(n^2)$ worst case), Space $O(\log n)$.
* **Recurrence & Analysis:** $T(n) = 2T(n/2) + \Theta(n) \implies O(n \log n)$ on average.

### 3. Deterministic Select (Median-of-Medians)
* **How it works:** Divides elements into groups of 5, finds each group's median, recursively finds the median of those medians to use as a good pivot, and searches only the necessary partition.
* **Complexity:** Time $\Theta(n)$ worst-case, Space $O(\log n)$.
* **Recurrence & Analysis:** $T(n) \le T(n/5) + T(7n/10) + \Theta(n)$. Because the subproblems sum to less than $n$ ($1/5 + 7/10 = 9/10$), the linear work dominates, giving $\Theta(n)$.

### 4. Closest Pair of Points
* **How it works:** Sorts points by x-coordinate, splits them into left and right halves, finds the closest distances in each half, and checks a narrow y-ordered strip around the middle line.
* **Complexity:** Time $\Theta(n \log n)$, Space $\Theta(n)$.
* **Recurrence & Analysis:** $T(n) = 2T(n/2) + \Theta(n)$. By Master Theorem Case 2, it runs in $\Theta(n \log n)$ time.

---

## C. Experimental Results
Experiments were run for different input sizes ($n = 100, 1,000, 10,000$) and input types (Random, Sorted, Reverse-Sorted, Duplicate-Heavy). Results are saved in `algorithm_experiment_results.csv`.

* **Execution Time & Recursion Depth:** MergeSort and Closest Pair showed steady $\Theta(n \log n)$ growth. QuickSort performed very fast on random inputs, and Deterministic Select showed linear growth as expected.
* **Plots:** 
  * *Time vs. n:* Shows curves for MergeSort, QuickSort, and Closest Pair, and a straight line for Deterministic Select.
  * *Recursion Depth vs. n:* Shows logarithmic growth ($O(\log n)$) for all algorithms, showing that recursion depths stayed small.

---

## D. Discussion
* **Do the results match theoretical complexity?** Yes. The execution times and comparison counts match our theoretical expectations ($\Theta(n \log n)$ for sorting/closest pair and $\Theta(n)$ for selection).
* **How does input structure affect performance?** MergeSort is steady regardless of input order. QuickSort with a random pivot avoids worst-case slow-downs on sorted data. Deterministic Select stays consistently linear.
* **Why does smaller-first recursion help QuickSort?** Recursing on the smaller side first ensures the recursion stack never goes deeper than $O(\log n)$, preventing stack overflow.
* **Why does Median-of-Medians guarantee $O(n)$?** Because the pivot chosen is guaranteed to be close to the true median, both recursive halves are reduced by a constant fraction, keeping total work linear.
* **Why is divide-and-conquer Closest Pair faster than $O(n^2)$ for large inputs?** Instead of checking every pair ($O(n^2)$), it divides the space and only checks points within a small strip, reducing the combine step to linear time.
* **What practical factors affect performance?** JVM JIT compilation speeds up code after warm-up, and using pre-allocated arrays helps avoid garbage collection slowdowns.

---

## E. Reflection
Working on this assignment taught me a lot about how divide-and-conquer algorithms work in practice rather than just on paper. Writing custom code for sorting, selection, and closest pair helped me truly understand recursion and partitioning. 

The hardest parts were handling edge cases like duplicate values during partitioning and keeping track of correct array indices. Overall, it was a great exercise in turning algorithmic theory into working Java code.
