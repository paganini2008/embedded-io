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
package com.github.paganini2008.embeddedio.test;

import java.util.concurrent.TimeUnit;

import com.github.paganini2008.embeddedio.AioAcceptor;
import com.github.paganini2008.embeddedio.IdleChannelHandler;
import com.github.paganini2008.embeddedio.IdleTimeoutListener;
import com.github.paganini2008.embeddedio.LoggingChannelHandler;
import com.github.paganini2008.embeddedio.ObjectSerialization;
import com.github.paganini2008.embeddedio.StringSerialization;

public class TestServer {

	public static void main(String[] args) throws Exception {
		AioAcceptor server = new AioAcceptor();
		server.getTransformer().setSerialization(new StringSerialization(), new ObjectSerialization());
		server.setReaderBufferSize(10 * 1024);
		LoggingChannelHandler handler = new LoggingChannelHandler("server");
		server.addHandler(IdleChannelHandler.readerIdle(60, 60, TimeUnit.SECONDS, IdleTimeoutListener.LOG));
		server.addHandler(handler);
		server.start();
		System.in.read();
		server.stop();
		System.out.println("TestServer.main()");
	}

}
