package 左旋转字符串;

/**
 * @Description
 * @ClassName Solution
 * @Author Administrator
 * @date 2020.04.07 10:10
 */
public class Solution {
    public static void main(String[] args) {
        String s = "abcdefg";
        System.out.println(reverseLeftWords(s,2));
    }
    public static String reverseLeftWords(String s ,int n){
       /* char[] chars = s.toCharArray();
        char[] newChar = new char[chars.length];
        for (int i = 0; i <chars.length-n; i++) {
            newChar[i] = chars[i+n];
        }
        for (int i = 0; i < n; i++) {
            newChar[chars.length-n + i] = chars[i];
        }
        return new String(newChar);*/
        //偷懒 嘿嘿
        //return s.substring(n) + s.substring(0,n);



        /*char[] chars = new char[s.length()];
        for (int i = 0; i <s.length()-n ; i++) {
            chars[i] = s.charAt(i+n);
        }
        for (int i = 0; i <n ; i++) {
            chars[s.length()-n +i] =s.charAt(i);
        }
        return new String(chars);*/


        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i <s.length() ; i++) {
            int m = (i+n) % s.length();
            buffer.append(s.charAt(m));
        }
        return buffer.toString();
    }

}
