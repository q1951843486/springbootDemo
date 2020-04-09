package 猜数字;

/**
 * @Description
 * @ClassName Solution
 * @Author Administrator
 * @date 2020.04.07 10:36
 */
public class Solution {

    public static void main(String[] args) {

    }

    public static int game(int[] guess,int[] answer){

        int number =0;
        for (int i = 0; i <guess.length ; i++) {
            if (guess[i] == answer[i]){
                number++;
            }
        }
        return number;
    }


}
