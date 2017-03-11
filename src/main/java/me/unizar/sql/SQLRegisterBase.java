package me.unizar.sql;

import me.unizar.sql.SQLQueries.QueryId;

public class SQLRegisterBase {

	public static final QueryId GET_PASSWORD;
	public static final QueryId REGISTER_USER;
	public static final QueryId ADD_NETWORK;
	public static final QueryId REMOVE_NETWORK;
	public static final QueryId GET_NETWORKS;
	public static final QueryId ADD_FILTER;
	public static final QueryId REMOVE_FILTER;
	public static final QueryId GET_FILTER;
	public static final QueryId GET_FILTERS;

	static {
		GET_PASSWORD = SQLQueries.registerQuery(new SQLQueryData("SELECT `pass` FROM `users` WHERE `name` = ?", 1));
		REGISTER_USER = SQLQueries
				.registerQuery(new SQLQueryData("INSERT INTO `users`(`name`, `pass`, `email`) VALUES (?, ?, ?)", 3));
		ADD_NETWORK = SQLQueries.registerQuery(new SQLQueryData("INSERT INTO `social_networks`(`user`, `network`, `value`, `oauth`) VALUES (?, ?, ?, ?);", 4));
		REMOVE_NETWORK = SQLQueries.registerQuery(new SQLQueryData("DELETE FROM `social_networks` WHERE `id` = ?;", 1));
		GET_NETWORKS = SQLQueries.registerQuery(new SQLQueryData("SELECT `id`, `network`, `value` FROM `social_networks` WHERE `user` = ?;", 1));
		
		ADD_FILTER = SQLQueries.registerQuery(new SQLQueryData("INSERT INTO `filters`(`user`, `filter`) VALUES (?, ?);", 2));
		REMOVE_FILTER = SQLQueries.registerQuery(new SQLQueryData("DELETE FROM `filters` WHERE `filterId` = ?;", 1));
		GET_FILTERS = SQLQueries.registerQuery(new SQLQueryData("SELECT `filterId`, `filter` FROM `filters` WHERE `user` = ?;", 1));
		GET_FILTER = SQLQueries.registerQuery(new SQLQueryData("SELECT `filter` FROM `filters` WHERE `filterId` = ?;", 1));
	}

}
