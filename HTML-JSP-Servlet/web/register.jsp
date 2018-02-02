<%--
  Created by IntelliJ IDEA.
  User: cc
  Date: 2018/2/2
  Time: 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户注册</title>
</head>
<body>
<form id="register" action="" target="_black" onsubmit="return checkRegisterForm()" method="post">
    <p id="test-error" style="color:red"></p>
    <p>
        用户名：<input type="text" id="username" name="username">
    </p>
    <p>
        密码：<input type="password" id="password" name="password">
    </p>
    <p>
        确认密码：<input type="password" id="password-2">
    </p>
    <p>
        <button type="submit">注册</button> <button type="reset">重置</button>
    </p>
</form>
<script>
    var checkRegisterForm = function () {
        //TODO
        var userName = document.getElementById("username");
        var passWord = document.getElementById("password");
        var passWord2 = document.getElementById("password-2");
        var flag = false;
        if (userName.value.length < 3 || userName.value.length > 10) {
            alert("用户名长度限制3-10个字母或数字");
            return false;
        }
        for (var m = 0; m < userName.value.length; m++) {
            if (!((userName[m].value >= 'a' && userName[m].value <= 'z') || (userName[m].value >= 'A' && userName[m].value <= 'Z') || (userName[m].value >= 0 && userName[m].value <= 9))) {
                alert("用户名只能包含字母或数字");
                return false;
            }
        }
        if (passWord.value.length < 6 || passWord.value.length > 20) {
            alert("密码长短在6-20位");
            return false;
        }
        if (passWord.value != passWord2.value) {
            alert("两次输入的密码不同，请重新输入");
            return false;
        }
        return true;
    };
</script>
</body>
</html>
