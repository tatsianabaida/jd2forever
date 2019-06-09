<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <c:forEach var="student" items="${requestScope.studentsList}">
        <div>${student.person.lastName} ${student.person.firstName}
            <br>Email: ${student.email}<br><br></div>
    </c:forEach>
</div>
</body>
</html>
