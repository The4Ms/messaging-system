<%-- 
    Document   : viewMessage
    Created on : Dec 23, 2014, 5:07:42 PM
    Author     : Mohamed Kamal
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="ia.project.mmm.model.Message"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<% String username = (String)session.getAttribute("username"); 
   Message message = (Message)request.getAttribute("message");
%>

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
    <nav class="navbar navbar-inverse">
      <div class="container">
        <div class="clear"></div>
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="index.jsp">BigM</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="index.jsp?view=inbox">Inbox</a></li>
            <li><a href="index.jsp?view=drafts">Drafts</a></li>
            <li><a href="index.jsp?view=trash">Trash</a></li>
            <li><a href="index.jsp?view=sent">Sent</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="profile.jsp">${fullname}</a></li>
            <li><a href="logout">Logout</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->

    <div class="container-fluid">
      <div class="col-md-4 col-md-offset-4">

        
        <div>
            <a class="btn btn-lg btn-info" href="doMessage?type=trash&id=${message.id}">trash</a>
            <a class="btn btn-lg btn-info" href="doMessage?type=delete&id=${message.id}">delete for ever</a>
            <a class="btn btn-lg btn-info" href="doMessage?type=reply&id=${message.id}">reply</a>
            <a class="btn btn-lg btn-info" href="doMessage?type=forward&id=${message.id}">forward</a>
        </div>

        <p>
            From: ${message.sender.username}
        </p>
        <p>
            To:
            <c:forEach var="receiver" items="${message.receivers}">
                 ${receiver.username},
            </c:forEach>
        </p>
        <p>
            Sent Date: ${message.sentDate}
        </p>
        <p>
            Subject: ${message.subject}
        </p>
        
        <p>
            Body:<br>
            <%= message.getBody().replace("\n", "<br>") %>
        </p>

      </div>
    </div>
  </body>
</html>
