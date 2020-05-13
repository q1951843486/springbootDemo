package test;

import com.alibaba.fastjson.JSONObject;
import com.arangodb.Protocol;
import com.arangodb.spark.ArangoSpark;
import com.arangodb.spark.ReadOptions;
import com.arangodb.spark.WriteOptions;
import com.arangodb.spark.rdd.api.java.ArangoJavaRDD;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @ClassName test.SparkOperationArangoDbUtils
 * @Author Administrator
 * @date 2020.05.09 11:23
 */
@Slf4j
public class SparkOperationArangoDbUtils {


    /**
     * 关闭 sparkSession
     * @param sparkSession
     */
    public static void closeJavaSparkSession(SparkSession sparkSession){
        sparkSession.close();
    }

    /**
     * 关闭sparkContext
     * @param javaSparkContext
     */
    public static void closeJavaSparkContext(JavaSparkContext javaSparkContext){
        javaSparkContext.close();
    }

    /**
     *
     *
     *
     * @return sparkSession对象
     */
    public static SparkSession getJavaSparkSession(){
        return  SparkSession
                .builder()
                .appName("test_JavaSparkSQL")
                .config("spark.some.config.option", "some-value")
                .getOrCreate();
    }

    /**
     *
     *
     * @param appName 提交任务名
     * @return sparkSession对象
     */
    public static SparkSession getJavaSparkSession(String appName){
        return  SparkSession
                .builder()
                .appName(appName)
                .config("spark.some.config.option", "some-value")
                .getOrCreate();
    }

    /**
     *
     *
     * @param appName 提交的任务名
     * @param sparkConf spark 配置
     * @return sparkSession对象
     */
    public static SparkSession getJavaSparkSession(String appName, SparkConf sparkConf){
        return  SparkSession
                .builder()
                .appName(appName)
                .config(sparkConf)
                .getOrCreate();
    }
    public static JavaSparkContext getJavaSparkContext() {
        SparkConf conf = new SparkConf(false).setMaster("local").set("spark.executor.memory", "4g")
                .setAppName("test");
        JavaSparkContext sc = new JavaSparkContext(conf);
        // 设置日志输出等级 有一些启动信息的日志没必要看
        sc.setLogLevel("WARN");
        return sc;
    }


    /**
     *
     * 从arangodb 中加载数据到spark
     * @param DB arangodb 数据库名
     * @param LoadCollection arangodb 读取的集合名
     * @param loadArangoDBClass arangodb 读取的集合映射的java对象
     * @param <T> 映射的java对象
     */
    public static <T> void  loadArangoDbToSpark(String DB,String LoadCollection,Class<T> loadArangoDBClass){
        SparkSession sparkSession = getJavaSparkSession();
        JavaSparkContext sc = getJavaSparkContext();
        long start = System.currentTimeMillis();
        ArangoJavaRDD<T> rdd = ArangoSpark.load(sc, LoadCollection, new ReadOptions().hosts("10.200.1.183:8529").database(DB).protocol(Protocol.HTTP_JSON),
                loadArangoDBClass);
        long end = System.currentTimeMillis();
        log.info("☆☆☆☆☆☆☆ spark读取arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",loadArangoDBClass,rdd.count(),(end - start));
        closeJavaSparkContext(sc);
        closeJavaSparkSession(sparkSession);
    }

    /**
     *
     * Description: spark写入arangodb数据
     * Title: loadSparkToArangodb
     * @param <T>
     * @param DB 数据库
     * @param collection  集合名
     * @param docs 集合数据
     */
    public <T> void loadSparkToArangodb(String DB,String collection,List<T> docs) {
        JavaSparkContext sc = getJavaSparkContext();
        long start = System.currentTimeMillis();
        JavaRDD<T> documents = sc.parallelize(docs);
        ArangoSpark.save(documents, collection, new WriteOptions().database(DB).protocol(Protocol.HTTP_JSON));
        long end = System.currentTimeMillis();
        log.info("☆☆☆☆☆☆☆ spark写入arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",collection,documents.count(),(end - start));
        closeJavaSparkContext(sc);
    }



    /**
     *
     * Description: 读取arangodb数据到spark写入新的集合
     * Title: loadArangodbToSparkToArangodb1
     * @param <T>
     * @param DB 查询数据库
     * @param queryCollection  查询集合
     * @param querycollectionClass 集合映射java类
     * @param writeCollection 写入的集合
     */

    public <T> void loadArangodbToSparkToArangodb(String DB,String queryCollection,Class<T> querycollectionClass,String writeCollection) {
        JavaSparkContext sc = getJavaSparkContext();
        long start = System.currentTimeMillis();
        ArangoJavaRDD<T> rdd = ArangoSpark.load(sc, queryCollection, new ReadOptions().database(DB),
                querycollectionClass);
        long end = System.currentTimeMillis();
        log.info("☆☆☆☆☆☆☆ spark读取arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",queryCollection,rdd.count(),(end - start));
        start = System.currentTimeMillis();
        ArangoSpark.save(rdd, writeCollection, new WriteOptions().database(DB).protocol(Protocol.HTTP_JSON));
        end = System.currentTimeMillis();
        log.info("☆☆☆☆☆☆☆ spark写入arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",writeCollection,rdd.count(),(end - start));
        closeJavaSparkContext(sc);
    }

    /**
     *
     * Description: 读取arangodb数据到spark，通过spark sql查询结果 再把结果写入新的集合
     * Title: loadArangodbToSparkToArangodb1
     * @param <T>
     * @param DB 查询数据库
     * @param queryCollection  查询集合
     * @param querycollectionClass 集合映射java类
     * @param writeCollection 写入的集合
     * @param sqlConditions  sql语句
     */
    public static  <T>  void loadArangodbToSparkToArangodb(String DB,String queryCollection,Class<T> querycollectionClass,String writeCollection,String sqlConditions) {
        JavaSparkContext sc = getJavaSparkContext();
        sc.addJar("/home/spark/spark-2.4.5/test-1.0-SNAPSHOT-jar-with-dependencies.jar");
        long start = System.currentTimeMillis();
        ArangoJavaRDD<T> rdd = ArangoSpark.load(sc, queryCollection, new ReadOptions().hosts("10.200.1.183:8529").database(DB).protocol(Protocol.HTTP_JSON),
                querycollectionClass);
        long end = System.currentTimeMillis();
        log.info("☆☆☆☆☆☆☆ spark读取arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",queryCollection,rdd.count(),(end - start));
        SparkSession spark = getJavaSparkSession();
        Dataset<Row> dataset = spark.createDataFrame(rdd, querycollectionClass);
        dataset.createOrReplaceTempView(writeCollection);
        dataset = spark.sql("SELECT * FROM " + writeCollection + (StringUtils.isEmpty(sqlConditions) ? "" : " where " + sqlConditions));
        //dataset.show();
        System.out.println(dataset.count());
       /* String[] columns = dataset.columns();
        List<Row> rows = dataset.collectAsList();
        List<T> jsonArray = new ArrayList<T>();*/
		/*for (Row row : rows) {
			JSONObject json = new JSONObject();
			for (int i = 0; i < columns.length; i++) {
				json.put(columns[i], row.get(i));
			}
			jsonArray.add((T) JSONObject.toBean(json, querycollectionClass));
		}*/
        /*for (Row r : rows) {

            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i <columns.length ; i++) {

                jsonObject.put(columns[i], r.get(i));
            }
            jsonArray.add(jsonObject.toJavaObject(querycollectionClass));

        }*/

        List jsonArray = RowToJavaBean(dataset, querycollectionClass);
        System.out.println(jsonArray.get(0).toString());
        JavaRDD<T> documents = sc.parallelize(jsonArray);
        start = System.currentTimeMillis();
        ArangoSpark.save(documents, writeCollection, new WriteOptions().hosts("10.200.1.183:8529").database(DB).protocol(Protocol.HTTP_JSON));
        end = System.currentTimeMillis();
        log.info("☆☆☆☆☆☆☆ spark写入arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",writeCollection,documents.count(),(end - start));
        closeJavaSparkSession(spark);
        closeJavaSparkContext(sc);
    }


    public static <T> List queryArangoDBToSparkBySql(String DB,String primaryTable,String assoviationTable,String sql,Class<T> queryClass,String resultTable){


        JavaSparkContext sc = getJavaSparkContext();
        sc.addJar("/home/spark/spark-2.4.5/test.jar");
        long start = System.currentTimeMillis();
        //System.out.println(start);
        ArangoJavaRDD<T> rdd = ArangoSpark.load(sc, primaryTable, new ReadOptions().hosts("10.200.1.183:8529").database(DB).protocol(Protocol.HTTP_JSON),
                queryClass);
        //System.out.println(rdd.count());
        long end = System.currentTimeMillis();
        log.info("☆☆☆☆☆☆☆ spark读取arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",queryClass,rdd.count(),(end - start));
        SparkSession spark = getJavaSparkSession();
        Dataset<Row> dataset = spark.createDataFrame(rdd, queryClass);
        dataset.createOrReplaceTempView("test2");

        end = System.currentTimeMillis();
        System.out.println(end -start);
        //System.out.println(System.currentTimeMillis());
        ArangoJavaRDD<T> rdd1 = ArangoSpark.load(sc, assoviationTable, new ReadOptions().hosts("10.200.1.183:8529").database(DB).protocol(Protocol.HTTP_JSON),
                queryClass);
        log.info("☆☆☆☆☆☆☆ spark读取arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",queryClass,rdd1.count(),(end - start));
        //System.out.println(rdd1.count());
        Dataset dataset1 =spark.createDataFrame(rdd1,queryClass);
        dataset1.createOrReplaceTempView("test1");
        start = System.currentTimeMillis();
        System.out.println(start - end);

        Dataset<Row> joinDataset = spark.sql(sql);

        end = System.currentTimeMillis();
        System.out.println(end -start);
        //System.out.println(System.currentTimeMillis());
        System.out.println(joinDataset.count());
        List jsonArray = RowToJavaBean(joinDataset, queryClass);
        System.out.println(jsonArray.get(0).toString());
        JavaRDD<T> documents = sc.parallelize(jsonArray);
        start = System.currentTimeMillis();
        System.out.println(start -end);
        ArangoSpark.save(documents, resultTable, new WriteOptions().hosts("10.200.1.183:8529").database(DB).protocol(Protocol.HTTP_JSON));
        end = System.currentTimeMillis();
        System.out.println(end -start);
        log.info("☆☆☆☆☆☆☆ spark写入arangodb数据 ☆☆☆☆☆☆☆  collection:{} **** 数据量: {} **** takeTime: {}毫秒",queryClass,documents.count(),(end - start));
        closeJavaSparkSession(spark);
        closeJavaSparkContext(sc);
        return null;
    }


    /**
     * 将dataSet 转换为java对象
     * @param dataset
     * @param querycollectionClass
     * @param <T>
     * @return
     */
    public  static <T> List RowToJavaBean(Dataset<Row> dataset,Class<T> querycollectionClass){
        List<T> list = new ArrayList<>();

        String[] columns = dataset.columns();
        List<Row> rows = dataset.collectAsList();
        List<Row> jsonArray = new ArrayList<Row>();
		/*for (Row row : rows) {
			JSONObject json = new JSONObject();
			for (int i = 0; i < columns.length; i++) {
				json.put(columns[i], row.get(i));
			}
			jsonArray.add((T) JSONObject.toBean(json, querycollectionClass));
		}*/
        for (Row r : rows) {

            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < columns.length; i++) {

                jsonObject.put(columns[i], r.get(i));
            }
            list.add((T) jsonObject.toJavaObject((Type) querycollectionClass));

        }
        return list;
    }

}
