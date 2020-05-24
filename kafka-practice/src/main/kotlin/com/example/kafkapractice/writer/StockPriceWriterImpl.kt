package com.example.kafkapractice.writer

import com.example.kafkapractice.dao.StockPriceDao
import com.example.kafkapractice.domain.StockPrice

import com.example.kafkapractice.serialize.StockPriceSerializer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Service
class StockPriceWriterImpl : StockPriceWriter {

    @Autowired
    lateinit var stockPriceDao: StockPriceDao

    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var server: String

    @Value("\${kafka.topic.stock_price}")
    lateinit var topicName: String

    @Value("\${writeStockPriceToKafka}")
    var writeStockPriceToKafka: Boolean = false

    lateinit var kafkaProducer: KafkaProducer<String, StockPrice>

    @PostConstruct
    fun init() {
        val kafkaProducerProperties = Properties()
        kafkaProducerProperties[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = server
        kafkaProducerProperties[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java.name
        kafkaProducerProperties[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StockPriceSerializer::class.java.name
        kafkaProducer = KafkaProducer<String, StockPrice>(kafkaProducerProperties)
    }

    override fun write(stockPrice: StockPrice) {
        stockPriceDao.insertStockPrice(stockPrice);
        if (writeStockPriceToKafka) {
            val producerRecord = ProducerRecord(topicName, stockPrice.stockSymbol, stockPrice)

            kafkaProducer.send(producerRecord) { metadata: RecordMetadata, exception: Exception? ->
                if (exception == null) {
                    LOGGER.info("StockPrice {} was successfully sent. Received metadata : Topic : {} \n Partition : {} \n Offset : {} \n Timestamp : {}",
                            stockPrice, metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp())
                } else {
                    LOGGER.error("Failed to send to topic - {}", exception.message)
                }
            }
        }
    }

    @PreDestroy
    fun cleanUp() {
        kafkaProducer.flush()
        kafkaProducer.close()
    }


    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(StockPriceWriterImpl::class.java)
    }

}