<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String to = (String)request.getAttribute("to");
    if(to == null) to = "";
    
    String body = (String)request.getAttribute("body");
    if(body == null) body = "";
    
    String subject = (String)request.getAttribute("subject");
    if(subject == null) subject = "";
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
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel="stylesheet">
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
            <li><a href="profile.jsp">${username}</a></li>
            <li><a href="logout">Logout</a></li>
          </ul>
        </div><!-- /.nav-collapse -->
      </div><!-- /.container -->
    </nav><!-- /.navbar -->

    <div class="container-fluid">
      <div class="col-md-4 col-md-offset-4">

        <form action="sendMessage" method="POST">
          <h1 class="page-header">New Message</h1>
          <div class="form-group">
            <input name="to" type="email1" class="form-control" placeholder="To">
          </div>

          <div class="form-group">
            <input name="subject" type="subject" class="form-control" placeholder="Subject">
          </div>

          <div class="form-group">
            <textarea name="body" class="form-control" rows="3" placeholder="Body"></textarea>
          </div>
          <button  class="btn btn-primary pull-right" type="submit">Send Message</button>
          
            <button  class="btn btn-info" type="button">Save to drafts</button>
        </form>

      </div>
    </div>
  </body>
</html>
