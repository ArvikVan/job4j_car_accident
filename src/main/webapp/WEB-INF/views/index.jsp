<%--
  Created by IntelliJ IDEA.
  User: arvik
  Date: 30.01.2022
  Time: 19:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
      integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<head>
    <title>Car-Accident</title>
</head>
<body>
<div class="col-md-8 mx-auto">
    <h2>Accident table</h2>
    <div>Login as : ${user.username}</div>
<table class="table table-bordered">
    <thead>
    <tr>
        <th scope="col">id</th>
        <th scope="col">name</th>
        <th scope="col">text</th>
        <th scope="col">address</th>
        <th scope="col">typeOfAccident</th>
        <th scope="col">Rules</th>
    </tr>
    </thead>
    <tbody>
<c:forEach var="accident" items="${NameOfRefToJSP}">
    <tr>
        <th scope="row">${accident.id}</th>
        <td><c:out value="${accident.name}"/></td>
        <td><c:out value="${accident.text}"/></td>
        <td><c:out value="${accident.address}"/></td>
        <td><c:out value="${accident.type.name}"/></td>
        <td><c:forEach var="rule" items="${accident.rules}"><c:out value="${rule.name}"/></c:forEach></td>
        <td><a href="<c:out value='/accident/update?id=${accident.id}'/>">Edit accident</a></td>
    </tr>
</c:forEach>
    </tbody>
</table>
    <a href="<c:url value='/create'/>">Add another accident</a>
</div>
</body>
</html>
