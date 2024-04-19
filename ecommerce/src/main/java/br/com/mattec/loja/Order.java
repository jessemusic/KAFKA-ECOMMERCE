package br.com.mattec.loja;

import java.math.BigDecimal;

public class Order {

    private final String userdId,orderId;
    private final BigDecimal amount;

    public Order(String userdId, String orderId, BigDecimal amount) {
        this.userdId = userdId;
        this.orderId = orderId;
        this.amount = amount;
    }
}
