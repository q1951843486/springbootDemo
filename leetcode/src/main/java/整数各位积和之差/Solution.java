package 整数各位积和之差;

/**
 * @Description
 * @ClassName Solution
 * @Author Administrator
 * @date 2020.04.08 09:43
 */
public class Solution {

    public static void main(String[] args) {
        System.out.println(subtractProductAndSum(234));

    }

    public static int subtractProductAndSum(int n) {

        int sum = 0;
        int product = 0;
        while (n!=0){
            product = 1;
            int number = n %10;
            sum +=  number;
            product *=number;
            n /=10;

        }
        return product -sum;
    }


}
