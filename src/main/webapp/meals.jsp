<%@ page import="java.util.List" %>
<%@ page import="ru.javawebinar.topjava.model.Meal" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.Month" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<jsp:useBean id="meals" class="ru.javawebinar.topjava.model.MealTo"/>
--%>
<%@ page import="java.io.IOException" %><%--

  Created by IntelliJ IDEA.
  User: Anna
  Date: 11.06.2019
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        TABLE {
            width: 300px;
            border: 1px solid black;
        }
        TD, TH {
            padding: 3px;
            border: 1px solid black;
            text-align: left;
        }

    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <thead>
    <tr>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr style="color: ${meal.isExcess() ? 'red' : 'green'}">
            <td>${meal.getDateTime().toString().replace("T", " ")}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>