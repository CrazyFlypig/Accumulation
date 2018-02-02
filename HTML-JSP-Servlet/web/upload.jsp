<%--
  Created by IntelliJ IDEA.
  User: cc
  Date: 2018/2/1
  Time: 9:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传实例 - CC</title>
</head>
<body>
<h1>文件上传实例 - CC</h1>
<form method="post" action="UploadServlet" enctype="multipart/form-data" onsubmit="return checkFileFormat()" target="_blank" >
    选择一个文件：
    <input type="file" id="uploadFile" name="uploadFile" />
    <br/><br/>
    <input type="submit" value="上传" />
</form>
<script>
    var checkFileFormat = function () {
        var file = document.getElementById("uploadFile");
        var fileName = file.value;
        alert(fileName.toString());
        if (fileName && !(fileName.indexOf(".jpg") != -1 || fileName.indexOf(".png") != -1 || fileName.indexOf(".gif") != -1)){
            alert("Can only upload image file.");
            return false;
        }
        return true;
    };
</script>
</body>
</html>
