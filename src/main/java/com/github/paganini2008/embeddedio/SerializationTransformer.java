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

import java.util.List;

/**
 * 
 * SerializationTransformer
 *
 * @author Fred Feng
 * @since 1.0
 */
public class SerializationTransformer implements Transformer {

	private Serialization encoder = new ObjectSerialization();
	private Serialization decoder = new ObjectSerialization();

	public void setSerialization(Serialization encoder, Serialization decoder) {
		this.encoder = encoder;
		this.decoder = decoder;
	}

	@Override
	public void transferTo(Object value, IoBuffer buffer) {
		byte[] bytes = encoder.serialize(value);
		buffer.append(bytes);
	}

	@Override
	public void transferFrom(IoBuffer buffer, List<Object> output) {
		Object object;
		while (buffer.hasRemaining(4)) {
			byte[] bytes = buffer.getBytes();
			if (bytes != null) {
				object = decoder.deserialize(bytes);
				output.add(object);
			} else {
				break;
			}
		}
	}

}
