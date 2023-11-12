<%-- 
    Document   : index
    Created on : 28 sept. 2023, 15:52:22
    Author     : Tafitasoa-P15B-140
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Karapanondro</title>
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/index.css">
    </head>
    <body>
        <div class="center-container">
            <div class="form-container">
                <h1>CIN</h1>
                <form action="<%= request.getContextPath() %>/Huhuservlet" method="POST">
                    <input type="number" name="cin" value="1001">
                        <%
                            String errorMessage = (String) request.getAttribute("errorMessage");
                            if (errorMessage != null) {
                        %>
                        <div class="error-message" style="color:red">
                                <%= errorMessage %>
                            </div>
                        <% } %>
                    <input type="submit" value="OK">
                </form>
            </div>
        </div>
    </body>
</html>
