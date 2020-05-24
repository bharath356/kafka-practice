package com.example.kafkapractice.config

import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan("com.example.kafkapractice.dao.mappers")
class MapperConfiguration