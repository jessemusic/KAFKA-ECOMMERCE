import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class CreateUserService {


    private final Connection connection;

    CreateUserService() throws SQLException {
        String url = "jdbc:sqlite:database/users_database.db";
        connection = DriverManager.getConnection(url);
        try {
            connection.createStatement().execute("create table Users (" +
                    "uuid varchar(200) primary key," +
                    "email varchar(200))");
        }catch (SQLException e){
            //Neste caso a slq pode estar errado!!!
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws SQLException {
        var createUserService = new CreateUserService();
        var service = new KafkaService<>(CreateUserService.class.getSimpleName(), "ECOMMERCE_NEW_ORDER",
                createUserService::parse,
                Order.class,
                Map.of());
        service.run();
    }

    private void parse(ConsumerRecord<String, Order> record) throws SQLException {
        System.out.println("---------------------------------------------");
        System.out.println("Processando new order, checking for user!");
        System.out.println(record.value());
        Order order = record.value();
        if(isNewUser(order.getEmail())){
            insertNewUser(order.getUserdId() ,order.getEmail());
            System.out.println("Usuario adicionado! " + UUID.randomUUID() + "@email.com");

        }
    }

    private void insertNewUser(String uuid, String email) throws SQLException {
        var insert = connection.prepareStatement("insert into Users( uuid, email) " +
                "values (?,?)");
        insert.setString(1, uuid);
        insert.setString(2, email);
        insert.execute();
    }

    private boolean isNewUser(String email) throws SQLException {
        var exists = connection.prepareStatement("select uuid from Users " +
                "where email = ? limit 1"
                );
        exists.setString(1,email);
        var results = exists.executeQuery();
        return !results.next();
    }


}
