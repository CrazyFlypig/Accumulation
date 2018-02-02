<%--
  Created by IntelliJ IDEA.
  User: cc
  Date: 2018/1/30
  Time: 16:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form action="TestFormRequestServlet" method="get">
    站点名：<input type="text" name="name">
    <br />
    网址：<input type="text" name="url" />
    <input type="submit" value="提交" />
</form>
</body>
</html>