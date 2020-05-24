package com.example.kafkapractice.dao

import com.example.kafkapractice.domain.StockPrice
import io.kotlintest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotlintest.shouldBe
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

private const val TEST = "TEST"

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class StockPriceDaoTest {

    @Autowired
    lateinit var stockPriceDao: StockPriceDao

    private fun StockPrice.toTestStockPrice() = this.copy(stockSymbol = "$TEST-$stockSymbol")

    @Test
    fun `test stock price inserts and selects`() {
        val localDate = LocalDate.of(2020, 5, 16)

        val ibmStockPrice = StockPrice("IBM", localDate, 90.00).toTestStockPrice()
        stockPriceDao.insertStockPrice(ibmStockPrice)

        val mfstStockPrice = StockPrice("MFST", localDate, 190.00).toTestStockPrice()
        stockPriceDao.insertStockPrice(mfstStockPrice)

        stockPriceDao.selectStockPrice(ibmStockPrice.stockSymbol, ibmStockPrice.date) shouldBe ibmStockPrice
        stockPriceDao.selectStockPrice(mfstStockPrice.stockSymbol, mfstStockPrice.date) shouldBe mfstStockPrice

        stockPriceDao.selectAllStockPrice() shouldContainExactlyInAnyOrder listOf(mfstStockPrice, ibmStockPrice)
    }
}