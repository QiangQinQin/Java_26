package network;

/**
 * 网络编程
 * 1、概念
 * 2、TCP协议/UDP协议
 * 3、Java中IO模型：BIO NIO AIO
 * 
 * （WPS图）
 * 计算机网络         由 若干结点 和 连接这些结点的链路组成。（理解为网络就是把计算机连接到一起）
 * 网络中的  结点   可以是计算机、集线器、交换机、路由器等等。
 *
 * 互连网(网络的网络)
 *  网络  之间通过 路由器 连接，这就构成一个覆盖范围更大的计算机网络，这样的网络称之为互连网
 *
 * 网络      把许多计算机连接在一起，
 * 互连网  把网络通过路由器连接在一起，与网络相连的计算机称之为   主机(IP地址)。
 * 注意：网络互连并不是把计算机简单地在物理层面连接起来，因为这样计算机不能够进行交互。我们还必须在你计算上安装能够交互的  软件(port：唯一标识一个软件（进程）)才行
 *
 * 计算机网络的分类
 * 1、按照网 的  作用范围  分类
 *   1）广域网(WAN) （长距离  高速度）
 *   2）域域网(MAN) （一个城市里 或者 跨越街区）
 *   3) 局域网(LAN)
 *   4) 个人区域网(PAN) （手机热点）
 * 2、 按照网络的  使用者  分类
 *   1）公有网
 *   2）私有网
 *
 * 计算机网络中性能指标
 * 1）速率  数据传输速率
 * 2）带宽  表示网络中某通道传输数据的能力，单位时间内网络信道所能通过的最高数据率
 * 3）吞吐量（QPS）： 单位时间内某个网络实际的数据量
 * 4）时延
 *    a.发送时延（主机发送数据帧，从一个帧的第一个比特位  到  当前帧的最后一个比特位所需要的时间）
 *    b.传播时延 （电磁波在信道中传播所需的）
 *    c.处理时延（收到数据帧后，从头部提取数据  差错校验等）
 *    d.排队时延（分组进路由器等处理）
 * 5）往返时间RTT
 *
 * 网络体系结构
 * 网络协议   （为了交换数据而定义的规则） 
 * 计算机网络各层及其协议的集合就是网络的   体系结构
 * 1）最开始：  OSI七层协议体系结构
 *  从上往下：  应用层  表示层  会话层  传输层  网络层  数据链路层  物理层
 * 2）接下来： TCP/IP体系结构
 *                  应用层     传输层  网络层              网络接口层
 * 3）接下里：  五层协议的体系结构（综合OSI和TCP/IP而来）
 *                  应用层  传输层  网络层  数据链路层   物理层
 *
 * a.应用层
 *    应用进程之间的  交互。这里的进程指的是在主机中正在运行的程序
 *    应用层交互的数据单元称为  报文
 *    
 * （表示层是把应用层提供的信息变成底层能理解的东西；会话层负责两个应用程序之间建立会话交流：单工  半双工  全双工）
 * 
 * b.传输层
 *    向两台主机中进程之间的通信提供  数据  传输服务。
 *    运输层起着承上启下的作用，涉及源端节点到目的端节点之间可靠信息的传输。
 *   传输层  需要解决网络连接的建立和释放：TCP协议/UDP协议
 * c.网络层
 *    网络层负责 分组交换网上的不同主机 提供通信服务，在发送数据时，网络层会将传输层产生的报文端或用户的数据报  封装成分组或者包进行传输
 *    网络层传输的是  数据报
 * d.数据链路层
 *    两台主机之间的数据传输，总是在链路上传输，所以需要链路层
 *    链路层传输的数据称之为  帧
 * e.物理层
 *    物理层所传输的单位是  比特，发送方发送0或1，接收方法接收0或1
 *
 * 主机1有一个进程:pro1   向    主机2:pro2 发送数据
 *   应用层   加上下一层（传输层）数据单元所需要的控制信息  就到了   传输层 -》传输层又加上下一层数据单元所需要的控制信息   到了 网络层 -》 数据链路层 -》物理层
 *   （以此类推，每一层往下传，需要加上每一层的控制信息      发：由上往下      收：由下往上）
 *   Teacher_3_6_TCP有传的过程
 *   
 *
 * 最常用的体系结构 TCP/IP体系结构和  五层协议体系结构
 * 差错控制的
 * 详见《计算机网络  》谢希仁   第一章
 *
 */
public class Teacher_3_3_TCP_IP {
}
