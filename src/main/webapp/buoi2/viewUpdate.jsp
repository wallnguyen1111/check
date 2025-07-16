<%--
  Created by IntelliJ IDEA.
  User: vipgl
  Date: 7/11/2025
  Time: 12:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Sua Thong Tin:
<form action="/buoi2/update" method="post">
    Id: <input type="text" name="id" value="${nuocHoa.id}"> <br>
    Ten: <input type="text" name="ten" value="${nuocHoa.ten}"> <br>
    So Luong:<input type="text" name="soLuong" value="${nuocHoa.soLuong}"> <br>
    Gia:<input type="text" name="gia" value="${nuocHoa.gia}"> <br>
    <button>Submit</button>
</form>
</body>
</html>
