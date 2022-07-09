/**
* Copyright 2017-2022 Fred Feng (paganini.fy@gmail.com)

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

import java.io.Serializable;
import java.util.List;

import com.github.paganini2008.devtools.collection.CollectionUtils;

/**
 * 
 * MessagePacket
 *
 * @author Fred Feng
 * @since 2.0.1
 */
public class MessagePacket implements Serializable {

	private static final long serialVersionUID = -4067748468303232269L;
	private final List<Object> messages;
	private long length;

	MessagePacket(List<Object> messages, long length) {
		this.messages = messages;
		this.length = length;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public List<Object> getMessages() {
		return messages;
	}

	public Object getMessage() {
		return CollectionUtils.isNotEmpty(messages) ? messages.get(0) : null;
	}

	public static MessagePacket of(List<Object> messages, long length) {
		return new MessagePacket(messages, length);
	}

}
