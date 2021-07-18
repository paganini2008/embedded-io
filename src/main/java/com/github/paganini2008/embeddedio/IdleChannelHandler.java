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

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.github.paganini2008.devtools.Assert;
import com.github.paganini2008.devtools.date.DateUtils;
import com.github.paganini2008.devtools.multithreads.Clock;
import com.github.paganini2008.devtools.multithreads.ClockTask;

/**
 * 
 * IdleChannelHandler
 *
 * @author Fred Feng
 * @since 1.0
 */
public class IdleChannelHandler implements ChannelHandler {

	private final long readerTimeout;
	private final long writerTimeout;
	private final long checkInterval;
	private final IdleTimeoutListener idleTimeoutListener;

	public IdleChannelHandler(long readerTimeout, long writerTimeout, long checkInterval, TimeUnit timeUnit,
			IdleTimeoutListener idleTimeoutListener) {
		Assert.isNull(idleTimeoutListener, "Nullable IdleTimeoutListener");
		this.readerTimeout = DateUtils.convertToMillis(readerTimeout, timeUnit);
		this.writerTimeout = DateUtils.convertToMillis(writerTimeout, timeUnit);
		this.checkInterval = DateUtils.convertToMillis(checkInterval, timeUnit);
		this.idleTimeoutListener = idleTimeoutListener;
	}

	private final Map<Channel, long[]> timeHolder = new ConcurrentHashMap<Channel, long[]>();
	private final Map<Channel, ClockTask> taskHolder = new ConcurrentHashMap<Channel, ClockTask>();
	private final Clock clock = new Clock();

	@Override
	public void fireChannelActive(final Channel channel) throws IOException {
		timeHolder.put(channel, new long[] { System.currentTimeMillis(), System.currentTimeMillis() });
		taskHolder.put(channel, new ClockTask() {
			@Override
			protected void runTask() {
				long[] times = timeHolder.get(channel);
				if (times != null) {
					long now = System.currentTimeMillis();
					long lastRead = times[0];
					long lastWriten = times[1];
					if (readerTimeout > 0) {
						if (now - lastRead > readerTimeout) {
							idleTimeoutListener.handleIdleTimeout(channel, readerTimeout);
						}
					}
					if (writerTimeout > 0) {
						if (now - lastWriten > writerTimeout) {
							idleTimeoutListener.handleIdleTimeout(channel, writerTimeout);
						}
					}
				}
			}
		});
		clock.scheduleAtFixedRate(taskHolder.get(channel), checkInterval, checkInterval, TimeUnit.MILLISECONDS);
	}

	@Override
	public void fireChannelInactive(Channel channel) throws IOException {
		ClockTask task = taskHolder.remove(channel);
		if (task != null) {
			task.cancel();
			timeHolder.remove(channel);
		}
	}

	@Override
	public void fireChannelReadable(Channel channel, MessagePacket packet) throws IOException {
		long[] times = timeHolder.get(channel);
		if (times != null) {
			times[0] = System.currentTimeMillis();
		}
	}

	@Override
	public void fireChannelWriteable(Channel channel, MessagePacket packet) throws IOException {
		long[] times = timeHolder.get(channel);
		if (times != null) {
			times[1] = System.currentTimeMillis();
		}
	}

	public static ChannelHandler readerIdle(int readerTimeout, int checkInterval, TimeUnit timeUnit,
			IdleTimeoutListener idleTimeoutListener) {
		return new IdleChannelHandler(readerTimeout, 0, checkInterval, timeUnit, idleTimeoutListener);
	}

	public static ChannelHandler writerIdle(int writerTimeout, int checkInterval, TimeUnit timeUnit,
			IdleTimeoutListener idleTimeoutListener) {
		return new IdleChannelHandler(0, writerTimeout, checkInterval, timeUnit, idleTimeoutListener);
	}
	
	public void releaseOtherResource() {
		clock.stop();
	}

}
