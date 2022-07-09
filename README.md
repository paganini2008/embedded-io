# embedded-io
A lightweight  NIO framework with JDK8

##  Compatibility

* Jdk 1.8 (or later)

## Install

```xml
<dependency>
    <groupId>com.github.paganini2008</groupId>
	<artifactId>embedded-io</artifactId>
    <version>2.0.4</version>
</dependency>
```

## Quick Start

**Example 1**

Start Nio Server

``` java
public static void main(String[] args) throws Exception {
		NioAcceptor server = new NioAcceptor();
		server.getTransformer().setSerialization(new StringSerialization(), new ObjectSerialization());
		server.setReaderBufferSize(10 * 1024);
		LoggingChannelHandler handler = new LoggingChannelHandler("server");
		server.addHandler(handler);
		server.start();
		System.in.read();
		server.stop();
}
```

Start Nio Client

``` java
public static void main(String[] args) throws Exception {
		NioConnector client = new NioConnector();
		client.getTransformer().setSerialization(new ObjectSerialization(), new StringSerialization());
		client.setWriterBufferSize(20 * 1024);
		client.setWriterBatchSize(10);
		client.setAutoFlushInterval(3);
		ChannelHandler handler = new LoggingChannelHandler("client");
		client.addHandler(handler);
		Channel channel;
		try {
			channel = client.connect(new InetSocketAddress(8090), new ChannelPromise<Channel>() {

				@Override
				public void onSuccess(Channel channel) {
					System.out.println(channel + " is ok");
				}

				@Override
				public void onFailure(Throwable e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		System.in.read();
		for (int i = 0; i < 10000; i++) {
			channel.write(new Item("test_" + i, UUID.randomUUID().toString()));
		}
		Thread.sleep(60 * 1000L);
		client.close();
	}
```



**Example 2**

Start Aio Server

``` java
public static void main(String[] args) throws Exception {
		AioAcceptor server = new AioAcceptor();
		server.getTransformer().setSerialization(new StringSerialization(), new ObjectSerialization());
		server.setReaderBufferSize(10 * 1024);
		LoggingChannelHandler handler = new LoggingChannelHandler("server");
		server.addHandler(IdleChannelHandler.readerIdle(60, 60, TimeUnit.SECONDS, IdleTimeoutListener.LOG));
		server.addHandler(handler);
		server.start();
		System.in.read();
		server.stop();
}
```



Start Client

``` java
public static void main(String[] args) throws Exception {
		AioConnector client = new AioConnector();
		client.getTransformer().setSerialization(new ObjectSerialization(), new StringSerialization());
		// client.setWriterBufferSize(20 * 1024);
		client.addHandler(IdleChannelHandler.writerIdle(30, 60, TimeUnit.SECONDS, IdleTimeoutListener.LOG));
		client.setWriterBatchSize(10);
		client.setAutoFlushInterval(3);
		ChannelHandler handler = new LoggingChannelHandler("client");
		client.addHandler(handler);
		Channel channel;
		try {
			channel = client.connect(new InetSocketAddress("127.0.0.1", 8090), new ChannelPromise<Channel>() {

				@Override
				public void onSuccess(Channel channel) {
					System.out.println(channel + " is ok");
				}

				@Override
				public void onFailure(Throwable e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		System.in.read();
		for (int i = 0; i < 10000; i++) {
			channel.write(new Item("test_" + i, UUID.randomUUID().toString()));
		}
		Thread.sleep(60 * 1000L);
		client.close();
	}
```

