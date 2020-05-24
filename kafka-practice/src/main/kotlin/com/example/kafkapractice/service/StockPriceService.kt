package com.example.kafkapractice.service

import com.example.kafkapractice.reader.CsvStockPriceReader
import com.example.kafkapractice.writer.StockPriceWriter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class StockPriceService(@Autowired
                        @Qualifier("csvStockPriceReader")
                        val stockPriceReader: CsvStockPriceReader,
                        @Autowired
                        val stockPriceWriter: StockPriceWriter) {

    fun startProcess() {
        val stockPrices = stockPriceReader.read()

        stockPrices.forEach { stockPrice ->
            stockPriceWriter.write(stockPrice)
        }
    }
}