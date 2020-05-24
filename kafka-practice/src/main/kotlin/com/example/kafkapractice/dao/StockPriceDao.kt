package com.example.kafkapractice.dao

import com.example.kafkapractice.dao.mappers.StockPriceMapper
import com.example.kafkapractice.domain.StockPrice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDate


@Repository
class StockPriceDao(@Autowired val stockPriceMapper: StockPriceMapper) {
    fun insertStockPrice(stockPrice: StockPrice) {
        stockPriceMapper.insertStockPrice(stockPrice)
    }

    fun selectStockPrice(stockSymbol: String, date: LocalDate): StockPrice? {
        return stockPriceMapper.selectStockPrice(stockSymbol, date)
    }

    fun selectAllStockPrice(): List<StockPrice> {
        return stockPriceMapper.selectAllStockPrice()
    }

    fun deleteStockPrice(stockSymbol: String, date: LocalDate) {
        stockPriceMapper.deleteStockPrice(stockSymbol, date)
    }
}