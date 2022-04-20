package com.sias.mq.two;

import com.rabbitmq.client.Channel;
import com.sias.mq.Utils.RabbitUtils;

import java.util.Scanner;

/**
 * @author Edgar
 * @create 2022-04-20 20:15
 * @faction:
 */
public class Task01 {

    public static final String QUEUE_NAME="hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitUtils.getChannel();

        /*01.先去建立连接，在去发送消息*/
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);


        /*02.从键盘上发送的消息可能很多
        *    因此，把发送消息的那段代码写在循环体中
        *    直到循环体结束的时候，不在发送消息
        *
        *
        *
        *    参数中的System.in是获取从键盘上输入的值
        *    下面的循环，hasNext是从对象中扫描数据是否还有
        *    有，有的话为true，放行，下面next是获取
        *    从键盘上打印的数据*/
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String next = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,next.getBytes());
            System.out.println("消息发送完成："+next);
        }
    }
}
