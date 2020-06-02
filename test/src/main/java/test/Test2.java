package test;

import com.arangodb.Protocol;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.ReadOptions;
import entity.Test1;
import org.apache.spark.Partition;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.List;

/**
 * @Description
 * @ClassName Test2
 * @Author Administrator
 * @date 2020.05.14 15:06
 */
public class Test2 {
    public static void main(String[] args) {
        JavaSparkContext sc = SparkOperationArangoDbUtils.getJavaSparkContextDev();
        long timeMillis = System.currentTimeMillis();
        JavaRDD<Test1> rdd = ArangoSpark.load(sc, "test_col2", new ReadOptions().hosts("10.200.1.183:8529").database("_system").protocol(Protocol.HTTP_JSON),
                Test1.class);
        JavaRDD<Test1> rdd1 = ArangoSpark.load(sc, "test_col1", new ReadOptions().hosts("10.200.1.183:8529").database("_system").protocol(Protocol.HTTP_JSON),
                Test1.class);
        //List<Partition> partitions = rdd.partitions();
        //partitions.stream().forEach(partition -> System.out.println(partition));
        //rdd1.partitions().stream().forEach(partition -> System.out.println(partition));
        JavaPairRDD<String, Test1> stringTest1JavaPairRDD = rdd.mapToPair(new PairFunction<Test1, String, Test1>() {
            @Override
            public Tuple2<String, Test1> call(Test1 test1) throws Exception {
                return new Tuple2<>(test1.getRandNum(),test1);
            }
        });
        JavaPairRDD<String, Test1> stringTest1JavaPairRDD1 = rdd1.mapToPair(new PairFunction<Test1, String, Test1>() {
            @Override
            public Tuple2<String, Test1> call(Test1 test1) throws Exception {
                return new Tuple2<>(test1.getRandNum(),test1);
            }
        });
        JavaPairRDD<String, Tuple2<Test1, Test1>> join = stringTest1JavaPairRDD.join(stringTest1JavaPairRDD1);
        long timeMillis1 = System.currentTimeMillis();
        System.out.println("sql 懒加载时间" +(timeMillis1 - timeMillis));
        System.out.println(join.count());

        System.out.println("总计用时 " + (System.currentTimeMillis() - timeMillis));
    }
}
