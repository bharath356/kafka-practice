package com.example.kafkapractice.reader

import com.example.kafkapractice.domain.StockPrice


interface StockPriceReader {
    fun read(): List<StockPrice>
}