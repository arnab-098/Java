package LeetCode;

import java.util.HashMap;

// LeetCode 1865

class FindSumPairs {

    private HashMap<Integer, Integer> counter;
    private int[] nums1, nums2;

    public FindSumPairs(int[] nums1, int[] nums2) {
        this.nums1 = nums1;
        this.nums2 = nums2;

        counter = new HashMap<>();
        for (int num : nums2) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
        }
    }

    public void add(int index, int val) {
        counter.put(nums2[index], counter.get(nums2[index]) - 1);
        nums2[index] += val;
        counter.put(nums2[index], counter.getOrDefault(nums2[index], 0) + 1);
    }

    public int count(int tot) {
        int result = 0;
        for (int num : nums1) {
            result += counter.getOrDefault(tot - num, 0);
        }
        return result;
    }
}
