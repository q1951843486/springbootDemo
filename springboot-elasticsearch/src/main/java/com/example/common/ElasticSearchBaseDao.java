package com.example.common;

import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * elasticSearch 封装模板类
 * @Description
 * @ClassName ElasticSearchBaseDao
 * @Author Administrator
 * @date 2020.05.20 16:51
 */
public class ElasticSearchBaseDao<T> {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 插入单条数据
     * @param t
     * @return
     */
    public String insert(T t){
        IndexQuery indexQuery = new IndexQueryBuilder().withId(String.valueOf(getId(t))).withObject(t).build();
        String index = elasticsearchTemplate.index(indexQuery);
        return index;
    }


    /**
     * 根据id查询单条数据
     * @param id
     * @return
     */
    public T selectById(Long id){
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.termQuery("id",id)).build();
        List<T> list = elasticsearchTemplate.queryForList(searchQuery, getTClass());

        if (ObjectUtils.allNotNull(list)&&list.size()!=0){

            return list.get(0);


        }
        return null;


    }

    /**
     * 根据对象查询
     * @param t
     * @return
     */
    public List selectByObject(T t){
        AnalyzerSearchQueryVo vo = ClassToQuery(t);

        List<T> list = new ArrayList<>();

        list= elasticsearchTemplate.queryForList(vo.getSearchQuery(), getTClass());
        /*float maxScore = ts.getMaxScore();
        System.out.println(maxScore);*/

        return list;
    }





   /* public Page<Random> findAllByExampleInPage(PageRequest pageRequest, Random queryParam) {

        Example<Random> example = Example.of(queryParam);
        return randomRepository.findAll(pageRequest);

    }*/


    /**
     * 根据对象转换出查询条件
     * @param t
     * @return
     */
    protected AnalyzerSearchQueryVo ClassToQuery(T t){


        AnalyzerSearchQueryVo vo = new AnalyzerSearchQueryVo();
        vo.setIsAnalyzer(false);
        if (!InvokeUtils.notAllNull(t)){
            vo.setSearchQuery(new NativeSearchQueryBuilder().build());
            return vo;
        }
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        try {
            Class<T> tClass = getTClass();


            //拿到全部属性
            Field[] fields = tClass.getDeclaredFields();
            for (Field field:fields) {
                String name = field.getName();
                //将属性名字的首字母大写
                name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());
                Method m = tClass.getMethod("get"+name);
                Object invoke = m.invoke(t);
                if (ObjectUtils.allNotNull(invoke)){
                    //判断是否进行了分词 进行分词的不能使用termQuery精确匹配
                 /*   if (InvokeUtils.isAnalyzer(field)){
                        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchPhraseQuery(field.getName(),invoke.toString()));
                        vo.setIsAnalyzer(true);

                    }else {

                    }*/
                    nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery(field.getName(),invoke.toString()));
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        vo.setSearchQuery(nativeSearchQueryBuilder.build());
        return vo;
    }

    public static void main(String[] args) {
        Long aLong = Long.getLong("1588726800000");
        Long dateTime = new Long("1588726800000");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = simpleDateFormat.format(new Date(aLong));

        System.out.println(s);

    }

    /**
     * 获取泛型所使用类
     * @return
     */
    protected Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    /**
     * 获取泛型使用实体类的id 属性应在第一个
     * @param t
     * @return
     */
    public Long getId(T t){

        try{
            Class<? extends Object> tClass = t.getClass();

            //得到所有属性
            Field[] field = tClass.getDeclaredFields();

            /**
             * 这里只需要 id 这个属性，所以直接取 field[0] 这
             * 一个，如果id不是排在第一位，自己取相应的位置，
             * 如果有需要，可以写成for循环，遍历全部属性
             */

            //设置可以访问私有变量
            field[0].setAccessible(true);

            //获取属性的名字
            String name = field[0].getName();
            //将属性名字的首字母大写
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());

            //整合出 getId() 属性这个方法
            Method m = tClass.getMethod("get"+name);

            //调用这个整合出来的get方法，强转成自己需要的类型
            Long id = (Long)m.invoke(t);

            //成功通过 T 泛型对象取到具体对象的 id ！
            return id;
        }catch(Exception e){
            System.out.println("获取id失败");
            return null;
        }
    }

    /**
     * 获取泛型实体类使用的idCard 属性在第三个
     * @param t
     * @return
     */
    public String getIdCard(T t){

        try{
            Class<? extends Object> tClass = t.getClass();

            //得到所有属性
            Field[] field = tClass.getDeclaredFields();

            /**
             * 这里只需要 id 这个属性，所以直接取 field[0] 这
             * 一个，如果id不是排在第一位，自己取相应的位置，
             * 如果有需要，可以写成for循环，遍历全部属性
             */

            //设置可以访问私有变量
            field[2].setAccessible(true);

            //获取属性的名字
            String name = field[0].getName();
            //将属性名字的首字母大写
            name = name.replaceFirst(name.substring(0, 1), name.substring(0, 1).toUpperCase());

            //整合出 getId() 属性这个方法
            Method m = tClass.getMethod("get"+name);

            //调用这个整合出来的get方法，强转成自己需要的类型
            String id = (String)m.invoke(t);

            //成功通过 T 泛型对象取到具体对象的 id ！
            return id;
        }catch(Exception e){
            System.out.println("获取id失败");
            return null;
        }
    }


}
