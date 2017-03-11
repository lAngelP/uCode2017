package me.unizar.sql;

import me.unizar.sql.SQLQueries.QueryId;

public class SQLRegisterBase {

	public static final QueryId GET_PASSWORD;
	public static final QueryId REGISTER_USER;

	static {
		GET_PASSWORD = SQLQueries.registerQuery(new SQLQueryData("SELECT `pass` FROM `users` WHERE `name` = ?", 1));
		REGISTER_USER = SQLQueries
				.registerQuery(new SQLQueryData("INSERT INTO `users`(`name`, `pass`, `email`) VALUES (?, ?, ?)", 3));
	}

}
