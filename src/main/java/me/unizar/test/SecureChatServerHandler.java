/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package me.unizar.test;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import me.unizar.packet.IPacket;
import me.unizar.packet.PacketResponse;
import me.unizar.packet.ManagerPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles a server-side channel.
 */
public class SecureChatServerHandler extends SimpleChannelInboundHandler<String> {

	static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	public void channelActive(final ChannelHandlerContext ctx) {
		// Once session is secured, send a greeting and register the channel to
		// the global channel
		// list so the channel received the messages from others.

		channels.add(ctx.channel());

	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		JSONObject obj = new JSONObject(msg);
		
		if(obj.has("pId")){
			try{
				IPacket packet = ManagerPacket.getPacket(obj.getInt("pId"));
				if(packet != null){
					packet.handle(ctx, obj);
				}else{
					ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
				}
			}catch(JSONException ex){
				ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			}
		}else{
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
