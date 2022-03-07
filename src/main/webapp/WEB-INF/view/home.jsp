<%--
  Created by IntelliJ IDEA.
  User: Deya
  Date: 2/21/2022
  Time: 11:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Home Page</title>
</head>
<body>

<h1>This is Home Page</h1>

<p>
    User : <security:authentication property="principal.username"/>
    <br><br>
    Role(s) : <security:authentication property="principal.authorities"/>
</p>

<hr>
<security:authorize access="hasRole('MANAGER')">
    <%-- Add a link to point to /leaders... this is for managers role--%>
    <p>
        <a href="${pageContext.request.contextPath}/leaders">LeaderShip Meeting</a>

    </p>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
    <%-- Add a link to point to /leaders... this is for admin role--%>
    <p>
        <a href="${pageContext.request.contextPath}/systems">Admin Meeting</a>

    </p>
</security:authorize>
<hr>

<form:form action="${pageContext.request.contextPath}/logout" method="post">
<input type="submit" value="Logout">
</form:form>

</body>
</html>
