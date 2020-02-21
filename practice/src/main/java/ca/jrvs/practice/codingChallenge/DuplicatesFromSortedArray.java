package ca.jrvs.practice.codingChallenge;

/**
 * ticket link: https://www.notion.so/Duplicates-from-Sorted-Array-d70187c81fb840ea9524500576ccf720
 */
public class DuplicatesFromSortedArray {

    /**
     * Brute force
     *Big-O: O(n) time
     *       O(1) space
     * Justification: at most travers the array
     */
    public int countDuplicate(int[] list) {
        if (list.length == 0) {
            return 0;
        }

        int i = 0;
        for (int j = 0; j < list.length; j++) {
            if (list[i] != list[j]) {
                i++;
                list[i] = list[j];
            }
        }
        return i + 1;
    }
}
