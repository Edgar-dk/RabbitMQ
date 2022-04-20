package com.sias.mq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.sias.mq.Utils.RabbitUtils;
import org.junit.jupiter.api.Test;

/**
 * @author Edgar
 * @create 2022-04-20 19:40
 * @faction:  这是一个接受消息的工作线程（相当于一个消费者）
 */
public class Worker01 {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {

        /*01.引入写好的公共部分
         *
         *    这个部分是，创建好的连接工厂
         *    以及信道，在使用的时候，直接引入即可*/
        Channel channel = RabbitUtils.getChannel();


        /*02.接收消息*/
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            /*把消息转换一下，对象---->消息体*/
            System.out.println("收到的消息：" + new String(message.getBody()));
        };
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println(consumerTag + "消息被打断，没有接受到消息");
        };

        System.out.println("C2等待者接受消息");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}




