package me.unizar;

import java.util.logging.Logger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import me.unizar.sql.MySQLConnection;
import me.unizar.test.SecureChatServerInitializer;

public class UCode2017 {

	public static Logger LOGGER = Logger.getLogger("uCode2017");
	private static final MySQLConnection SQL = new MySQLConnection("host", "usr", "pass", "db");

	static final int PORT = Integer.parseInt(System.getProperty("port", "8992"));

    public static void main(String[] args) throws Exception {
        //SelfSignedCertificate ssc = new SelfSignedCertificate();
        //SslContext sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey())
        //    .build();

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new SecureChatServerInitializer());

            b.bind(PORT).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

	public static MySQLConnection getSql() {
		return SQL;
	}
}
