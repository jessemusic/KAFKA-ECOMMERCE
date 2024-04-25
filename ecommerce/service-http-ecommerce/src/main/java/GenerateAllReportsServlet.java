import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class GenerateAllReportsServlet extends HttpServlet {
    private final KafkaDispatcher<String> batchDispatcher = new KafkaDispatcher<>();


    @Override
    public void destroy() {
        super.destroy();
        batchDispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{

            batchDispatcher.send("SEND_MESSAGE_TO_ALL_USERS", "USER_GENERATE_READING_REPORT","USER_GENERATE_READING_REPORT");


            System.out.println("Gerando relatório para todos os usuários! ");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("Relatório gerado!");
        }catch (ExecutionException e){
            throw new ServletException(e);

        }catch (InterruptedException e){
            throw new ServletException(e);
        }
    }

}
