package com.example.kafkapractice.service

import com.example.kafkapractice.dao.StockPriceDao
import com.example.kafkapractice.domain.StockPrice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StockPriceService(@Autowired val stockPriceDao: StockPriceDao) {

    fun selectAllStockPrice(): List<StockPrice> {
        return stockPriceDao.selectAllStockPrice()
    }
}