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

import com.github.paganini2008.devtools.logging.Log;
import com.github.paganini2008.devtools.logging.LogFactory;

/**
 * 
 * IdleTimeoutListener
 *
 * @author Fred Feng
 * @since 1.0
 */
@FunctionalInterface
public interface IdleTimeoutListener {

	static final Log logger = LogFactory.getLog(IdleTimeoutListener.class);

	void handleIdleTimeout(Channel channel, long timeout);

	static final IdleTimeoutListener LOG = new IdleTimeoutListener() {

		@Override
		public void handleIdleTimeout(Channel channel, long timeout) {
			logger.warn(channel.toString() + " is timeout.");
		}
	};

	static final IdleTimeoutListener CLOSE = new IdleTimeoutListener() {

		@Override
		public void handleIdleTimeout(Channel channel, long timeout) {
			logger.warn(channel.toString() + " is timeout.");
			channel.close();
		}
	};

}
