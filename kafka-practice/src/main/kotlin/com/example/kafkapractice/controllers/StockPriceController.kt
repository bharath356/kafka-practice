package com.example.kafkapractice.controllers

import com.example.kafkapractice.service.StockPriceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/service/stockPriceService")
@RestController
class StockPriceController(@Autowired val stockPriceService: StockPriceService) {

    @GetMapping
    fun startProcess(){
        return stockPriceService.startProcess()
    }
}