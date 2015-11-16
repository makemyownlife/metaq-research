/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Authors:
 *   wuhua <wq163@163.com> , boyan <killme2008@gmail.com>
 */
package com.mylife.metaq.research.office;


import com.taobao.metamorphosis.Message;
import com.taobao.metamorphosis.client.MessageSessionFactory;
import com.taobao.metamorphosis.client.MetaMessageSessionFactory;
import com.taobao.metamorphosis.client.producer.MessageProducer;
import com.taobao.metamorphosis.client.producer.SendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Producer {

   private static Logger logger = LoggerFactory.getLogger("cacheLog");

    public static void main(final String[] args) throws Exception {
        final MessageSessionFactory sessionFactory = new MetaMessageSessionFactory(Help.initMetaConfig());
        final MessageProducer producer = sessionFactory.createProducer();
        // publish topic
        final String topic = "mytest";
        producer.publish(topic);

        String line = "lilin";
        for(int i = 0 ; i < 100;i++) {
            long start = System.currentTimeMillis();
            // send message
            final SendResult sendResult = producer.sendMessage(new Message(topic, line.getBytes()));
            // check result
            if (!sendResult.isSuccess()) {
                logger.info("Send message failed,error message:" + sendResult.getErrorMessage());
            } else {
                logger.info("Send message successfully,sent to " + sendResult.getPartition());
            }
        }
    }


    private static String readLine(final BufferedReader reader) throws IOException {
        System.out.println("Type a message to send:");
        return reader.readLine();
    }
}