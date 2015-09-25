package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tobias Jacobsen
 */
@WebServlet(name = "Rest", urlPatterns = {"/api/quote/*"})
public class Rest extends HttpServlet {

    private Map<Integer, String> quotes = new HashMap() {
        {
            put(1, "Friends are kisses blown to us by angels");
            put(2, "Do not take life too seriously. You will never get out of it alive");
            put(3, "Behind every great man, is a woman rolling her eyes");
        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doGet");
        //checks if request is random or by id 
        String[] parts = request.getRequestURI().split("/");

        if (parts.length == 5) {
            String parameter = parts[4];
            int id = 0;
            try {
                id = Integer.parseInt(parameter);
            } catch (NumberFormatException | NullPointerException e) {
                //its a string, get random,
                Random random = new Random();
                int randomId = random.nextInt(quotes.size()) + 1;
                String randomQuote = formatQuote(quotes.get(randomId));
                request.setAttribute("quote", randomQuote);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            //its an id, get by id
            if (quotes.containsKey(id)) {
                String quote = formatQuote(quotes.get(id));
                request.setAttribute("quote", quote);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");
        
        JsonObject quote = new JsonObject();
        int key = 1; //Get the second quote 
        quote.addProperty("quote", quotes.get(key));
        String jsonResponse = new Gson().toJson(quote);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {

    }

    private String formatQuote(String input) {
        String formattedString = "quote : " + input;
        return formattedString;
    }
}
