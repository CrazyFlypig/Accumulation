<%--
  Created by IntelliJ IDEA.
  User: cc
  Date: 2018/1/30
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>信息采集</title>
</head>
<body>
<form action="TestFormRequestServlet" target="_blank" method="post">
    First Name：
    <input type="text" name="firstname" size="15">
    <br>
    <br>
    Last Name：
    <input type="text" name="lastname" size="15">
    <br>
    <br>
    Password：
    <input type="password" name="psw" required="required" size="15">
    <br>
    <br>
    <input type="radio" name="sex" value="男" checked> 男
    <input type="radio" name="sex" value="女"> 女
    <br>
    <br>
    <input type="checkbox" name="object" value="语文"> 语文
    <input type="checkbox" name="object" value="数学"> 数学
    <input type="checkbox" name="object" value="英语"> 英语
    <input type="checkbox" name="object" value="C++"> C++
    <br>
    <br>
    <select name="education" required="required"> 学历
        <option value="本科">本科</option>
        <option value="研究生">研究生</option>
        <option value="博士">博士</option>
    </select>
    <br>
    <br>
    自我介绍：
    <input type="text" name="introduce" size="15" height="50" width="15">
    <br>
    <br>
    <br>
    <input type="submit" name="登录">
</form>
</body>
</html>
