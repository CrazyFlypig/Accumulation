<%--
  Created by IntelliJ IDEA.
  User: cc
  Date: 2018/2/2
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>
<input type="file" id="image">
<div>
    <img src="" id="preview">
</div>
<script type="text/javascript">
    var image = document.querySelector('#image');
    var preview = document.querySelector('#preview');
    image.onchange = Preview;

    function Preview() {
        var file = this.files[0];
        var read = new FileReader();
        read.readAsDataURL(file);
        read.onload = function () {
            preview.src = read.result;
        };
    }
</script>
</body>
</html>
