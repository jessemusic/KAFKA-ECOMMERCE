import java.math.BigDecimal;

public class Order {

    private final String userdId,orderId;
    private final BigDecimal amount;
    private final String email;

    public Order(String userdId, String orderId, BigDecimal amount, String email) {
        this.userdId = userdId;
        this.orderId = orderId;
        this.amount = amount;
        this.email = email;
    }

    public String getUserdId() {
        return userdId;
    }

    public String getEmail() {
        return email;

    }
}
