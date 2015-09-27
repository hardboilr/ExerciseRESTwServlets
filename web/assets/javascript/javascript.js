var id = 0;

$(document).ready(function () {
    //random quote of today
    getRandomQuote();

    //get new random quote btn
    $("#getRandomQuoteBtn").click(function () {
        $("#quoteOfToday").text(getRandomQuote());
    });

    //listen for changes in quote input field 
    $("#findQuote").keyup(function () {
        var input = $("#findQuote").val();
        console.log("input is: " + input);
        if (input !== "" && $.isNumeric(input)) {
            //is number, enable button
            $('#findQuoteBtn').prop('disabled', false);
            //get quote by id
            $("#findQuoteBtn").click(function () {
                getQuotebyId();
            });
        } else {
            //is text, disable button
            $('#findQuoteBtn').prop('disabled', true);
        }
    });

    //save changes to quote
    $("#putQuoteBtn").click(function () {
        if (id !== 0) {
            console.log("id is not 0!, but: " + id);
            updateQuote();
        } else {
            console.log("id is 0!: " + id);
            //do nothing
        }
    });

    //create quote
    $("#createQuote").submit(function (event) {
        event.preventDefault();
        createQuote();

    });
    
     //delete quote
    $("#deleteQuoteBtn").click(function () {
        deleteQuote();
    });
});

function createQuote() {
    var quote = new Object();
    quote.quote = $("#createQuote").find('input[name="createQuoteText"]').val();
    url = "http://localhost:8080/ExerciseRESTwServlets/api/quote";
    $.ajax({
        url: url,
        type: "POST",
        dataType: "json",
        data: JSON.stringify(quote),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function () {
            console.log("created succesfully");
        }
    });
}

function updateQuote() {
    var quote = new Object();
    quote.quote = $("#editQuote").find('input[name="updateQuoteText"]').val();
    url = "http://localhost:8080/ExerciseRESTwServlets/api/quote?id=" + id;
    $.ajax({
        url: url,
        type: "PUT",
        dataType: "json",
        data: JSON.stringify(quote),
        contentType: 'application/json',
        mimeType: 'application/json',
        success: function () {
            console.log("created succesfully");
        }
    });
}

function deleteQuote() {
    console.log("id is: " + id);
    var url = "http://localhost:8080/ExerciseRESTwServlets/api/quote?id=" + id;
    $("#findQuote").text("");
    $.ajax({
        url: url,
        method: "DELETE",
        success: function (data) {
            console.log("quote found: " + data.quote);
            $("#findQuote").val(data.quote);
            $('#findQuoteBtn').prop('disabled', true);
        }
    });
}

function getRandomQuote() {
    var url = "http://localhost:8080/ExerciseRESTwServlets/api/quote?id=" + "random";
    var quote = "";
    console.log("url is: " + url);
    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            quote = data.quote;
            $("#quoteOfToday").text(quote);
        }
    });
}

function getQuotebyId() {
    id = $("#findQuote").val();
    console.log("id is: " + id);
    var url = "http://localhost:8080/ExerciseRESTwServlets/api/quote?id=" + id;
    $("#findQuote").text("");
    $.ajax({
        url: url,
        method: "GET",
        success: function (data) {
            console.log("quote found: " + data.quote);
            $("#findQuote").val(data.quote);
            $('#findQuoteBtn').prop('disabled', true);
        }
    });
}

