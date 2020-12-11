<%@ page import="java.net.InetAddress" %>
<html>
<head>
  <title>Echoing HTML Request Parameters</title>
</head>
<body>
  <h1> 
    <marquee direction="left" behavior="scroll" style="color:maroon;font-weight:bold;font-size:20px">Conratulations !!! You have successfully completed the Cloud Elevation Training for "Introduction to AWS Network Solution Archotecture and Getting Hands-On with DevSecops...See you at the next Training Session on Containerization with Kubernetes ....</marquee>
  </h1>
  <h3><% InetAddress ia = InetAddress.getLocalHost();
    out.println("<!-- hostname  " + ia.getHostName() +  " -->");
   %></h3>
  <h3>Choose an author:</h3>
  <form method="get">
    <input type="checkbox" name="author" value="Tan Ah Teck">Tan
    <input type="checkbox" name="author" value="Mohd Ali">Ali
    <input type="checkbox" name="author" value="Kumar">Kumar
    <input type="submit" value="Query">
  </form>
 
  <%
  String[] authors = request.getParameterValues("author");
  if (authors != null) {
  %>
    <h3>You have selected author(s):</h3>
    <ul>
  <%
      for (int i = 0; i < authors.length; ++i) {
  %>
        <li><%= authors[i] %></li>
  <%
      }
  %>
    </ul>
    <a href="<%= request.getRequestURI() %>">BACK</a>
  <%
  }
  %>
</body>
</html>