package com.github.wenslo.forger.data.es.service

import com.github.wenslo.forger.core.domain.Pageable
import com.github.wenslo.forger.data.es.dto.AggregationDto
import org.apache.commons.beanutils.PropertyUtils
import org.elasticsearch.index.query.QueryBuilder
import org.elasticsearch.search.aggregations.AggregationBuilders
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder
import org.elasticsearch.search.sort.SortBuilders
import org.elasticsearch.search.sort.SortOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder
import org.springframework.data.elasticsearch.core.query.Query
import org.springframework.stereotype.Component


@Component
class ElasticsearchService {
    @Autowired
    lateinit var elasticsearchRestTemplate: ElasticsearchRestTemplate

    /**
     * 聚合
     * @fieldName 字段名称
     * @num 返回聚合条数
     * @index 索引
     */
    fun aggs(
        fieldName: String,
        num: Int = 10,
        index: IndexCoordinates,
        queryBuilder: List<QueryBuilder> = emptyList()
    ): MutableList<AggregationDto> {
        val aggregationBuilder = AggregationBuilders.terms(fieldName).field("$fieldName.keyword").size(num)

        val builder = NativeSearchQueryBuilder()
        if (queryBuilder.isNotEmpty()) {
            for (query in queryBuilder) {
                builder.withQuery(query)
            }
        }

        val build = builder.addAggregation(aggregationBuilder) // 添加聚合条件
            .withPageable(PageRequest.of(0, 1)) //符合查询条件的文档分页（不是聚合的分页）
            .build()
        val search = elasticsearchRestTemplate.search(build, HashMap::class.java, index)
        val result = mutableListOf<AggregationDto>()
        search.aggregations?.toList()?.forEach {
            val trans = it as ParsedStringTerms
            trans.buckets.forEach { ac ->
                result.add(AggregationDto(ac.keyAsString, ac.docCount))
            }
        }
        return result
    }

    /**
     * 分页
     */
    fun <T> page(queryBuilder: List<QueryBuilder> = emptyList(), pageable: Pageable, clazz: Class<T>): Page<T> {
        val builder = pageQueryPopulate(queryBuilder, pageable)
        val build = builder.build()
        build.trackTotalHits = true

        val hits = elasticsearchRestTemplate.search(build, clazz)
        val content = mutableListOf<T>()
        hits.searchHits.forEach {
            content.add(it.content)
        }
        return PageImpl(content, pageable, hits.totalHits)
    }

    private fun pageQueryPopulate(
        queryBuilder: List<QueryBuilder>,
        pageable: Pageable
    ): NativeSearchQueryBuilder {
        val builder = NativeSearchQueryBuilder()
        if (queryBuilder.isNotEmpty()) {
            for (query in queryBuilder) {
                builder.withQuery(query)
            }
        }
        builder.withPageable(PageRequest.of(pageable.page!!.toInt(), pageable.size!!.toInt()))
        val iterator = pageable.sort.iterator()
        while (iterator.hasNext()) {
            val order = iterator.next()
            val sortBuilder = SortBuilders.fieldSort(order.property)
            if (order.isAscending) {
                sortBuilder.order(SortOrder.ASC)
            } else {
                sortBuilder.order(SortOrder.DESC)
            }
            builder.withSorts(sortBuilder)
        }
        return builder
    }

    /**
     * 分页 带高亮的那种
     */
    fun <T> pageWithHighlight(
        queryBuilder: List<QueryBuilder> = emptyList(),
        pageable: Pageable,
        clazz: Class<T>,
        highlightFieldList: List<String>
    ): PageImpl<T> {

        val builder = pageQueryPopulate(queryBuilder, pageable)
        builder.withHighlightBuilder(buildHighlightQuery(highlightFieldList))
        val build = builder.build()
        build.trackTotalHits = true
        val hits = elasticsearchRestTemplate.search(build, clazz)
        val content = mutableListOf<T>()
        hits.searchHits.forEach {
            val highlightFields = it.highlightFields
            highlightFields.forEach { highlightField ->
                if (highlightField.value.size > 1) {
                    PropertyUtils.setProperty(it.content, highlightField.key, highlightField.value)
                } else {
                    PropertyUtils.setProperty(it.content, highlightField.key, highlightField.value.joinToString(""))
                }

            }
            content.add(it.content)
        }
        return PageImpl(content, pageable, hits.totalHits)
    }

    private fun buildHighlightQuery(highlightFieldList: List<String>): HighlightBuilder {
        val highlightBuilder = HighlightBuilder()
        for (highlightField in highlightFieldList) {
            highlightBuilder.field(highlightField)
        }
        return highlightBuilder
    }

    /**
     * 根据条件查询该核心下总共有多少条数据
     * @index 索引
     */
    fun count(query: Query, index: IndexCoordinates): Long {
        return elasticsearchRestTemplate.count(query, index)
    }

}