package ca.jrvs.practice.codingChallenge;

import java.util.HashSet;
import java.util.Set;

/**
 * ticket link:https://www.notion.so/Missing-Number-f26efb74fde34cc08e08b3807d5bf6c4
 */
public class MissingNumber {

    /**
     * sum all number
     *Big-O: O(n) time
     *       O(1) space
     * Justification: travers the list once
     */
    public int missingNumber1(int[] nums) {
        int i;
        int total = 0;
        int sum = 0;
        for (i= 0; i < nums.length; i++) {
            total += i;
            sum += nums[i];
        }
        return total + i - sum;
    }

    /**
     * using set
     *Big-O: O(n) time
     *       O(n) space
     * Justification: travers the list twice
     */
    public int missingNumber2(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
        }
        for (int i = 0; i <=nums.length; i++) {
            if (!set.contains(i)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Gauss' Formula
     *Big-O: O(n) time
     *       O(1) space
     * Justification: travers the list once
     */
    public int missingNumber3(int[] nums) {
        int sum = nums.length * (nums.length + 1) / 2;
        int total = 0;
        for (int num :nums) total += num;

        return sum - total;
    }
}
