package com.example.kafkapractice.writer

import com.example.kafkapractice.domain.StockPrice

interface StockPriceWriter {
    fun write(stockPrice: StockPrice)
}