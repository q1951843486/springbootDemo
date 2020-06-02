import com.arangodb.ArangoDB;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.ReadOptions;
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD;
import entity.TestJavaEntity;
import org.apache.spark.api.java.JavaSparkContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @Description
 * @ClassName SparkTest1
 * @Author Administrator
 * @date 2020.05.08 14:23
 */
public class SparkTest1 {private static final String DB = "_system";
    private static final String COLLECTION = "spark_test_col";
    private static ArangoDB arangoDB;
    private static JavaSparkContext sc;
    public static void main(String[] args) {
        ArangoJavaRDD<TestJavaEntity> rdd = ArangoSpark.load(sc, COLLECTION, new ReadOptions().database(DB),
                TestJavaEntity.class);

        System.out.println("=======================" + rdd.count());
        assertThat(rdd.count(), is(100L));
    }
}
