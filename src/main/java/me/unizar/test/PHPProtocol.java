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

import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

import me.unizar.packet.IPacket;
import me.unizar.packet.ManagerPacket;

/**
 * Handles a server-side channel.
 */
public class PHPProtocol {

	public boolean channelRead(PrintWriter ctx, String msg) throws Exception {
		System.err.println("New packet received " + msg);
		JSONObject obj = new JSONObject(msg);
		if (obj.has("pId")) {
			try {
				IPacket packet = ManagerPacket.getPacket(obj.getInt("pId"));
				if (packet != null) {
					packet.handle(ctx, obj);
				} else {
					ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
				}
			} catch (JSONException ex) {
				ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
			}
		} else {
			ManagerPacket.sendErrorMessage(ctx, "Malformed packet!");
		}

		return true;

		// ctx.channel().closeFuture().syncUninterruptibly().channel().close();
	}
}
