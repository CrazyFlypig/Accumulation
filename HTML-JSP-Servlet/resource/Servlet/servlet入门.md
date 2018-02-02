# Servlet入门

## 一、Servlet简介

###1.1 Servlet是什么?

Java Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。

使用 Servlet，您可以收集来自网页表单的用户输入，呈现来自数据库或者其他源的记录，还可以动态创建网页。

### 1.2 Servlet架构

![servlet架构](D:\programmer\前端\servlet入门\servlet架构.jpg)

### 1.3 Servlet任务

Servlet主要执行以下任务：

*   读取客户端（浏览器）发送的显式的数据。这包括网页上的 HTML 表单，或者也可以是来自 applet 或自定义的 HTTP 客户端程序的表单。
*   读取客户端（浏览器）发送的隐式的 HTTP 请求数据。这包括 cookies、媒体类型和浏览器能理解的压缩格式等等。
*   处理数据并生成结果。这个过程可能需要访问数据库，执行 RMI 或 CORBA 调用，调用 Web 服务，或者直接计算得出对应的响应。
*   发送显式的数据（即文档）到客户端（浏览器）。该文档的格式可以是多种多样的，包括文本文件（HTML 或 XML）、二进制文件（GIF 图像）、Excel 等。
*   发送隐式的 HTTP 响应到客户端（浏览器）。这包括告诉浏览器或其他客户端被返回的文档类型（例如 HTML），设置 cookies 和缓存参数，以及其他类似的任务。

### 1.4 Servlet包

Servlet 可以使用 **javax.servlet** 和 **javax.servlet.http** 包创建，它是 Java 企业版的标准组成部分，Java 企业版是支持大型开发项目的 Java 类库的扩展版本。

## 二、Servlet生命周期

Servlet 生命周期可被定义为从创建直到毁灭的整个过程。以下是 Servlet 遵循的过程：

*   Servlet 通过调用 **init ()** 方法进行初始化。
*   Servlet 调用 **service()** 方法来处理客户端的请求。
*   Servlet 通过调用 **destroy()** 方法终止（结束）。
*   最后，Servlet 是由 JVM 的垃圾回收器进行垃圾回收的。

### init()方法

它在第一次创建 Servlet 时被调用，在后续每次用户请求时不再调用。

也可以指定 Servlet 在服务器第一次启动时被加载。当用户调用一个 Servlet 时，就会创建一个 Servlet 实例，每一个用户请求都会产生一个新的线程，适当的时候移交给 doGet 或 doPost 方法。init() 方法简单地创建或加载一些数据，这些数据将被用于 Servlet 的整个生命周期。

### service()方法

service() 方法是执行实际任务的主要方法。Servlet 容器（即 Web 服务器）调用 service() 方法来处理来自客户端（浏览器）的请求，并把格式化的响应写回给客户端。

每次服务器接收到一个 Servlet 请求时，服务器会产生一个新的线程并调用服务。service() 方法检查 HTTP 请求类型（GET、POST、PUT、DELETE 等），并在适当的时候调用 doGet、doPost、doPut，doDelete 等方法。

service() 方法由容器调用，service 方法在适当的时候调用 doGet、doPost、doPut、doDelete 等方法。所以，您不用对 service() 方法做任何动作，您只需要根据来自客户端的请求类型来重写 doGet() 或 doPost() 即可。

### doGet()方法

GET 请求来自于一个 URL 的正常请求，或者来自于一个未指定 METHOD 的 HTML 表单，它由 doGet() 方法处理。

### doPost()方法

POST 请求来自于一个特别指定了 METHOD 为 POST 的 HTML 表单，它由 doPost() 方法处理。

### destroy()方法

destroy() 方法只会被调用一次，在 Servlet 生命周期结束时被调用。destroy() 方法可以让您的 Servlet 关闭数据库连接、停止后台线程、把 Cookie 列表或点击计数器写入到磁盘，并执行其他类似的清理活动。

在调用 destroy() 方法之后，servlet 对象被标记为垃圾回收。

## 架构图

一个典型的 Servlet 生命周期方案：

*   第一个到达服务器的 HTTP 请求被委派到 Servlet 容器。
*   Servlet 容器在调用 service() 方法之前加载 Servlet。
*   然后 Servlet 容器处理由多个线程产生的多个请求，每个线程执行一个单一的 Servlet 实例的 service() 方法。

![Servlet生命周期](D:\programmer\前端\servlet入门\Servlet生命周期.jpg)

## Servlet文件上传

Servlet 可以与 HTML form 标签一起使用，来允许用户上传文件到服务器。上传的文件可以是文本文件或图像文件或任何文档。

此次测试用到的文件有：

*   upload.jsp：文件上传表单
*   message.jsp：上传成功后跳转页面
*   UploadServlet.java：上传处理Servlet
*   需要引入的jar文件：commons-fileupload-1.3.2、commons-io-2.5.jar。

注意：Servlet3.0已经内置了文件上传这一特性，无需重新导入jar包。

### 创建一个文件上传表单

下面的 HTML 代码创建了一个文件上传表单。以下几点需要注意：

*   表单**method**属性应该设置为**Post**方法，不能使用Get方法。
*   表单**enctype**属性应该设置为**multipart/from-data**.
*   表单**action**属性应设置为在后端服务器上处理文件上传的servlet文件。
*   上传单个文件，应该使用单个带有属性 type="file"的\<input .../>标签。为了允许多个文件上传，请包含多个name属性值不同的input标签。输入标签具有不同的名称属性的值。浏览器会为每个input标签关联一个浏览按钮。

### 编写后台 Servlet



