package network;

/**
 * 复习：
 * IO模型
 *      BIO   同步阻塞IO(如TCP;多线程下每来一个请求创建一个线程)
 *      NIO   同步非阻塞IO（轮询）
 *      IO多路复用   一个select/epoll系统调用（一个线程来轮询）
 *      AIO   异步非阻塞IO(发起系统调用，内核完成后来通知你)
 *
 * Java中NIO（不是对应操作系统中NIO）
 *      提供了选择器(Selector)类似操作系统提供的select/epoll，也叫做IO多路复用器
 *      作用是检查一个channel(通道)的状态是否是可读、可写、可连接、可接收的状态，
 *      为了实现 单线程 管理 多个channel，其实也就是管理多个网络请求（WPS上有图）
 *
 * Channel通道（之前学的流，只能读或只能写）
 *      来自java.nio.channels，即可以读，又可以写，不直接和 数据源 打交道，主要和缓冲区Buffer进行交互
 * ServerSocketChannel 服务器端
 *       SocketChannel 客户端
 *
 * Buffer 缓冲区
 *       IO流中的数据通过缓冲区交给Channel
 *
 * 服务器端：
 *      1、实例化 ServerSocketChannel（new的时候也可以把端口传进去）
 *      2、绑定端口 通过ServerSocketChannel调用bind()方法
 *      3、设置ServerSocketChannel为非阻塞 configureBlocking....  （connect本身是阻塞方法，设置通道后 无论是否 连接成功都得立即返回）
 *      4、实例化Selector选择器（也叫多路复用器 用来管理channels）
 *      5、将ServerSocketChannel注册到选择器上  ServerSocketChannel.register()
 *      6、监听是否有新的事件 （服务器端叫）接收(（客户端叫）连接)事件/读写事件/     Selector.select()（类似于accept，没事件就阻塞，有事件就是返回一个大于0的结果）
 *      7、获取 已完成事件 的集合，对于这个集合进行遍历，如果发现是Accept事件则进行accept调用（即同意连接，进行TCP三次握手），获取到SocketChannel
 *      8、先将SocketChannel设置为非阻塞，再将其注册到Selector上，关注（注册）read事件
 *      9、监听是否有read读就绪事件
 *      10、若有读就绪事件，就通过SocketChananel通道来读取数据（不是直接和数据源），通过buffer作为传输介质
 *        （可以在读事件中注册一个写事件，也可以不注册，因为是主动发起的）
 *      11、关闭资源 SocketChannel Selector ServerSocketChannel
 *
 * 客户端：
 * 1、实例化 SocketChannel
 * 2、设置 SocketChannel 为非阻塞
 * 3、实例化 Selector（）
 * 4、连接服务器connect，在这个方法中提供ip地址和端口号，
 *      注意：这个方法不是一个阻塞方法，如果连接失败返回false，连接成功返回true
 * 5、如果是false（即没建立连接，要继续去建立连接，监听客户端是否有一个  就绪的连接事件（即建立连接成功的)），
 *    则将SocketChannel注册到Selector选择器中，监听connect中可连接事件（看有没有自己连接成功的消息）
 * 6、监听selector中是否有可完成事件，遍历可完成事件的集合，判断该事件是否是可连接事件。若是，connect方法就会返回true
 * 7、给服务器端发送消息，channel.write（Buffer）
 * 8、关闭资源，selector   SocketChannel
 *
 *
 * ------------------------------------------------------------------------------------------------
 * 自己找的：
 * （使用Selector客户端与服务器的通信  链接：https://blog.csdn.net/canot/article/details/51372651
 * 由于select操作只管对selectedKeys的集合进行添加而不负责移除，所以当某个消息被处理后我们需要从该集合里去掉。）

 https://ifeve.com/selectors/
 （
    register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。
    可以监听四种不同类型的事件：Connect Accept  Read Write
    当向Selector注册Channel时，register()方法会返回一个SelectionKey对象。这个对象包含了一些你感兴趣的属性：如
     interest集合  ready集合  Channel  Selector  附加的对象（可选）

    通道触发了一个事件 意思是  该事件已经就绪。所以，
    某个channel成功连接到另一个服务器称为“连接就绪”。
    一个server socket channel准备好接收新进入的连接称为“接收就绪”。
    一个有数据可读的通道可以说是“读就绪”。
     等待写数据的通道可以说是“写就绪”。）
 *
 * （JAVA NIO 137页）
 * 选择器不是像通道或流(stream)那样的基本 I/O 对象：数据从来没有通过它们进行传递。
 * 当相关的 Selector 上 的 select( )操作正在进行时改变键的 interest 集合，不会影响那个正在进行的选择操作。所有更改将
 * 会在 select( )的下一个调用中体现出来。
 *
 * 选择器类管理着一个被注册的通道集合的信息和它们的就绪状态。
 * 通道是和选择器一起被注册的，并且使用选择器来更新通道的就绪状态。
 *
 *  选择器（selector）  是对 select( )、poll( )等本地调用(native call)或者类似的操作系统特定的系统调用的一个包装。
 *  select( )方法调用，如果没有通道已经准备好，线程可能会在这时阻塞，通常会有一个超时值，直到至少有一个已注册的通道就绪
 * select返回值不是已准备好的通道的总数，而是从上一个 select( )调用之后进入就绪状态的通道的数量。返回值可能是 0。
 *
 * register( )方法 实际上是将通道被注册到选择器上的。
 * 第二个参数表示所关心的通道操作。这是一个表示选择器在检查通道就绪状态时需要关心的操作的比特掩码。
 *
 * ready 集合是interest集合的子集， 并且 表示了 interest 集合中从上次调用 select( )以来已经就绪的那些操作。
 *
 * readyOps( )
 * interestOps( )
 *
 * if (key.isWritable( ))  等价于 if ((key.readyOps( ) & SelectionKey.OP_WRITE) != 0)
 *
 */
public class Teacher_3_16_NIO {
}
