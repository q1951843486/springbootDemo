package test;

import entity.Test1;
import entity.Test2;
import entity.Test3;
import entity.TestJavaEntity;

/**
 * @Description
 * @ClassName test.Test
 * @Author Administrator
 * @date 2020.05.11 09:56
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("444444444444");

        String sql = "select t2.id as id , t1.randName as randName ,t2.randNum as randNum from test2  t2 left join  test1  t1 on t1.randNum = t2.randNum";
        //test.SparkOperationArangoDbUtils.loadArangodbToSparkToArangodb("_system","test_col2"   , Test1.class,"test_coll","id == 1");
        test.SparkOperationArangoDbUtils.queryArangoDBToSparkBySql("_system","test_col2","test_col1",sql,Test2.class,"test_coll");
        //SparkOperationArangoDbUtils.loadArangoDbToSpark("_system","test_col3",Test3.class);
    }
}
