package ca.jrvs.practice.codingChallenge;

/**
 * ticket link:https://www.notion.so/Remove-Element-0975bd7e3110489cb32c8f56af1b3da6
 */
public class RemoveElement {

    /**
     * Two pointer
     *Big-O: O(n) time
     *       O(1) space
     * Justification: loop through the list
     */
    public int remove(int[] nums, int val) {
        int i = 0;
        int j = nums.length - 1;
        int count = nums.length;
        while (i < j) {
            if (nums[i] == val) count--;
            if (nums[j] == val) count--;
            i++;
            j--;
        }
        return count;
    }
}
