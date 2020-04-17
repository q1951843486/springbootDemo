package com.example.modul;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Description
 * @ClassName Population
 * @Author Administrator
 * @date 2020.04.15 13:39
 */
@Data
@Document(indexName = "information",type = "population")
public class Population {


    @Id
    @Field(index = false,type = FieldType.Keyword,store = true)
    private long id;

    /**
     * 姓名 使用ik分词器 最大分词
     */
    @Field(index = true,type = FieldType.Text,analyzer = "ik_max_word")
    private String name;
    /**
     * 身份证号
     */
    @Field(index = true, type = FieldType.Keyword)
    private String idCard;

    /**
     * 资源表
     */
    @Field(index = false, type = FieldType.Keyword)
    private String sourceTableName;

    /**
     * 资源表id
     */
    @Field(index = false, type = FieldType.Keyword)
    private String sourceTableId;
}
