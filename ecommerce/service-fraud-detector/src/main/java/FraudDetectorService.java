import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FraudDetectorService {
    public static void main(String[] args) {
        var fraudService = new FraudDetectorService();
        var service = new KafkaService<>(FraudDetectorService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER",
                fraudService::parse,
                Order.class,
                Map.of());
        service.run();
    }
    private final KafkaDispatcher<Order> orderKafkaDispatcher = new KafkaDispatcher<>();

    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException {
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
        Order order = record.value();
        if(isFraud(order)){
            // verificando se é maior que 3000
            System.out.println("Order is a fraud!!!!");
            orderKafkaDispatcher.send("ECOMMERCE_ORDER_REJECTED", order.getUserdId(), order);
        }else {
            System.out.println("Approved no sistema!!!! -> " + order);
            orderKafkaDispatcher.send("ECOMMERCE_ORDER_APPROVED", order.getUserdId(), order);
        }

    }

    private static boolean isFraud(Order order) {
        return order.getAmount().compareTo(new BigDecimal("3000")) >= 0;
    }

}
