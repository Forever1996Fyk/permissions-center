package com.ah.cloud.permissions.api.controller.elasticsearch;

import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.elsticsearch.domain.doc.search.EsPageResult;
import com.ah.cloud.permissions.elsticsearch.domain.doc.search.EsQueryDTO;
import com.ah.cloud.permissions.elsticsearch.domain.example.ItemDoc;
import com.ah.cloud.permissions.elsticsearch.domain.example.ItemQuery;
import com.ah.cloud.permissions.elsticsearch.infrastructure.service.search.impl.ExampleElasticSearchServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 17:05
 **/
@RestController
@RequestMapping("/es")
public class ElasticSearchExampleController {
    @Resource
    private ExampleElasticSearchServiceImpl exampleElasticSearchService;

    @PostMapping("/search")
    public ResponseResult<EsPageResult<ItemDoc>> search(@RequestBody EsQueryDTO<ItemQuery> queryDTO) {
        EsPageResult<ItemDoc> result = exampleElasticSearchService.commonPageSearch(queryDTO);
        return ResponseResult.ok(result);
    }
}
