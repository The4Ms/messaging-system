<%-- 
    Document   : OperationFail.jsp
    Created on : Dec 23, 2014, 6:43:16 PM
    Author     : Mohamed Kamal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>The requested operation failed</h1>
        <p>
            What went wrong: ${errorMsg}
        </p>
        <a href="javascript:history.back()">go back</a>
    </body>
</html>
