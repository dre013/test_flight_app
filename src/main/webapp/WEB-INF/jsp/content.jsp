<%--
  Created by IntelliJ IDEA.
  User: nicea
  Date: 16.05.2024
  Time: 4:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <span>CONTENT РУССКИЙ</span>
    <p>Size: ${requestScope.flights.size()}</p>
    <p>Description: ${requestScope.flights.get(0).description()}</p>
    <p>Id: ${requestScope.flights[0].id()}</p>
</div>
</body>
</html>
