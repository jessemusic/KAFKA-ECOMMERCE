import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class newOrderServlet extends HttpServlet {
    private final KafkaDispatcher<Order> orderDispatcher = new KafkaDispatcher<>();
    private final KafkaDispatcher<String> emailDispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        super.destroy();
        orderDispatcher.close();
        emailDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                try{
                    var email = req.getParameter("email");
                    var orderId = UUID.randomUUID().toString();
                    var amount = new BigDecimal(req.getParameter("amount"));

                    var order = new Order(orderId, amount, email);
                    orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order);

                    var emailCode = " --> That's okay, we are processing your order!";
                    emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode);

                    System.out.println("Nova conta processada com sucesso! ");
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.getWriter().println("Requisição enviar com sucesso!");
                }catch (ExecutionException e){
                    throw new ServletException(e);

                }catch (InterruptedException e){
                    throw new ServletException(e);
                }
            }

}

