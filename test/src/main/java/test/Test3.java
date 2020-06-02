package test;

import org.apache.spark.api.java.JavaSparkContext;

/**
 * @Description
 * @ClassName Test3
 * @Author Administrator
 * @date 2020.05.14 15:56
 */
public class Test3 {
    public static void main(String[] args) {

        JavaSparkContext javaSparkContext = SparkOperationArangoDbUtils.getJavaSparkContext();
    }
}
