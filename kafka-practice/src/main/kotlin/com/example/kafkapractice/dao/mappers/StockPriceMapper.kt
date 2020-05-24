package com.example.kafkapractice.dao.mappers

import com.example.kafkapractice.domain.StockPrice
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import java.time.LocalDate

@Mapper
interface StockPriceMapper {
    @Insert("INSERT INTO stock_price(stock_symbol, date, price) VALUES ( #{stockSymbol}, #{date}, #{price} )")
    fun insertStockPrice(stockPrice: StockPrice)

    @Select("SELECT stock_symbol AS stockSymbol, date, price FROM stock_price WHERE stock_symbol = #{stockSymbol} AND date = #{date}")
    fun selectStockPrice(stockSymbol: String, date: LocalDate): StockPrice

    @Select("SELECT stock_symbol as stockSymbol, date, price FROM stock_price")
    fun selectAllStockPrice(): List<StockPrice>

    @Delete("DELETE FROM stock_price WHERE stock_symbol = #{stockSymbol} AND date = #{date}")
    fun deleteStockPrice(stockSymbol: String, date: LocalDate)
}