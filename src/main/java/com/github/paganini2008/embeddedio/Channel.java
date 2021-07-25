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

import java.net.SocketAddress;

/**
 * 
 * Channel
 *
 * @author Fred Feng
 * @since 1.0
 */
public interface Channel {

	long writeAndFlush(Object message);

	long write(Object message);

	long flush();

	long read();

	boolean isActive();
	
	SocketAddress getLocalAddr();
	
	SocketAddress getRemoteAddr();
	
	void close();

}