package 有多少小于当前数字的数字;

/**
 * @Description
 * @ClassName Solution
 * @Author Administrator
 * @date 2020.04.07 13:38
 */
public class Solution {

    public static void main(String[] args) {

    }

    public static int[] smallerNumberThanCurrent(int[] nums){
        int[] newInts= new int[nums.length];
        int number = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j <nums.length ; j++) {
                if (nums[i]>nums[j]){
                    number ++;
                }
            }
            newInts[i] = number;
            number =0;
        }


        return newInts;
    }
}
