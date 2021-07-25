/**
* Copyright 2017-2021 Fred Feng (paganini.fy@gmail.com)

* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.paganini2008.embeddedio.examples;

import java.net.InetSocketAddress;
import java.util.UUID;

import com.github.paganini2008.embeddedio.Channel;
import com.github.paganini2008.embeddedio.ChannelHandler;
import com.github.paganini2008.embeddedio.LoggingChannelHandler;
import com.github.paganini2008.embeddedio.NioConnector;
import com.github.paganini2008.embeddedio.ObjectSerialization;
import com.github.paganini2008.embeddedio.ChannelPromise;
import com.github.paganini2008.embeddedio.StringSerialization;

public class TestClient {

	public static void main(String[] args) throws Exception {
		NioConnector client = new NioConnector();
		client.getTransformer().setSerialization(new ObjectSerialization(), new StringSerialization());
		client.setWriterBufferSize(20 * 1024);
		client.setWriterBatchSize(10);
		client.setAutoFlushInterval(3);
		ChannelHandler handler = new LoggingChannelHandler("client");
		client.addHandler(handler);
		Channel channel;
		try {
			channel = client.connect(new InetSocketAddress(8090), new ChannelPromise<Channel>() {

				@Override
				public void onSuccess(Channel channel) {
					System.out.println(channel + " is ok");
				}

				@Override
				public void onFailure(Throwable e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		System.in.read();
		for (int i = 0; i < 100000; i++) {
			channel.write(new Item("fengy_" + i, toFullString()));
		}
		Thread.sleep(60 * 60 * 1000L);
		client.close();
		System.out.println("TestClient.main()");
	}

	private static String toFullString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < 100; i++) {
			str.append(UUID.randomUUID().toString());
		}
		return str.toString();
	}

}
