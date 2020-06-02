import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.ReadOptions;
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD;
import entity.Test1;
import entity.Test2;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * @Description
 * @ClassName SparkTest
 * @Author Administrator
 * @date 2020.05.07 15:34
 */
public class SparkTest {
    public static void main(String[] args) {

        SparkConf sparkConf = new SparkConf()
                .set("arangodb.hosts", "10.200.1.183:8529").set("database","_system");
        SparkSession sparkSession = SparkSession.builder().master("spark://10.200.1.193:7077").appName("ArangoSparkConnectorLk").config(sparkConf).getOrCreate();
        //JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        //List<Test1> docs = (List<Test1>) arangoTemplate.findAll(Test1.class);
        //JavaRDD<Test1> test1JavaRDD = javaSparkContext.parallelize(docs);
        //ArangoSpark.save(test1JavaRDD,"Test1",new WriteOptions().database("test"));
        //ArangoSpark.load(javaSparkContext, "test1", Test1.class);

        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkSession.sparkContext());

        ReadOptions readOptions =  new ReadOptions().database("_system").collection("test2");

        ArangoJavaRDD<Test2> dataset = ArangoSpark.load(javaSparkContext, "test2",readOptions, Test2.class);

        Dataset<Row> dataFrame = sparkSession.createDataFrame(dataset, Test2.class);

        ReadOptions readOptions1 = new ReadOptions().database("_system").collection("test1");
        ArangoJavaRDD<Test1> test1ArangoJavaRDD = ArangoSpark.load(javaSparkContext, "test1", readOptions1, Test1.class);
        Dataset<Row> dataset1 = sparkSession.createDataFrame(test1ArangoJavaRDD, Test1.class);

        dataFrame.createOrReplaceTempView("test2");
        dataset1.createOrReplaceTempView("test1");


        Dataset<Row> joinDataset = sparkSession.sql("select t1.randName as t1name,t2.randName as t2name,t1.randNum as t1num,t2.randNum as t2num from test2 t2 join test1 t1 on t1.randNum = t2.randNum");
        System.out.println(joinDataset.count());

        joinDataset.printSchema();
        joinDataset.show();
        javaSparkContext.close();
    }
}
