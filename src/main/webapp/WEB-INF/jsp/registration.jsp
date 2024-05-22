<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: nicea
  Date: 17.05.2024
  Time: 2:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="name">Name :
        <input type="text" name="name" id="name">
    </label><br/>
    <label for="birthday">Birthday :
        <input type="date" name="birthday" id="birthday">
    </label><br/>
    <label for="email">Email :
        <input type="text" name="email" id="email">
    </label><br/>
    <label for="pwd">Password :
        <input type="password" name="pwd" id="pwd">
    </label><br/>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option label="${role}" id="role">${role}</option><br>
        </c:forEach>
        <br/>
    </select>
    <br/>
    <label>
        <c:forEach var="gender" items="${requestScope.genders}">
            <input type="radio" name="gender" VALUE="${gender}">${gender}
            <br/>
        </c:forEach>
    </label>
    <input type="submit" value="Send">
</form>
<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
