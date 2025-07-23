<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: vipgl
  Date: 7/10/2025
  Time: 10:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Them thong tin nuoc hoa:
<form action="/buoi2/them" method="post">
    Ten: <input type="text" name="ten"> <br>
    So Luong:<input type="text" name="soLuong"> <br>
    Gia:<input type="text" name="gia"> <br>
    <button>Submit</button>
</form>
Thong tin nuoc hoa:
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>Ten</th>
        <th>So Luong</th>
        <th>Gia</th>
        <th>Hanh Dong</th>
    </tr>
    </thead>
    <tbody>
    <%--        goi den cac thuoc tinh trong file model--%>
    <c:forEach items="${danhSach}" var="nh">
        <tr>
            <td>${nh.id}</td>
            <td>${nh.ten}</td>
            <td>${nh.soLuong}</td
            <td>${nh.gia}</td>
            <td>
                <a href="/buoi2/chi-tiet?id=${nh.id}">Chi tiet</a>
                <a href="/buoi2/view-update?id=${nh.id}">View Update</a>
                <a href="/buoi2/xoa?id=${nh.id}">Xoa </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
