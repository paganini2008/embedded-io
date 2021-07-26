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

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 
 * IoBuffer
 *
 * @author Fred Feng
 * @since 2.0.1
 */
public interface IoBuffer {

	IoBuffer append(String value);

	IoBuffer append(String value, Charset charset);

	String getString();

	String getString(Charset charset);

	IoBuffer append(ByteBuffer bb);

	IoBuffer append(byte[] bytes);

	byte[] getBytes();

	IoBuffer append(double value);

	double getDouble();

	IoBuffer append(long value);

	long getLong();

	IoBuffer append(float value);

	float getFloat();

	IoBuffer append(int value);

	int getInt();

	IoBuffer append(short value);

	short getShort();

	IoBuffer append(char value);

	char getChar();

	IoBuffer append(byte value);

	byte getByte();

	long length();

	IoBuffer flip();

	IoBuffer limit(int limit);

	int limit();

	int position();

	int remaining();

	boolean hasRemaining();

	boolean hasRemaining(int length);

	void get(byte[] bytes);

	void reset();

	void clear();

	ByteBuffer get();

}