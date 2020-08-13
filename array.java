//Sum(i~j) = PrefixSum[j + 1] - PrefixSum[i]
//leetcode 53. Maximum Subarray
public int maxSubArray(int[] nums) {
       int max = Integer.MIN_VALUE;
       int sum = 0;
       int min_sum = 0;
       int x = 9;
       string s = "sharon";
       for(int num : nums){
           sum += num;
           if(sum - min_sum > max){
               max = sum - min_sum;
           }
           if(sum < min_sum){
               min_sum = sum;
           }
       }
       return max;
}

//lintcode 604. Window Sum
//Given an array of n integers, and a moving window(size k),
//move the window at each iteration from the start of the array, find the sum of the element inside the window at each moving
//TC O(N), no need to add the whole sum, only head and tail
public int[] winSum(int[] nums, int k) {
       // write your code here
       if (nums == null || nums.length < k || k <= 0)
           return new int[0];

       int[] sums = new int[nums.length - k + 1];
       // 计算nums的前k个数之和，赋值给sum[0]
       for (int i = 0; i < k; i++)
           sums[0] += nums[i];
       for (int i = 1; i < sums.length; i++) {
           // 减去头部元素，加上尾部元素
           sums[i] = sums[i - 1] - nums[i - 1] + nums[i + k-1];
       }
       return sums;
   }

//lintcode 138. Subarray Sum
//Given an integer array, find a subarray where the sum of numbers is zero.
//Your code should return the index of the first number and the index of the last number.
public List<Integer> subarraySum(int[] nums) {
       // write your code here
       if(nums== null || nums.length == 0){
           return null;
       }
       List<Integer> ret = new ArrayList<>();
       Map<Integer, Integer> map = new HashMap<>();
       int pref_sum = 0;
       map.put(0, -1); //******
       for(int i = 0; i < nums.length; ++i){
           pref_sum += nums[i];
           if(map.containsKey(pref_sum)){
               int index1 = map.get(pref_sum) + 1;
               int index2 = i;
               ret.add(index1);
               ret.add(index2);
               return ret;
           }
           else{
               map.put(pref_sum, i);
           }
       }
       return ret;
   }
//lintcode 139. Subarray Sum Closest
//define a class pair to store the mapping of val and index
public class Solution {
    /*
     * @param nums: A list of integers
     * @return: A list of integers includes the index of the first number and the index of the last number
     */
     class pair{
         private int val;
         private int index;
         public pair(int _val, int _index){
             this.val = _val;
             this.index= _index;
         }
         public int getterVal(){
             return val;
         }
         public int getterIndex(){
             return index;
         }
     }

    public int[] subarraySumClosest(int[] nums) {
        // write your code here
        int[] ret = new int[2];
        if(nums == null || nums.length == 0){
            return ret;
        }
         PriorityQueue<pair> pq = new PriorityQueue<>(new Comparator<pair>(){
            public int compare(pair a, pair b){
                return a.getterVal() - b.getterVal();
            }
        });
        int pref_sum = 0;
        pq.offer (new pair(0, 0));
        for(int i = 1; i <= nums.length; ++i){
            pref_sum += nums[i - 1];
            pq.offer(new pair(pref_sum, i));
        }

        int min = Integer.MAX_VALUE;
        pair prev = pq.poll();
        while(!pq.isEmpty()){
            pair cur = pq.poll();
            int diff = cur.getterVal() - prev.getterVal();
            if(diff < min){
                min = diff;
                ret[0] = cur.getterIndex();
                ret[1] = prev.getterIndex();
                Arrays.sort(ret);
                ret[1] = ret[1] - 1;
            }
            prev = cur;
        }
        return ret;
    }
}

//leetcode 88. Merge Sorted Array
//Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
//from taol to head, two pointer
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m - 1;
    int j = n - 1;
    int k = nums1.length - 1;
    while(i >= 0 && j >= 0){
        if(nums1[i] >= nums2[j]){
            nums1[k--] = nums1[i--];
        }
        else{
            nums1[k--] = nums2[j--];
        }
    }
    while(i >= 0){
        nums1[k--] = nums1[i--];
    }
    while(j >= 0){
        nums1[k--] = nums2[j--];
    }
}
//leetcode 349. Intersection of Two Arrays
// hashset method TC O(n + m)
public int[] intersection(int[] nums1, int[] nums2) {
       //haseset method
       if(nums1 == null || nums2 == null){
           return null;
       }
       int length1 = nums1.length;
       int length2 = nums2.length;
       Set<Integer> set1 = new HashSet<>();
       //put elemtns in nums1 into a hash set
       for(int num : nums1){
           set1.add(num);
       }

       Set<Integer> result = new HashSet<>();
       for(int num : nums2){
           if(set1.contains(num) && !result.contains(num)){
               result.add(num);
           }
       }
       int size = result.size();
       int[] ret = new int[size];
       int index = 0;
       for(int num : result){
           ret[index++] = num;
       }
       return ret;
   }
   // version 2 sort & binary search
   //TC O((m + n)logn) SC O(1)
   public class Solution {
       /**
        * @param nums1 an integer array
        * @param nums2 an integer array
        * @return an integer array
        */
       public int[] intersection(int[] nums1, int[] nums2) {
           if (nums1 == null || nums2 == null) {
               return null;
           }

           HashSet<Integer> set = new HashSet<>();

           Arrays.sort(nums1);
           for (int i = 0; i < nums2.length; i++) {
               if (set.contains(nums2[i])) {
                   continue;
               }
               if (binarySearch(nums1, nums2[i])) {
                   set.add(nums2[i]);
               }
           }

           int[] result = new int[set.size()];
           int index = 0;
           for (Integer num : set) {
               result[index++] = num;
           }

           return result;
       }

       private boolean binarySearch(int[] nums, int target) {
           if (nums == null || nums.length == 0) {
               return false;
           }

           int start = 0, end = nums.length - 1;
           while (start + 1 < end) {
               int mid = (end - start) / 2 + start;
               if (nums[mid] == target) {
                   return true;
               }
               if (nums[mid] < target) {
                   start = mid;
               } else {
                   end = mid;
               }
           }

           if (nums[start] == target) {
               return true;
           }
           if (nums[end] == target) {
               return true;
           }

           return false;
       }
   }
// leetcode 4. Median of Two Sorted Arrays
//leftmanx <= rightminy && leftmaxy <= rightminx
public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        if(length1 > length2){
            return findMedianSortedArrays(nums2, nums1);
        }
        int start = 0;
        int end = length1;
        double ret = 0;

        while(start <= end){
            int partitionx = start + (end - start)/2;
            int partitiony = (length1 + length2 + 1)/2 - partitionx;

             int maxleft_x = partitionx == 0 ? Integer.MIN_VALUE : nums1[partitionx - 1];
             int maxleft_y = partitiony == 0 ? Integer.MIN_VALUE : nums2[partitiony - 1];
             int minright_x = partitionx == length1 ? Integer.MAX_VALUE : nums1[partitionx];
             int minright_y = partitiony == length2 ? Integer.MAX_VALUE : nums2[partitiony];


            if(maxleft_x <= minright_y && maxleft_y <= minright_x){
                //even
                if((length1 + length2)%2 == 0){
                    double candidate_right = Math.min(minright_x, minright_y);
                    double candidate_left = Math.max(maxleft_x, maxleft_y);
                    ret = (candidate_right + candidate_left)/2;
                    return ret;
                }
                //odd
                else{
                    ret = Math.max(maxleft_x, maxleft_y);
                    return ret;
                }
            }
            else if(maxleft_x > minright_y){
                end = partitionx - 1;
            }
            else{
                start = partitionx + 1;
            }
        }
        return ret;
    }
// TWO POINTERS


//leetcode 283. Move Zeroes
//Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements
//two pointer, left of ptr "left" are all non-zero and "left" points to the first Zeroes
public void moveZeroes(int[] nums) {
       if(nums == null || nums.length == 0){
           return;
       }

       int left = 0, right = 0;
       while(right <= nums.length -1){
           if(nums[right] != 0){
               int temp = nums[right];
               nums[right] = nums[left];
               nums[left] = temp;
               left++;
           }
           right++;
       }
  }
//leetcode 26. Remove Duplicates from Sorted Array
//return the number of unique elements in the array
//two pointer, hard to think!!!!!
public int removeDuplicates(int[] nums) {
    if(nums == null || nums.length == 0){
        return 0;
    }
    int left = 0;
    for(int i = 0; i < nums.length; ++i){
        if(nums[left] != nums[i]){
            int temp = nums[++left];
            nums[++left] = nums[i];
            nums[i] = temp;
        }
    }

    return left + 1;
}


int [] item;

boolean find(int testValue) {
   boolean found =false;

   int i=0;

  while ( (!found) && (i<item.length) ) {
    if (item[i] == testValue)
        found=true;
    else
        i++;
    }
  return found;
}

int [] item;

boolean find(int testValue) {
  int initialValue = item[item.length-1];
  item[item.length-1] = testValue;
  int i=0;

while (item[i] !=testValue) {
   i++;
 }

item[item.length-1] = initialValue;

return i< item.length-1 || testValue==initialValue;

}

 //Leetocde 125. Valid Palindrome
 public boolean isPalindrome(String s) {
        if(s == null){
            return true;
        }

        int length = s.length();
        int start = 0;
        int end = length - 1;
        while(start < end){
            while(start < end && !isAlphaNumeric(s.charAt(start))){
                start++;
            }
            while(start < end && !isAlphaNumeric(s.charAt(end))){
                end--;
            }
            if(Character.toLowerCase(s.charAt(start)) != Character.toLowerCase(s.charAt(end))){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

public boolean isAlphaNumeric(char c){
    return Character.isLetter(c) || Character.isDigit(c);
}

//lintcode 31. Partition Array
public int partitionArray(int[] nums, int k) {
       if(nums == null || nums.length == 0){
           return 0;
       }

       int left = 0, right = nums.length - 1;
       while (left <= right) {

           while (left <= right && nums[left] < k) {
               left++;
           }

           while (left <= right && nums[right] >= k) {
               right--;
           }

           if (left <= right) {
               int temp = nums[left];
               nums[left] = nums[right];
               nums[right] = temp;

               left++;
               right--;
           }
       }
       return left;
}

//lintcode 461. Kth Smallest Numbers in Unsorted Array
// QUICK SELECT
// three parts : 1. <= pivot, 2. >= pivot, 3. directly return nums[left - 1] or nums[right + 1]

public int kthSmallest(int k, int[] nums) {
       // write your code here
       if(nums == null || nums.length == 0){
           return -1;
       }
       return helper(nums, k, 0, nums.length - 1);
   }

   public int helper(int[] nums, int k, int start, int end){
       int pivot = nums[start + (end- start) / 2];
       int left = start;
       int right = end;

       while(left <= right){
           while(left <= right && nums[left] < pivot){
               left++;
           }
           while(left <= right && nums[right] > pivot){
               right--;
           }
           if(left <= right){
               int temp = nums[left];
               nums[left] = nums[right];
               nums[right] = temp;
               left++;
               right--;
           }
       }
       //2 cases
       if(start + k - 1 <= right){
           return helper(nums, k, start, right);
       }
       if(start + k - 1 >= left){
           return helper(nums, k - (left - start), left, end);
       }
       return nums[left - 1];
   }

//leetocde 215. Kth Largest Element in an Array
public int findKthLargest(int[] nums, int k) {
        if(nums == null || nums.length == 0){
            return -1;
        }
       return quickselect(nums, 0, nums.length - 1, k);
}

   public int quickselect(int[] nums, int start, int end, int k){
       int pivot = nums[start];
       int left = start;
       int right = end;
       while(left <= right){
           while(left <= right && nums[left] > pivot){   //here, the difference from kth smallest element!!!
               left++;
           }
           while(left <= right && nums[right] < pivot){
               right--;
           }
           if(left <= right){
               int temp = nums[left];
               nums[left] = nums[right];
               nums[right] = temp;
               left++;
               right--;
           }
       }

       if(start + k - 1 <= right){
           return quickselect(nums, start, right, k);
       }
       else if(start + k - 1 >= left){
           return quickselect(nums, left, end, k - (left - start));
       }
       else return nums[right + 1];
   }

//lintcode 373. Partition Array by Odd and Even
public void partitionArray(int[] nums) {
        // write your code here
        if(nums == null || nums.length == 0){
            return;
        }

        int start = 0;
        int end = nums.length - 1;
        while(start < end){
            while(start < end && nums[start] % 2 == 1){
                start++;
            }
            while(start < end && nums[end] % 2 == 0){
                end--;
            }
            if(start < end){
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
                start++;
                end--;
            }
        }
    }

//lintcode 144. Interleaving Positive and Negative Numbers
// revire !!!!!!!!!!!
public void rerange(int[] A) {
       // write your code here
         if(A == null || A.length == 0){
             return ;
         }
         int left = 0;
         int right = A.length -1 ;

         while(left <= right){
             while(left <= right && A[left] >=0){
                 left++;
             }
             while(left <= right && A[right] < 0){
                 right--;
             }
             if(left <= right){
                 int temp = A[left];
                 A[left] = A[right];
                 A[right] = temp;
                 left++;
                 right--;
             }
         }
         int i = 0;
         int j = A.length-1;

         System.out.println(j);
         if(left > A.length - left){
             interleave(A, 1, A.length-1);
         }
         else if(left < A.length - left){
             interleave(A, 0, A.length-2);
         }
         else{
             interleave(A, 0, A.length-1);
         }
   }
   private void interleave(int[] A, int i, int j){
         while(i <= j){
             int temp = A[i];
             A[i] = A[j];
             A[j] = temp;
             i+=2;
             j-=2;
         }
   }

//lintocde 49. Sort Letters by Case
public void sortLetters(char[] chars) {
       // write your code here
       if(chars == null || chars.length == 0){
           return;
       }
       int left = 0, right = chars.length - 1;
       while(left <= right){
           while(left <= right && Character.isLowerCase(chars[left])){
               left++;
           }
           while(left <= right && !Character.isLowerCase(chars[right])){
               right--;
           }
           if(left <= right){
               char temp = chars[left];
               chars[left] = chars[right];
               chars[right] = temp;
           }
       }
}

//leetcode 75. Sort Colors (Dutch National Flag Problem)
public void sortColors(int[] nums) {
        if(nums == null || nums.length == 0){
            return;
        }

        int left = 0;
        int right = nums.length - 1;
        int i = 0;
        while(i <= right){
            if(nums[i] == 0){
                swap(nums, left, i);
                left++;
                i++;
            }
            else if(nums[i] == 1){
                i++;
            }
            else{
                swap(nums, i, right);
                right--;
            }
        }
    }

public void swap(int[] nums, int a, int b){
    int temp = nums[a];
    nums[a] = nums[b];
    nums[b] = temp;
}

//lintcode 143. Sort Colors II (rainbow sort)
public void sortColors2(int[] colors, int k) {
       if (colors == null || colors.length == 0) {
           return;
       }
       rainbowSort(colors, 0, colors.length - 1, 1, k);
   }

   public void rainbowSort(int[] colors,
                           int left,
                           int right,
                           int colorFrom,
                           int colorTo) {
       if (colorFrom == colorTo) {
           return;
       }

       if (left >= right) {
           return;
       }

       int colorMid = (colorFrom + colorTo) / 2;
       int l = left, r = right;
       while (l <= r) {
           while (l <= r && colors[l] <= colorMid) {
               l++;
           }
           while (l <= r && colors[r] > colorMid) {
               r--;
           }
           if (l <= r) {
               int temp = colors[l];
               colors[l] = colors[r];
               colors[r] = temp;

               l++;
               r--;
           }
       }

       rainbowSort(colors, left, r, colorFrom, colorMid);
       rainbowSort(colors, l, right, colorMid + 1, colorTo);
   }

//lintcode 56. Two Sum
//use hashmap to obtain TC -> O(N), SC -> O(N)
public int[] twoSum(int[] numbers, int target) {
       // write your code here
       if(numbers == null || numbers.length == 0){
           return null;
       }
       Map<Integer, Integer> map = new HashMap<>();
       for(int i = 0; i < numbers.length; i++){
           if(map.containsKey(target - numbers[i])){
               return new int[]{map.get(target - numbers[i]), i};
           }
           map.put(numbers[i], i);
       }
       return new int[0];
   }

//leetcode 167. Two Sum II - Input array is sorted
//two pointer, start and end going toward the center
// Only works when the array is sorted !!!!!!!!!!!!
public int[] twoSum(int[] numbers, int target) {
       int[] ret = new int[2];
       if(numbers == null || numbers.length == 0){
           return null;
       }

       int start = 0;
       int end = numbers.length - 1;
       while(start < end){
           if(numbers[start] + numbers[end] == target){
               ret[0] = start + 1;
               ret[1] = end + 1;
               return ret;
           }
           else if(numbers[start] + numbers[end] < target){
               start++;
           }
           else{
               end--;
           }
       }
       return ret;
}

//leetcode 170. Two Sum III - Data structure design
class TwoSum {

    /** Initialize your data structure here. */
    public Map<Integer, Integer> map;
    public TwoSum() {
        map = new HashMap<Integer, Integer>();
    }

    /** Add the number to an internal data structure.. */
    public void add(int number) {
        if(map.containsKey(number)){
            map.put(number, map.get(number) + 1);
        }
        else{
            map.put(number, 1);
        }
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find(int value) {
        for(Map.Entry entry : map.entrySet()){
            int key1 = (int)entry.getKey();
            int left = value - key1;
            if((left == key1 && (int)entry.getValue()>1) || (left != key1 && map.containsKey(left))){
                return true;
            }
        }
        return false;
    }
 }

//lintcod 587. Two Sum - Unique pairs
public int twoSum6(int[] numbers, int target) {
        // write your code here
        int ret = 0;
        if(numbers == null || numbers.length == 0){
            return ret;
        }

        Arrays.sort(numbers);
        int start = 0;
        int end = numbers.length - 1;
        while(start < end){
            if(numbers[start] + numbers[end] == target){
                ret++;
                start++;
                end--;
                while(start < end && numbers[start] == numbers[start - 1]){  //deal with the duplicates here !!!!!!!!!!!
                    start++;
                }
                while(start < end && numbers[end] == numbers[end + 1]){
                    end--;
                }

            }
            else if(numbers[start] + numbers[end] < target){
                start++;
            }
            else{
                end--;
            }
        }
        return ret;
}

//leetcode 15. 3Sum
//pick a point c, and then do the two sum for the remaining array (from left to right)
public List<List<Integer>> threeSum(int[] nums) {
       List<List<Integer>> list = new ArrayList<>();
       if(nums == null || nums.length == 0){
           return list;
       }
       Arrays.sort(nums);

       int c = 0;
       for(int i = 0; i < nums.length - 2; ++i){
           if(i > 0 && nums[i] == nums[i - 1]){
               continue;
           }
           c = nums[i];
           int sum = -c;

           int start = i + 1;
           int end = nums.length - 1;
           while(start < end){
           if(nums[start] + nums[end] == sum){
               List<Integer> new_list = new ArrayList<>();
               new_list.add(c);
               new_list.add(nums[start]);
               new_list.add(nums[end]);
               list.add(new_list);
               start++;
               end--;
               while(start < end && nums[start] == nums[start - 1]){
                   start++;
               }
               while(start < end && nums[end] == nums[end + 1]){
                   end--;
               }
           }
           else if(nums[start] + nums[end] < sum){
               start++;
           }
           else{
               end--;
           }
       }
       }
       return list;
}

//lintcode 609. Two Sum - Less than or equal to target
public int twoSum5(int[] nums, int target) {
       // write your code here
       if(nums == null || nums.length == 0){
           return 0;
       }
       int count = 0;
       Arrays.sort(nums);

       int start = 0;
       int end = nums.length - 1;
       while(start < end){
           if(nums[start] + nums[end] <= target){ //*****
               count += end - start; //*******
               start++;
           }
           else{
               end--;
           }
       }
       return count;
}

//leetcode 1099. Two Sum Less Than K
//Given an array A of integers and integer K, return the maximum S
//such that there exists i < j with A[i] + A[j] = S and S < K. If no i, j exist satisfying this equation, return -1.
public int twoSumLessThanK(int[] A, int K) {
       if(A == null || A.length <2){
           return -1;
       }
       int max = Integer.MIN_VALUE;
       Arrays.sort(A);
       if((A[0] + A[1]) >= K){
           return -1;
       }
       int start = 0;
       int end = A.length - 1;
       while(start < end){
           if(A[start] + A[end] < K){
               max = Math.max(max, A[start] + A[end]);
               start++;
           }
           else{
               end--;
           }
       }
       return max;
}

//lintcode 533. Two Sum - Closest to target
//Return the absolute value of difference between the sum of the two integers and the target
public int twoSumClosest(int[] nums, int target) {
        // write your code here
        if(nums == null || nums.length < 2){
            return -1;
        }
        Arrays.sort(nums);
        int ret = Integer.MAX_VALUE;
        int start = 0;
        int end = nums.length - 1;
        while(start < end){
            int sum = nums[start] + nums[end];
            if(sum == target){
                return 0;
            }
            else if(sum < target){
                start++;
            }
            else{
                end--;
            }
            if(Math.abs(target-sum) < ret){
                ret = Math.abs(target-sum);
            }
        }
        return ret;
}

//leetcode 16. 3Sum Closest
//first pick a point c, and then do two sum to the ramingning array
public int threeSumClosest(int[] nums, int target) {
       if(nums == null || nums.length <3){
           return -1;
       }
       Arrays.sort(nums);
       int ret = nums[0] + nums[1] + nums[2];
       int c = 0;
       for(int i = 0; i < nums.length - 2; ++i){
           if(i > 0 && nums[i] == nums[i - 1]){
               continue;
           }
           c = nums[i];
           ret = twosum(nums, i + 1, nums.length - 1, ret, target, c);
       }
       return ret;
   }

   public int twosum(int[] nums, int start, int end, int ret, int target, int c){
       if(start >= end){
           return ret;
       }

       while(start < end){
           int total = nums[start] + nums[end] + c;
           if(total == target){
              return total;
           }
           else if(total < target){
               start++;
           }
           else{
               end--;
           }
           if(Math.abs(total - target) < Math.abs(ret - target)){
               ret = total;
           }
       }
       return ret;
   }

//leetcode 18. 4Sum
//similar to 3sum
public List<List<Integer>> fourSum(int[] nums, int target) {
       //a + b + c + d = 0
       List<List<Integer>> list = new ArrayList<>();
       if(nums == null || nums.length == 0){
           return list;
       }
       Arrays.sort(nums);
       int c, d = 0;
       for(int i = 0; i < nums.length - 3; ++i){
           d = nums[i];
           if(i > 0 && nums[i] == nums[i - 1]){
               continue;
           }
           for(int j = i + 1; j < nums.length - 2; ++j){
               if(j > i + 1 && nums[j] == nums[j - 1]){
                   continue;
               }
               c = nums[j];
               int target1 = target - c - d;
               twosum(nums, j + 1, nums.length - 1, target1, c, d, list);
           }
       }
       return list;
   }

   public void twosum(int[] nums, int start, int end, int target, int c, int d, List<List<Integer>> list){
       if(start >= end){
           return;
       }
       while(start < end){
           if(nums[start] + nums[end] == target){
               List<Integer> new_list = new ArrayList<>();
               new_list.add(d);
               new_list.add(c);
               new_list.add(nums[start]);
               new_list.add(nums[end]);
               list.add(new_list);
               start++;
               end--;
               while(start < end && nums[start] == nums[start - 1]){
                   start++;
               }
               while(start < end && nums[end] == nums[end + 1]){
                   end--;
               }
           }
           else if(nums[start] + nums[end] < target){
               start++;
           }
           else{
               end--;
           }
       }
  }

//lintcode 610. Two Sum - Difference equals to target
//Given an array of integers, find two numbers that their difference equals to a target value.
//where index1 must be less than index2. Please note that your returned answers (both index1 and index2) are NOT zero-based

//because we need index here, we use Hashmap, hence do not need to sort beforehead
public int[] twoSum7(int[] nums, int target) {
        // write your code here
        if(nums == null || nums.length < 2){
            return null;
        }
        int[] ret = new int[2];
        //  num      index
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < nums.length; ++i){
            if(map.containsKey(target + nums[i])){   //case 1 -> a - b = target
                ret[0] = map.get(target + nums[i]) + 1;
                ret[1] = i + 1;
                return ret;
            }
             else if(map.containsKey( nums[i] - target)){ //case 2 -> b - a = target
                ret[0] = map.get( nums[i] - target) + 1;
                ret[1] = i + 1;
                return ret;
            }

            else{
                map.put(nums[i], i);
            }
        }
        return ret;
}
