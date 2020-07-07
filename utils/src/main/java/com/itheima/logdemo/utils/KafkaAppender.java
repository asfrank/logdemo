package com.itheima.logdemo.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private String brokers;
    private String topic;

    KafkaTemplate kafkaTemplate ;


    @Override
    public void start() {

        Map props = new HashMap();
        //kafka的机器地址
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokers);
        //序列化方式
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        props.put(ProducerConfig.RETRIES_CONFIG,1);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG,10240);
        props.put(ProducerConfig.LINGER_MS_CONFIG,1);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG,1024000);

        kafkaTemplate = new KafkaTemplate(new DefaultKafkaProducerFactory(props));
        super.start();
    }

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        kafkaTemplate.send(topic,iLoggingEvent.getMessage());
    }

    public String getBrokers() {
        return brokers;
    }

    public void setBrokers(String brokers) {
        this.brokers = brokers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
