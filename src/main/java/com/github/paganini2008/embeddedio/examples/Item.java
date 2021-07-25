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

import java.io.Serializable;
import java.nio.ByteBuffer;

import com.github.paganini2008.devtools.multithreads.AtomicIntegerSequence;

public class Item implements Serializable {

	private static final long serialVersionUID = -4323424140330874831L;

	private String name;
	private Object value;

	public Item() {
	}

	public Item(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Item [name=" + name + ", value=" + value + "]";
	}

	public static void main(String[] args) {
		ByteBuffer buffer = ByteBuffer.allocate(20);
		buffer.putInt(3);
		buffer.putDouble(16.2D);
		System.out.println(buffer);
		
		buffer.flip();
		int i = buffer.getInt();
		System.out.println(i);
		System.out.println(buffer);
		buffer.compact();
		System.out.println(buffer);
		buffer.flip();
		double d = buffer.getDouble();
		System.out.println(d);
	}

}
