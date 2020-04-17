package com.example.modul;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Description
 * @ClassName Random
 * @Author Administrator
 * @date 2020.04.15 13:52
 */
@Data
@Document(collection = "random")
public class Random {
    private String id;
    private String xm;
    private String zjh;


}
