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
package com.github.paganini2008.embeddedio;

import com.github.paganini2008.devtools.event.Event;

/**
 * 
 * ChannelEvent
 * 
 * @author Fred Feng
 * @since 2.0.1
 */
public class ChannelEvent extends Event<Object> {

	private static final long serialVersionUID = 5631405024269581391L;

	public ChannelEvent(Channel source, EventType eventType) {
		this(source, eventType, null, null);
	}

	public ChannelEvent(Channel source, EventType eventType, MessagePacket messagePacket, Throwable cause) {
		super(source, null);
		this.eventType = eventType;
		this.messagePacket = messagePacket;
		this.cause = cause;
	}

	private final EventType eventType;
	private final MessagePacket messagePacket;
	private final Throwable cause;

	public MessagePacket getMessagePacket() {
		return messagePacket;
	}

	public EventType getEventType() {
		return eventType;
	}

	public Throwable getCause() {
		return cause;
	}

	public Channel getChannel() {
		return (Channel) getSource();
	}

	public static enum EventType {
		ALL, ACTIVE, INACTIVE, READABLE, WRITEABLE, FATAL
	}

}
