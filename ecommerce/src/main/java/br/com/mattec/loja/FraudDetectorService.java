package br.com.mattec.loja;


import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;
import java.util.Map;

public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class,
                Map.of());
        service.run();
    }

    private void parse(ConsumerRecord<String, Order> record) {
        System.out.println("---------------------------------------------");
        System.out.println("Processando0 new order, checking for fraud!");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ignoring
            throw new RuntimeException(e);
        }
        System.out.println("Order processed");
    }

}
