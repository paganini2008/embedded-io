/**
* Copyright 2018-2021 Fred Feng (paganini.fy@gmail.com)

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

import java.util.concurrent.Executor;

import com.github.paganini2008.devtools.event.EventBus;
import com.github.paganini2008.devtools.event.EventSubscriber;
import com.github.paganini2008.embeddedio.ChannelEvent.EventType;

/**
 * 
 * DefaultChannelEventPublisher
 *
 * @author Fred Feng
 * @since 1.0
 */
public class DefaultChannelEventPublisher implements ChannelEventPublisher {

	private final EventBus<ChannelEvent, Object> delegate;

	public DefaultChannelEventPublisher(Executor executor) {
		this.delegate = new EventBus<>(executor, true, true);
	}

	@Override
	public void publishChannelEvent(ChannelEvent event) {
		delegate.publish(event);
	}

	@Override
	public void subscribeChannelEvent(ChannelHandler channelHandler) {
		subscribeChannelEvent(new ChannelEventListenerAdaptor(channelHandler));
	}

	@Override
	public void subscribeChannelEvent(final ChannelEventListener listener) {

		delegate.subscribe(new EventSubscriber<ChannelEvent, Object>() {
			@Override
			public void onEventFired(ChannelEvent event) {
				if (listener.getEventType() == ChannelEvent.EventType.ALL || listener.getEventType() == event.getEventType()) {
					listener.onEventFired(event);
				}
			}
		});
	}

	@Override
	public void destroy() {
		delegate.close();
	}

	/**
	 * 
	 * ChannelEventListenerAdaptor
	 *
	 * @author Fred Feng
	 * @since 1.0
	 */
	private class ChannelEventListenerAdaptor implements ChannelEventListener {

		private final ChannelHandler handler;

		ChannelEventListenerAdaptor(ChannelHandler handler) {
			this.handler = handler;
		}

		@Override
		public void onEventFired(ChannelEvent event) {
			Channel channel = event.getChannel();
			try {
				switch (event.getEventType()) {
				case ACTIVE:
					handler.fireChannelActive(channel);
					break;
				case INACTIVE:
					handler.fireChannelInactive(channel);
					break;
				case READABLE:
					handler.fireChannelReadable(channel, event.getMessagePacket());
					break;
				case WRITEABLE:
					handler.fireChannelWriteable(channel, event.getMessagePacket());
					break;
				case FATAL:
					handler.fireChannelFatal(channel, event.getCause());
					break;
				default:
					break;
				}
			} catch (Throwable e) {
				publishChannelEvent(new ChannelEvent(channel, EventType.FATAL, null, e));
				channel.close();
			}
		}

	}

}
