package LeetCode;

import java.util.Arrays;

class Solution_1498 {
    public int numSubseq(int[] nums, int target) {
        int mod = (int) Math.pow(10, 9) + 7;
        int count = 0, n = nums.length, left = 0, right = n - 1;

        int[] power = new int[nums.length];
        power[0] = 1;
        for (int i = 0; i < nums.length; i++) {
            power[i] = power[i - 1] * 2 % mod;
        }

        Arrays.sort(nums);
        while (left <= right) {
            if (nums[left] + nums[right] <= target) {
                count = (count + power[right - left]) % mod;
                left++;
            } else {
                right--;
            }
        }

        return count % mod;
    }
}
