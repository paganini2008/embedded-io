/**
* Copyright 2021 Fred Feng (paganini.fy@gmail.com)

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
package com.github.paganini2008.embeddedio;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.paganini2008.devtools.logging.Log;
import com.github.paganini2008.devtools.logging.LogFactory;

/**
 * 
 * LoggingChannelHandler
 *
 * @author Fred Feng
 * @since 1.0
 */
public class LoggingChannelHandler implements ChannelHandler {

	private static final Log log = LogFactory.getLog(LoggingChannelHandler.class);

	private final String side;

	public LoggingChannelHandler(String side) {
		this.side = side;
	}

	@Override
	public void fireChannelActive(Channel channel) throws IOException {
		log.info("Channel is active. Channel info: " + channel);
	}

	@Override
	public void fireChannelInactive(Channel channel) throws IOException {
		log.info("Channel is inactive. Channel info: " + channel);
	}

	private final AtomicInteger counter = new AtomicInteger();

	@Override
	public void fireChannelReadable(Channel channel, MessagePacket packet) throws IOException {
		log.info("Channel read length: " + packet.getLength());
		packet.getMessages().forEach(data -> {
			log.info("[" + counter.incrementAndGet() + "]: " + data);
			if ("server".equals(side)) {
				//channel.write("ok");
			}
		});
	}

	@Override
	public void fireChannelWriteable(Channel channel, MessagePacket packet) throws IOException {
		//log.info("Channel write length: " + packet.getLength() + ", size: " + packet.getMessages().size());
	}

	@Override
	public void fireChannelFatal(Channel channel, Throwable e) {
		log.info("Channel has fatal error. Channel info: " + channel);
		log.error(e.getMessage(), e);
	}

}
