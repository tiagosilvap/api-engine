package com.billing.config;

import org.bson.types.Decimal128;
import org.springframework.core.convert.converter.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

@Configuration
@EnableMongoRepositories(basePackages = "com.billing.infrastructure.persistence")
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new BigDecimalWriter(),
                new BigDecimalReader()
        ));
    }

    @WritingConverter
    static class BigDecimalWriter implements Converter<BigDecimal, Decimal128> {
        public Decimal128 convert(BigDecimal source) {
            return new Decimal128(source.setScale(2, RoundingMode.HALF_UP));
        }
    }

    @ReadingConverter
    static class BigDecimalReader implements Converter<Decimal128, BigDecimal> {
        public BigDecimal convert(Decimal128 source) {
            return source.bigDecimalValue().setScale(2, RoundingMode.HALF_UP);
        }
    }
}

