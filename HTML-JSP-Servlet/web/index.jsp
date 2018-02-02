<%--
  Created by IntelliJ IDEA.
  User: cc
  Date: 2018/1/30
  Time: 13:44
  To change this template use File | Settings | File Templates.
  <%代码片段%>
  <jsp:script>代码片段<jsp:script>
  <%! declaration; [ declaration; ]+ ... %> JSP声明，方法、变量，先声明再使用
  <%= 表达式 %> JSP表达式中包含的脚本语言表达式，先被转化成String，然后插入到表达式出现的地方。
      语法同Java类似，缺少分号。
  JSP注释：为代码作注释以及将某段代码注释掉。
  <%@ directive attribute="value" %>，JSP指令用来设置与整个JSP页面相关的属性。
  <jsp:action_name attribute="value" />，JSP行为标签使用XML语法结构来控制servlet引擎。
      它能够动态插入一个文件，重用JavaBean组件，引导用户去另一个页面，为Java插件产生相关的HTML等等。
  JSP隐含对象
  JSP提供对Java语言的全面支持。您可以在JSP程序中使用Java API甚至建立Java代码块，包括判断语句和循环语句等等。

--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%! int day = 3; %>
<%! String result = ""; %>
<html>
<head>
    <title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h3>if...else...实例</h3>
<select >
</body>
</html>
