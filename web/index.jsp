<%-- 
    Document   : index
    Created on : Sep 24, 2015, 9:06:13 AM
    Author     : Tobias Jacobsen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>Test</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div>
                    <h1>Quote of today</h1>
                    <p id="quoteOfToday"></p>
                    <br>
                    <input type="button" id="getRandomQuoteBtn" value="Get new random quote">
                </div>
                <br>
                <div>
                    <form id="createQuote">
                        Create quote:<br>
                        <input type="text" name="createQuoteText" placeholder="insert quote" required>
                        <input type="submit" id="createQuoteBtn" value="Create">
                    </form>
                </div>
                <br>
                <div>
                    <form id="editQuote">
                        Find quote:<br>
                        <input type="text" name="updateQuoteText" id="findQuote" placeholder="insert quote id" required>
                        <input type="button" id="findQuoteBtn" value="Find">
                        <input type="button" id="putQuoteBtn" value="Save changes">
                        <input type="button" id="deleteQuoteBtn" value="Delete quote">

                    </form>
                    <p id="test"></p>
                </div>
            </div>
        </div>
        <div>
            <script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
            <link rel="stylesheet" property="" type="text/boostrap" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
            <link rel="stylesheet" property="" type="text/boostrap" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
            <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
            <script src="assets/javascript/javascript.js"></script> 
        </div>

    </body>
</html>
