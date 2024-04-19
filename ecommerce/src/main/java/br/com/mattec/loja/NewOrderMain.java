package br.com.mattec.loja;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

    try(var dispatcher = new KafkaDispatcher()){
        for(var i=0;i<10;i++) {
            var key = UUID.randomUUID().toString();
            var value = key + ",554433,12300003000";
            dispatcher.send("ECOMMERCE_NEW_ORDER", key, value);

            var email = key + " --> That's okay, we are processing your order!";
            dispatcher.send("ECOMMERCE_SEND_EMAIL", key, email);
        }

        }
    }


}