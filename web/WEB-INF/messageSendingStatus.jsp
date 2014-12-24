<%-- 
    Document   : messageSendingStatus
    Created on : Dec 22, 2014, 6:36:28 PM
    Author     : Mohamed Kamal
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% String[] failedUsers = (String[])request.getAttribute("failedUsers"); %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>BigM</title>

    <!-- Bootstrap core CSS -->
    <link href="bootstrap.min.css" rel="stylesheet">
  </head>

  <body>
    
    <div class="container-fluid">
      <div class="col-md-4 col-md-offset-4" style="margin-top:50px">

        <% if(failedUsers.length == 0){ 
            out.print(" <div class=\"alert alert-success\"><h1>Message Sent Successfully</h1><a class=\"btn btn-success pull-right\" href=\"index.jsp\">back to home page</a> <div class=\"clearfix\"></div></div>");
           }
            else { 
            
                out.print(" <div class=\"alert alert-danger\">");
                    out.print("The message was sent to the correct users but couldn't find the correct users: ");

                    for(int i=0; i<failedUsers.length; i++){
                        out.print(failedUsers[0] + ",");
                    }
                out.print("<a class=\"btn btn-danger pull-right\" href=\"index.jsp\">back to home page</a> <div class=\"clearfix\"></div></div>");
           }
        %>
           

      </div>
    </div>
  </body>
</html>
