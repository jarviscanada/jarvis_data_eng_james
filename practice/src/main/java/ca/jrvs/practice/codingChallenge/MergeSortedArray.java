package ca.jrvs.practice.codingChallenge;

import java.util.Arrays;

/**
 * ticket link:https://www.notion.so/Merge-Sorted-Array-33f482f739a64a02ba6e263aac8f1a8f
 */
public class MergeSortedArray {

    /**
     * nlogn
     *Big-O: O(nlogn) time
     *       O(1) space
     * Justification: sorting cost nlogn
     */
    public int[] mergeSortedArray1(int[] arr1,int m, int[] arr2, int n) {
        for (int i = 0; i < n ; i++) {
            arr1[m+i] = arr2[i];
        }
        Arrays.sort(arr1);
        return arr1;
    }


}
