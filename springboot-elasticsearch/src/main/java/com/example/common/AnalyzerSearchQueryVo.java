package com.example.common;

import lombok.Data;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 * @Description
 * @ClassName AnalyzerSearchQueryVo
 * @Author Administrator
 * @date 2020.05.27 14:02
 */
@Data
public class AnalyzerSearchQueryVo {
    private SearchQuery searchQuery;
    private Boolean isAnalyzer;
}
