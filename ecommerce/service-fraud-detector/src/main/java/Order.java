import java.math.BigDecimal;

public class Order {

    private final String userdId,orderId;
    private final BigDecimal amount;

    public Order(String userdId, String orderId, BigDecimal amount) {
        this.userdId = userdId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getUserdId() {
        return userdId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "userdId='" + userdId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
