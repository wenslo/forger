package com.github.wenslo.forger.core.domain

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import java.io.Serializable
import java.util.*

class Pageable(var page: Int?, var size: Int?) : Pageable, Serializable {
    var sortField: String? = null
    var sortFieldList: List<String>? = null


    override fun getPageNumber(): Int {
        return page ?: 0
    }

    override fun hasPrevious(): Boolean {
        return (page ?: 0) > 0
    }

    override fun getSort(): Sort {
        return when {
            sortFieldList != null -> {
                val orders = ArrayList<Sort.Order>()
                for (sortField in sortFieldList!!) {
                    orders.add(populateOrder(sortField))
                }
                Sort.by(orders)
            }
            sortField != null -> {
                Sort.by(populateOrder(sortField!!))
            }
            else -> Sort.by("id")
        }
    }

    private fun populateOrder(sortField: String): Sort.Order {
        val (field, sortDirections) = sortField.split("_")
        return if (Objects.equals(sortDirections, "desc")) Sort.Order(
            Sort.Direction.DESC,
            field
        ) else Sort.Order(Sort.Direction.ASC, field)
    }


    override fun next(): Pageable {
        return Pageable(pageNumber + 1, pageSize)
    }

    override fun getPageSize(): Int {
        return size ?: 20
    }

    override fun getOffset(): Long {
        return (pageNumber * pageSize).toLong()
    }

    override fun first(): Pageable = Pageable(0, pageSize)

    override fun withPage(pageNumber: Int): Pageable {
        return Pageable(pageNumber,pageSize)
    }
    private fun previous(): Pageable {
        return if (pageNumber == 0) this else Pageable(
            pageNumber - 1,
            pageSize
        )
    }

    override fun previousOrFirst(): Pageable {
        return if (hasPrevious()) {
            previous()
        } else {
            first()
        }
    }


}