# 关于TCP/IP，必知必会的十个问题

[参考文章链接](http://sctrack.sendcloud.net/track/click/eyJtYWlsbGlzdF9pZCI6IDAsICJ0YXNrX2lkIjogIiIsICJlbWFpbF9pZCI6ICIxNTE3NDA4NDA3NjA0XzE4N18yODc5N183MzcwLnNjLTEwXzlfNF80OC1pbmJvdW5kMCQxNTIwMjQzNjkyMUAxNjMuY29tIiwgInNpZ24iOiAiZGJjY2M0ZGM0NDQ3ZTQwNDBhZmNlYzE5ZGNkNjAyNjciLCAidXNlcl9oZWFkZXJzIjoge30sICJsYWJlbCI6ICIxNjgzMyIsICJsaW5rIjogImh0dHAlM0EvL3dlZWtseS5tYW5vbmcuaW8vYm91bmNlJTNGdXJsJTNEaHR0cCUyNTNBJTI1MkYlMjUyRm1wLndlaXhpbi5xcS5jb20lMjUyRnMlMjUyRnFuNWZ3OHlIdmpCb3U2UHMyWG85THclMjZhaWQlM0QxMjIzMiUyNm5pZCUzRDIwMyUyNm4lM0RORFV3TkRVLi1GSG9pM20tMkpFczA4dTVwUGtZbktqRjE2NCIsICJ1c2VyX2lkIjogMTg3LCAiY2F0ZWdvcnlfaWQiOiA2MDU4OX0=.html)

![640](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/640.jpg)

## 一、TCP/IP模型

基于TCP/IP的参考模型将协议分成四个层次，它们分别是链路层、网络层、传输层和应用层。下图表示TCP/IP模型与OSI模型各层的对照关系。

![1.1](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/1.1.jpg)

TCP/IP协议族按照层次由上到下，层层包装。最上面应用层包括http、ftp等。第二层传输层包括TCP和UDP。第三层网络层，IP协议就在这，它负责对数据加上IP地址和其它数据以确定传输的目标。第四层是数据链路层，主要负责为待传送的数据加入一个以太网协议头，并进行CRC编码，为最后的数据传输做准备。

TCP/IP协议通信的过程其实就对应着数据入栈与出栈的过程。入栈的过程，数据发送方每层不断地封装首部与尾部，添加一些传输的信息，确保能传输到目的地。出栈的过程，数据接收方每层不断的拆除首部与尾部，得到最终的传输数据。

以HTTP协议为例：

![1.2](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/1.3.png)

## 二、数据链路层

物理层负责0、1比特流与物理电压的高低、光的闪灭之间的切换。数据链路层负责将0、1序列划分为数据帧从一个节点传输到临近的另一个节点，这些节点通过MAC地址来唯一标识。

*   封装成帧：把网络层数据报加头和尾，封装成帧，帧头中包括源MAC地址和目标MAC地址。
*   透明传输：零比特填充、转义字符。
*   可靠传输：在出错率很低的链路上很少用，但是无线链路WLAN会保证可靠传输。
*   差错检测(CRC)：接收者检测错误，如发现差错，丢弃该帧。

## 三、网络层

### 1. IP协议

所有的TCP、UDP、ICMP、IGMP的数据报都以IP数据格式传输。注意的是，IP协议没有提供一种数据未传达以后的处理机制，这被认为是上层协议：TCP或UDP做的事情。

#### 1.1 IP地址

IP层使用IP地址唯一标识一个节点。

*   A类IP地址: 0.0.0.0~127.255.255.255
*   B类IP地址:128.0.0.0~191.255.255.255
*   C类IP地址:192.0.0.0~239.255.255.255

#### 1.2 IP协议头

![3.1](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/3.1.jpg)

这里只介绍:八位的TTL字段。这个字段规定该数据包在穿过多少个路由之后才会被抛弃。某个IP数据包每穿过一个路由器，该数据包的TTL数值就会减少1，当该数据包的TTL成为零，它就会被自动抛弃。
这个字段的最大值也就是255，也就是说一个协议包也就在路由器里面穿行255次就会被抛弃了，根据系统的不同，这个数字也不一样，一般是32或者是64。

### 2. ARP及RARP协议

ARP是根据IP地址获取MAC地址的一种协议。

ARP（地址解析）协议是一种解析协议，本来主机是完全不知道这个IP对应的是哪个主机的哪个接口，当主机要发送一个IP包的时候，会首先查一下自己的ARP高速缓存（就是一个IP-MAC地址对应表缓存）。如果查询的IP－MAC值对不存在，那么主机就向网络发送一个ARP协议广播包，这个广播包里面就有待查询的IP地址，而直接收到这份广播的包的所有主机都会查询自己的IP地址，如果收到广播包的某一个主机发现自己符合条件，那么就准备好一个包含自己的MAC地址的ARP包传送给发送ARP广播的主机。而广播主机拿到ARP包后会更新自己的ARP缓存（就是存放IP-MAC对应表的地方）。发送广播的主机就会用新的ARP缓存数据准备好数据链路层的的数据包发送工作。

RARP协议与ARP协议工作机制相反。

### 3. ICMP协议

ICMP（网络控制报文）协议，是IP层协议。

当传送IP数据包发生错误时。比如主机不可达、路由不可达等，ICMP协议就会把错误信息封包，然后传送回主机。给主机一个处理错误的机会。

## 四、ping

ping可是说是ICMP的最著名的应用，是TCP/IP协议的一部分。它利用ICMP协议包来侦测另一个主机是否可达。原理是用类型码为0的ICMP发请求，收的请求主机则是用类型码为8的ICMP回应。

ping程序来计算间隔时间，并计算有多少个包被送达。用户就可以判断网络大致的情况。我们可以看到， ping给出来了传送的时间和TTL的数据。

## 五、Traceroute

Traceroute是用来侦测主机到目的主机之间所经路由情况的工具。

Tracerout的原理是，它收到目标主机IP后，首先给目的主机发送一个TTL=1的UDP数据包，而经过的第一个路由器收到这个数据包后，就自动把TTL减1，而TTL变为0以后，路由器就把这个数据包给抛弃了，并同时产生一个主机不可达的ICMP数据报给主机。主机收到这个数据报以后再发送一个TTL=2的UDP数据包给目的主机，然后刺激第二个路由器给主机发送ICMP数据报。如此往复，Traceroute就拿到了所有路由器IP。如：`tracert www.baidu.com`

## 六、TCP/UDP

![6.1](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/6.1.jpg)

#### 面向报文

面向报文的传输方式是应用层交给UDP多长的报文，UDP就照样发送，即一次发送一个报文。因此，应用程序必须选择合适大小的报文。若报文太长，则IP层需要分片，降低效率。若太短，会是IP太小。

#### 面向字节流

应用程序和TCP的交互是一次一个数据块（大小不等），但TCP把应用程序看成是一连串的无结构的字节流。TCP有一个缓冲，当应用程序传送的数据块太长，TCP就可以把它划分为短一些再传送。

TCP和UDP协议的一些引用：

![6.2](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/6.2.jpg)

#### 什么时候应该使用UDP？

当对网络通讯质量要求不高的时候，要求网络通讯速度能尽量的快，这时就可以使用UDP。

#### 什么时候应该使用TCP？

当对网络通讯质量有要求的时候，比如：整个数据要准确无误的传递给对方，这往往用于一些要求可靠的应用，比如HTTP、HTTPS、FTP等传输文件的协议，POP、SMTP等邮件传输的协议。

## 七、DNS

DNS（Domain Name System，域名系统），因特网上作为域名和IP地址相互映射的一个分布式数据库，能够使用户更方便的访问互联网，而不用去记住能够被机器直接读取的IP数串。通过主机名，最终得到该主机名对应的IP地址的过程叫做域名解析（或主机名解析）。DNS协议运行在UDP协议之上，使用端口号53。

## 八、TCP连接的建立与终止

### 1. 三次握手

在TCP/IP协议中，TCP协议提供可靠的连接服务，连接是通过三次握手进行初始化的。三次握手的目的是同步连接双方的序列号和确认号并交换TCP窗口大小信息。

![8.1](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/8.1.png)

第一次握手：建立连接。客户端发送连接请求报文段，将SYN位置为1，Sequence Number为 x；然后，客户端进入SYN_SEND状态，等待服务器确认；

第二次握手：服务器收到SYN报文段。服务器收到客户端的SYN报文段，需要对这个SYN报文段进行确认，设置Acknowledgement Number为x+1（Sequence Number + 1）；同时，自己还要发送SYN请求信息，将SYN位置为1，Sequence Number为y；服务器端将上述所有信息放到一个报文段（即SYN + ACK报文段）中，一并发送给客户端，此时服务器进入SYN_RECV状态；

第三次握手：客户端收到服务器的SYN+ACK报文段。然后将Acknowledgement Number设置为y+1，向服务器发送ACK报文段，这个报文段发送完成以后，客户端和服务端都进入ESTABLISHED状态，完成TCP三次握手。

#### 为什么要三次握手

为了防止已失效的连接请求报文段突然又传送到服务端，因而产生错误。

具体例子：“已失效的连接请求报文段”的产生在这样一种情况下：client发出的第一个连接请求报文段并没有丢失，而是在某个网络结点长时间的滞留了，以致延误到连接释放以后的某个时间才到达server。本来这是一个早已失效的报文段。但server收到此失效的连接请求报文段后，就误认为是client再次发出的一个新的连接请求。于是就向client发出确认报文段，同意建立连接。假设不采用“三次握手”，那么只要server发出确认，新的连接就建立了。由于现在client并没有发出建立连接的请求，因此不会理睬server的确认，也不会向server发送数据。但server却以为新的运输连接已经建立，并一直等待client发来数据。这样，server的很多资源就白白浪费掉了。采用“三次握手”的办法可以防止上述现象发生。例如刚才那种情况，client不会向server的确认发出确认。server由于收不到确认，就知道client并没有要求建立连接。”

### 2. 四次挥手

![8.2](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/8.2.jpg)

第一次分手：主机1，设置Sequence Number，向主机2发送一个FIFN报文段；此时主机1进入FIN_WAIT_1状态；这表示主机1没有数据要发送给主机2了；

第二次分手：主机2收到主机1发送的FIN报文段，向主机1回一个ACK报文段，Acknowledgement Number为Sequence Number加1；主机1进入FIN_WAIT_2状态；主机2告诉主机1，我“同意”你的关闭请求；

第三次分手：主机2向主机1发送FIN报文段，请求关闭连接，同时主机2进入LAST_ACK状态；

第四次分手：主机1收到主机2发送的FIN报文段，向主机2发送ACK报文段，然后主机1进入TIME_WAIT状态；主机2收到主机1的ACK报文段以后，就关闭连接；此时，主机1等待2MSL后依然没有收到回复，则证明Server端已经正常关闭，那好，主机1也可以关闭连接了。

#### 为什么要四次分手？

TCP协议是一种面向连接的、可靠的、基于字节流的运输层通信协议。TCP是全双工模式，这意味着，当主机1发出FIN报文段时，只表示主机1已经没有数据要发送了，主机1告诉主机2，它的数据已经全部发送完毕了；但是，这个时候主机1还是可以接受来自主机2的数据；当主机2返回ACK报文段时，表示它已经知道主机1没有数据发送了，但是主机2还是可以发送数据到主机1的；当主机2也发送了FIN报文段时，这个时候就表示主机2也没有数据要发送了，就会告诉主机1，我没有数据要发送了，之后彼此就会愉快的中断这次TCP连接。

#### 为什么要等待2MSL？

MSL：报文段最大生存时间，它是任何报文段被丢弃前在网络内的最长时间。

原因有二：

*   保证TCP协议的双全工连接能够可靠关闭
*   保证这次连接的重复数据段从网络中消失

第一点：如果主机1直接CLOSED了，那么由于IP协议的不可靠性或者其它网络原因，导致主机２没有收到主机１最后回复的ACK。那么主机２就会在超时之后继续发送FIN，此时由于主机１已经CLOSED，就找不到与重发的FIN对应的连接。所以，主机1不是直接进入CLOSED，而要保持TIME＿WAIT，当再次收到FIN的时候，能够保证对方收到ACK，最后正确的关闭连接。

第二点：如果主机1直接CLOSED，然后又再向主机2发起一个连接，我们不能保证这个新连接与刚关闭的连接的端口号是不同的。也就是说有可能新连接和老连接的端口号是相同的。一般来说不会发生什么问题，但是还是有特殊情况出现：假设新的连接和已经关闭的老连接端口号是一样的，如果前一次连接的某些数据仍然滞留在网络中，这些延迟数据在建立新连接之后才到达主机2，由于新连接和老连接的端口号是一样的，TCP协议就认为那个延迟数据是属于新的连接的，这样就和真正的新连接的数据包发生混淆了。所以TCP连接还要在TIME_WAIT状态等待2倍的MSL，这样可以保证本次连接的所有数据从网络中消失。

## 九、TCP流量控制

如果发送方把数据发送得快，接收方可能来不及接收，这就会造成数据丢失。所谓流量控制就是让发送方的发送速率不要太快，要让接收方来得及接收。

利用滑动窗口机制可以很方便地在TCP连接上实现对发送方的流量控制。

设A向B发送数据。在建立连接时，B告诉A：“我的接收窗口是 rwnd=400”(这里的rwnd表示 receiver window)。因此，发送方的发送窗口不能超过接收方给出的接收窗口的数值。请注意，TCP的窗口单位是字节，不是报文段。假设每一个报文段为100字节长，而数据报文段的初始序号是1。大写的ACK表示首部中的确认ACK，小写的ack表示确认字段的值ack。

![9.1](https://github.com/CrazyFlypig/Accumulation/blob/master/Internet/Theory/Images/IP%EF%BC%8C%E5%BF%85%E7%9F%A5%E5%BF%85%E4%BC%9A%E7%9A%84%E5%8D%81%E4%B8%AA%E9%97%AE%E9%A2%98/9.1.jpg)

从图中可以看出，B进行了三次流量控制。第一次把窗口减少到 rwnd=300	，第二次又减到 rwnd=100，最后减到 rwnd=0，即不允许发送方再发送数据了。这种使发送方暂停发送的状态持续到主机B重新发出一个新的窗口值为止。B向A发送的三个报文段都设置了 ACK=1，只有在 ACK=1时确认号字段才有意义。

TCP为每一个连接都设有一个持续计数器（persistence timer）。只要TCP连接的一方接收到对方的零窗口通知，就启动持续计数器。若持续计数器的时间到期，就发送一个零窗口检测报文段（携1字节的数据），那么收到这个报文段的一方就重新设置持续计数器。

## 十、TCP拥塞控制







