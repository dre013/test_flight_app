<%@ page import="by.dre.je.jdbc.service.TicketService" %>
<%@ page import="by.dre.je.jdbc.dto.TicketDto" %><%--
  Created by IntelliJ IDEA.
  User: nicea
  Date: 16.05.2024
  Time: 3:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/functions' prefix='fn' %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
    <body>
    <%@include file="header.jsp"%>
        <h1>Список билетов</h1>
        <ul>
            <c:if test="${not empty requestScope.tickets}">
                <c:forEach var="ticket" items="${requestScope.tickets}">
                    <li>${fn:toLowerCase(ticket.seatNo())}</li>
                </c:forEach>
            </c:if>
        </ul>
    </body>
</html>
