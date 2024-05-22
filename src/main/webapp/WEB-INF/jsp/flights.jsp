<%--
  Created by IntelliJ IDEA.
  User: nicea
  Date: 16.05.2024
  Time: 4:38
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
    <h1>Список перелетов</h1>
    <ul>
        <c:if test="${not empty requestScope.flights}">
            <c:forEach var="flight" items="${requestScope.flights}">
                <li>
                    <a href='${pageContext.request.contextPath}/tickets?flightId=${flight.id()}'>${flight.description()}</a>
                </li>
            </c:forEach>
        </c:if>
    </ul>
</body>
</html>
