package com.sias.mq.one;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Edgar
 * @create 2022-04-20 13:53
 * @faction:
 */



/*这个是一个生产者，可以发送消息*/
public class Producer {


    /*1.定义一个公共的变量*/
    public static final String QUEUE_NAME="hello";


    /*2，测试*/
    public static void main(String[] args) throws IOException, TimeoutException {
        /*01.创建一个连接工厂
         *
         *    就是先连接上客户端
         *    这个时候可能有一个问题，为什么有连接工厂呢，原因在于，想要去
         *    用信道和队列去连接，需要在一个大的基础上，搭建一个框架，然后在
         *    框架之内，可以把二者连接起来*/
        ConnectionFactory factory = new ConnectionFactory();

        /*02.设置工厂的IP,以及连接的用户名和密码
         * 有了这个IP，账号，密码。就可以连接到RabbitMQ队列
         * 然后才可以以后的操作*/
        factory.setHost("192.168.17.131");
        factory.setUsername("admin");
        factory.setPassword("123");

        /*03.创建连接
         *    这个连接，只是在一个大的基础上去搭建
         *    一个框架，然后在框架里面去增加连接的通道
         *    通过通道的方式去和队列连接（消息互通）*/
        Connection connection = factory.newConnection();


        /*04.增加信道（在连接里面）
             在连接里面去增加信道(可以有多个信道)*/
        Channel channel = connection.createChannel();


        /*05.信道连接队列（简单的话，连接队列，复杂的话，可以连接交换机）
         *
         *    先连接后通信（其实也可以在计算机网络中，一边通信，一边连接），
         *    需要指明连接的名字，以及准备的信息（为了发送消息的时候使用）
         *
         * 1.第一个是队列的名称（没有交换机，直接连接队列）
         * 2.第二个，队列里面的消息是否可以持久化（保存在磁盘上），默认存储在内存（false）
         * 3.ture多个消费者可以从这个队列中获取消息，false只能有一个
         * 4.true自动删除，false不自动删除
         * 5.其他参数
         *
         * */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);



        /*06.消息实体*/
        String message ="hello word";


        /*07.发送消息
         *
         * 1.发送的交换机（没有的话，默认为空）
         * 2.路由的Key是哪一个，本次是队列的名称
         * 3.其它参数
         * 4.发送消息实体，需要把消息转换成二进制的形式
         **/
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("这个消息发送完毕了");
    }

}
