package 将数字变成0的操作次数;

/**
 * @Description
 * @ClassName Solution
 * @Author Administrator
 * @date 2020.04.07 10:46
 */
public class Solution {
    public static void main(String[] args) {

        System.out.println(numberOfSteps(14));
    }
    public static int numberOfSteps(int num){

        int number = 0;
        while (num != 0){
            if (num% 2 == 0){
                num = num/2;
            }else {
                num = --num;
            }
            number ++;
        }
        return number;


    }
}
