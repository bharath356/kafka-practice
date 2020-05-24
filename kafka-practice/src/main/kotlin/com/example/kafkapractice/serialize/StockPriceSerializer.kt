package com.example.kafkapractice.serialize

import com.example.kafkapractice.domain.StockPrice
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.apache.kafka.common.serialization.Serializer
import org.slf4j.LoggerFactory


class StockPriceSerializer : Serializer<StockPrice> {

    override fun serialize(topic: String, data: StockPrice): ByteArray {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())

        try {
            return objectMapper.writeValueAsString(data).toByteArray()
        } catch (e: JsonProcessingException) {
            LOGGER.error("Error serializing Stock Price {}", e.message)
        }
        return ByteArray(0)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(StockPriceSerializer::class.java)
    }
}