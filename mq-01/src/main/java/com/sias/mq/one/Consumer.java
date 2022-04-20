package com.sias.mq.one;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Edgar
 * @create 2022-04-20 18:15
 * @faction:  接收消息
 */
public class Consumer {

    /*01.接受消息的队列名字*/
    public static final String QUEUE_NAME="hello";


    public static void main(String[] args) throws IOException, TimeoutException {

        /*02.在接受消息的时候
         *
         *    需要和生产消息的那个用户名和
         *    以及账号，和IP地址一样，才可以
         *    接受到消息*/
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.17.131");
        factory.setUsername("admin");
        factory.setPassword("123");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        /*接收消息
         *
         * 1.第一个是接收那个队列的消息
         * 2.第二个，消息成功之后，是否自动应答，true是自动应答，false手动应答
         * 3.消费者接受消息（如果说消费者成功接受到了消息
         *                那么，下面的消息中断就不执行，
         *                如果消费者没有成功消费掉消息
         *                执行回掉，就是下面的哪一个）
         * 4.接受消息的时候被中断
         * */


        DeliverCallback deliverCallback=(consumerTag,message)->{
            /*把消息转换一下，对象---->消息体*/
            System.out.println(new String(message.getBody()));
        };
        CancelCallback cancelCallback=consumerTag->{
            System.out.println("消息被打断，没有接受到消息");
        };
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }

}
