package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final String NO_EXIST = "Quote does not exist";
    private final String INVALID_ID = "Invalid id";
    Scanner jsonScanner;
    JsonObject quoteObj;
    String jsonResponse = "";

    private Map<Integer, String> quotes = new HashMap() {
        {
            put(1, "Friends are kisses blown to us by angels");
            put(2, "Do not take life too seriously. You will never get out of it alive");
            put(3, "Behind every great man, is a woman rolling her eyes");
        }
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String stringId = request.getParameter("id");
        jsonResponse = "";

        if (stringId.equals("random")) {
            //get random quote
            Random random = new Random();
            int randomId = random.nextInt(quotes.size() - 1) + 1;
            String randomQuote = quotes.get(randomId);
            quoteObj = new JsonObject();
            quoteObj.addProperty("quote", randomQuote);
            jsonResponse = new Gson().toJson(quoteObj);
        } else {
            //assume its an id, get by id
            int id = 0;
            try {
                id = Integer.parseInt(stringId);
                if (quotes.containsKey(id) && id != 0) {
                    String quote = quotes.get(id);
                    quoteObj = new JsonObject();
                    quoteObj.addProperty("quote", quote);
                    jsonResponse = new Gson().toJson(quoteObj);
                } else {
                    String quote = NO_EXIST;
                    quoteObj = new JsonObject();
                    quoteObj.addProperty("quote", quote);
                    jsonResponse = new Gson().toJson(quoteObj);

                }
            } catch (NumberFormatException | NullPointerException e) {
                //its a string, get index 0 quote
                String quote = INVALID_ID;
                quoteObj = new JsonObject();
                quoteObj.addProperty("quote", quote);
                jsonResponse = new Gson().toJson(quoteObj);
            }

        }
        //send jSon
        sendJson(response, jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            jsonScanner = new Scanner(request.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Rest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String json = "";
        while (jsonScanner.hasNext()) {
            json += jsonScanner.nextLine();
        }
        JsonObject newQuote = new JsonParser().parse(json).getAsJsonObject();
        String quote = newQuote.get("quote").getAsString();

        int id = 0;
        boolean foundEmpty = false;
        for (Map.Entry<Integer, String> entry : quotes.entrySet()) {
            id = entry.getKey();
            String value = entry.getValue();
            if (value.equals("")) { //found empty spot 
                quotes.put(id, quote);
                foundEmpty = true;
            }
        }
        if (!foundEmpty) {
            id = quotes.size() + 1;
            quotes.put(id, quote);
        }

        //Response
        quoteObj = new JsonObject();
        quoteObj.addProperty("id", id);
        quoteObj.addProperty("quote", quote);
        jsonResponse = new Gson().toJson(quoteObj);
        sendJson(response, jsonResponse);

    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {
        String stringId = request.getParameter("id");
        int id = parseInt(stringId);
        try {
            jsonScanner = new Scanner(request.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Rest.class.getName()).log(Level.SEVERE, null, ex);
        }
        String json = "";
        while (jsonScanner.hasNext()) {
            json += jsonScanner.nextLine();
        }
        JsonObject newQuote = new JsonParser().parse(json).getAsJsonObject();
        String quote = newQuote.get("quote").getAsString();
        quotes.put(id, quote);

        //Response
        quoteObj = new JsonObject();
        quoteObj.addProperty("id", id);
        quoteObj.addProperty("quote", quote);
        jsonResponse = new Gson().toJson(quoteObj);
        sendJson(response, jsonResponse);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String stringId = request.getParameter("id");
        int id = parseInt(stringId);
        String quote = quotes.get(id);
        quotes.put(id, "");

        //Response
        quoteObj = new JsonObject();
        quoteObj.addProperty("quote", quote);
        jsonResponse = new Gson().toJson(quoteObj);
        sendJson(response, jsonResponse);
    }

    private void sendJson(HttpServletResponse response, String jSonString) {
        response.setContentType(
                "application/json");
        try (PrintWriter pw = response.getWriter()) {
            pw.print(jSonString);
            System.out.println("Response was: " + jSonString);
            pw.flush();
        } catch (IOException ex) {
            Logger.getLogger(Rest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
