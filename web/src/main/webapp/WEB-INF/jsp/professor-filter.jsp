<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Matveyenka academy</title>
    <link rel="icon" href="https://pbs.twimg.com/media/D27t5xjXQAAiJv8.png">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
    <style>
        body {
            background-color: azure;
        }
    </style>
</head>
<body>
<section class="a">
    <div class="container-fluid  d-fluid justify-content-between" style="background-color: azure">
        <div class="row">
            <div class="col-lg" style="padding: 1%; margin: 1%; border-top-style: solid/*border-width:thick; border-style: solid;*/
                 <%--border-color: white"--%>">
                <form action="${pageContext.request.contextPath}/professor-filter" method="post">
                    <style>
                        body {
                            background-color: azure;
                        }

                        form {
                            width: 70%;
                            display: inline-table;
                            margin-top: 5%;
                            margin-left: 15%;
                            margin-right: 15%;
                            background-color: #e8fdff;

                            padding: 2%;
                            padding-left: 5%;
                            padding-right: 5%;
                        }
                    </style>
                    <a class="form-group">
                        <label for="firstName"><b>First name: </b></label>
                        <input type="text" class="form-control" id="firstName"
                               name="firstName" style="width: 65%">
                        <label for="lastName"><b>Last name: </b></label>
                        <input type="text" class="form-control" id="lastName"
                               name="lastName" style="width: 65%">
                        <label for="email"><b>E-mail: </b></label>
                        <input type="email" class="form-control" id="email"
                               name="email" style="width: 65%">
                        <label for="speciality"><b>Speciality: </b></label>
                        <select class="form-control" id="speciality"
                                name="speciality" style="width: 65%">
                            <option value=""></option>
                            <c:forEach var="speciality" items="${requestScope.specialities}">
                                <option value="${speciality}">${speciality}</option>
                            </c:forEach>
                        </select>

                        <br><br>
                        <label for="limit" style=""><b>Limit: </b></label>
                        <input type="number" class="form-control" id="limit"
                               name="limit" style="width: 20%/* border-box*/"><br>

                        <input class="btn btn-success" id="filter-button" style="margin-top: 1%" type="submit"
                               value="Filter">
                    </a>
                </form>
            </div>
            <div class="col-sm" style="padding: 1%; margin: 1%; border-top-style: solid">
                <c:if test="${param.not_found_error}">
                    <div class="alert alert-danger" role="alert"
                         style="display: inline-table; width: content-box; margin-top: 2%; margin-left: 20%;">
                        <span class="sr-only">Error:</span>
                        <span>Professor not found!</span><br>
                    </div>
                    <br>
                    <style>
                        .col-sm {
                            background-image: url("https://pbs.twimg.com/media/D28KQ3dXcAECj0a.png");
                            background-position-x: center;
                            background-position-y: bottom;
                            background-repeat: no-repeat;
                            background-blend-mode: darken;
                            image-orientation: from-image;
                            background-size: 50%;
                        }
                    </style>
                </c:if>
                <c:if test="${param.page ne null}">
                    <div>
                        <nav aria-label="Pagination">
                            <ul class="pagination justify-content-center">
                                <c:set var="pagesAmount" value="${fn:length(sessionScope.pages)}"/>
                                <li class="page-item <c:if test="${param.page == 1}">disabled</c:if>">
                                    <a class="page-link " href="${pageContext.request.contextPath}
                                             /professor-filter?page=${param.page - 1}" aria-label="Previous">
                                        <span aria-hidden="true">&laquo;</span>
                                        <span class="sr-only">Previous</span>
                                    </a>
                                </li>
                                <c:forEach var="page" items="${sessionScope.pages}">
                                    <li class="page-item <c:if test="${param.page == page.pageNumber}">active</c:if>">
                                        <a class="page-link" id="page${page.pageNumber}"
                                           href="${pageContext.request.contextPath}
                                             /professor-filter?page=${page.pageNumber}">${page.pageNumber}
                                        </a>
                                    </li>
                                </c:forEach>
                                <li class="page-item <c:if test="${param.page == pagesAmount}">disabled</c:if>">
                                    <a class="page-link" href="${pageContext.request.contextPath}
                                             /professor-filter?page=${param.page + 1}" aria-label="Next">
                                        <span aria-hidden="true">&raquo;</span>
                                        <span class="sr-only">Next</span>
                                    </a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                    <c:set var="currentPageIndex" value="${param.page - 1}"/>
                    <div>
                        <c:forEach var="professor" items="${sessionScope.pages.get(currentPageIndex).entitiesList}">
                            <div><b>${professor.person.lastName} ${professor.person.firstName}</b>
                                <br>Email: ${professor.email}
                                <br>Speciality: ${professor.speciality}
                                <br>Interests: ${professor.interests}
                                <br><br></div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</section>
</body>
</html>
