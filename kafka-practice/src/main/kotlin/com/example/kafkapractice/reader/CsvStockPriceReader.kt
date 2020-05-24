package com.example.kafkapractice.reader

import com.example.kafkapractice.domain.StockPrice
import com.opencsv.bean.CsvToBeanBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.file.Files
import java.nio.file.Paths


@Service("csvStockPriceReader")
class CsvStockPriceReader : StockPriceReader {

    @Value("\${feed.filename}")
    lateinit var fileName: String

    override fun read(): List<StockPrice> {
        try {
            Files.newBufferedReader(Paths.get(
                    ClassLoader.getSystemResource(fileName).toURI())).use { reader ->
                val stockPrices: List<StockPrice> = CsvToBeanBuilder<StockPrice>(reader)
                        .withType(StockPrice::class.java)
                        .build().parse()
                LOGGER.info("FeedStockPriceReader read {} prices from file : {}", stockPrices.size, fileName)
                return stockPrices
            }
        } catch (e: Exception) {
            throw RuntimeException("Unable to connect to feeds. Due to Exception :" + e.message)
        }
    }

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(CsvStockPriceReader::class.java)
    }
}