<%--
  Created by IntelliJ IDEA.
  User: cc
  Date: 2018/1/30
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style type="text/css">
        th {background-color: dimgrey}
        tr {background-color: aliceblue}
        td {text-align: center}
        .introduce{text-align:left; padding: auto; text-indent: 50px}
    </style>
    <title>信息接收</title>
</head>
<body>
<table border="1" align="center" width="100%">
    <th colspan="2">个人信息</th>
    <tr>
        <td width="100">First Name</td>
        <td><p><%= new String((request.getParameter("firstname")).getBytes("ISO-8859-1"),"UTF-8")%></p></td>
    </tr>
    <tr>
        <td width="100">Last Name</td>
        <td><p><%= new String((request.getParameter("lastname")).getBytes("ISO-8859-1"),"UTF-8")%></p></td>
    </tr>
    <tr>
        <td width="100">Sex</td>
        <td><p><%= new String((request.getParameter("sex")).getBytes("ISO-8859-1"),"UTF-8")%></p></td>
    </tr>
    <tr>
        <td width="100">已选科目</td>
        <%
            String[] results = request.getParameterValues("object");
            StringBuffer buffer = new StringBuffer();
            for (String result : results){
                buffer.append(result + "; ");
            }
        %>
        <td><p><%= new String(buffer.toString().getBytes("ISO-8859-1"),"UTF-8")%></p></td>
    </tr>
    <tr>
        <td width="100">学历</td>
        <td><p><%= new String((request.getParameter("education")).getBytes("ISO-8859-1"),"UTF-8")%></p></td>
    </tr>
    <tr id="introduce">
        <td width="100">自我介绍</td>
        <td class="introduce"><p><%= new String((request.getParameter("introduce")).getBytes("ISO-8859-1"),"UTF-8")%></p></td>
    </tr>
</table>
</body>
</html>
