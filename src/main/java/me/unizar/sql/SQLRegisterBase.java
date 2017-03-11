package me.unizar.sql;

import me.unizar.sql.SQLQueries.QueryId;

public class SQLRegisterBase {

	public static final QueryId GET_PASSWORD;
	public static final QueryId REGISTER_USER;
	public static final QueryId ADD_NETWORK;
	public static final QueryId REMOVE_NETWORK;
	public static final QueryId GET_NETWORKS;

	static {
		GET_PASSWORD = SQLQueries.registerQuery(new SQLQueryData("SELECT `pass` FROM `users` WHERE `name` = ?", 1));
		REGISTER_USER = SQLQueries
				.registerQuery(new SQLQueryData("INSERT INTO `users`(`name`, `pass`, `email`) VALUES (?, ?, ?)", 3));
		ADD_NETWORK = SQLQueries.registerQuery(new SQLQueryData("INSERT INTO `social_networks`(`user`, `network`, `value`, `oauth`) VALUES (?, ?, ?, ?);", 4));
		REMOVE_NETWORK = SQLQueries.registerQuery(new SQLQueryData("DELETE FROM `social_networks` WHERE `id` = ?;", 1));
		GET_NETWORKS = SQLQueries.registerQuery(new SQLQueryData("SELECT `id`, `network`, `value` FROM `social_networks` WHERE `user` = ?;", 1));
	}

}
