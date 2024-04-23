import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try(var orderDispatcher = new KafkaDispatcher<Order>()) {
            try (var emailDispatcher = new KafkaDispatcher<String>()) {
                for (var i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(Math.random() * 5000 + 1);
                    var email = userId + "@email.com";
                    var order = new Order(userId, orderId, amount, email);
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", userId, order);

                    var emailCode = " --> That's okay, we are processing your order!";
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", userId, emailCode);
                }
            }
        }
    }
}