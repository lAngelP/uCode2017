package me.unizar.sql;

import me.unizar.sql.SQLQueries.QueryId;

public class SQLRegisterBase {

	public static final QueryId ASSIGN_BOOST_TO_USER;
	public static final QueryId ASSIGN_RANK;
	public static final QueryId GET_MARK_REASONS;
	public static final QueryId INSERT_BUG;
	public static final QueryId GET_UUID_FROM_NAME;
	public static final QueryId GET_USERID_FROM_NAME;
	public static final QueryId GET_USER_NAME_FROM_UUID;
	public static final QueryId GET_USER_NAME_FROM_ID;
	public static final QueryId UPDATE_LEVEL;
	public static final QueryId UPDATE_LEVEL_PERC;
	public static final QueryId INCREMENT_USER_COINS;
	public static final QueryId DECREMENT_USER_COINS;
	public static final QueryId GET_ALL_RANKS;
	public static final QueryId INSERT_USER_OPTIONS;
	public static final QueryId INSERT_OPTION_BIG;
	public static final QueryId GET_USER_DATA;
	public static final QueryId UPDATE_COINS_MULTIPLIERS;
	public static final QueryId UPDATE_XP_MULTIPLIERS;
	public static final QueryId INSERT_USER;
	public static final QueryId INSERT_LOG;
	public static final QueryId UPDATE_NICK_FROM_UUID;
	public static final QueryId INSERT_IGNORE;
	public static final QueryId DELETE_IGNORE;
	//public static final QueryId GET_UUID_COINS;
	public static final QueryId RESET_COINS;
	public static final QueryId GET_PERMISSIONS;

	static {
		ASSIGN_BOOST_TO_USER = SQLQueries.registerQuery(
				new SQLQueryData("INSERT INTO `user_boosts`(`boostId`, `buyTime`, `userId`) VALUES (?, ?, ?)", 3));
		ASSIGN_RANK = SQLQueries
				.registerQuery(new SQLQueryData("UPDATE `users` SET `rank`= ?, `vipTime`= ? WHERE `uuid` = ?", 3));
		GET_MARK_REASONS = SQLQueries.registerQuery(
				new SQLQueryData("SELECT `reason` FROM `marks` WHERE `uuidMarked` = ? ORDER BY `when` DESC", 1));
		INSERT_BUG = SQLQueries.registerQuery(new SQLQueryData(
				"INSERT INTO `issues` (`name`, `desc`, `reproduce`, `posted`, `private`) VALUES " + "(?, ?, ?, ?, ?)",
				5));
		GET_UUID_FROM_NAME = SQLQueries
				.registerQuery(new SQLQueryData("SELECT `uuid` FROM `users` WHERE `name` = ?", 1));
		GET_USERID_FROM_NAME = SQLQueries
				.registerQuery(new SQLQueryData("SELECT `id` FROM `users` WHERE `name` = ?", 1));
		GET_USER_NAME_FROM_UUID = SQLQueries
				.registerQuery(new SQLQueryData("SELECT `name` FROM `users` WHERE `uuid` = ?", 1));
		GET_USER_NAME_FROM_ID = SQLQueries
				.registerQuery(new SQLQueryData("SELECT `name` FROM `users` WHERE `id` = ?", 1));
		UPDATE_LEVEL = SQLQueries
				.registerQuery(new SQLQueryData("UPDATE `users` SET `xpPerc` = ?, `exp` = ? WHERE `uuid` =?", 3));
		UPDATE_LEVEL_PERC = SQLQueries
				.registerQuery(new SQLQueryData("UPDATE `users` SET `xpPerc` = ? WHERE `uuid` = ?", 2));
		INCREMENT_USER_COINS = SQLQueries
				.registerQuery(new SQLQueryData("UPDATE `users` SET `coins` = `coins` + ? WHERE `uuid` = ?", 2));
		DECREMENT_USER_COINS = SQLQueries
				.registerQuery(new SQLQueryData("UPDATE `users` SET `coins` = `coins` - ? WHERE `uuid` = ?", 2));
		GET_ALL_RANKS = SQLQueries.registerQuery(new SQLQueryData("SELECT `id`, `color`, `chatColor` FROM `ranks`", 0));
		INSERT_USER_OPTIONS = SQLQueries
				.registerQuery(new SQLQueryData("INSERT INTO `user_options`(`uuid`) VALUES (?)", 1));
		INSERT_OPTION_BIG = SQLQueries
				.registerQuery(new SQLQueryData("INSERT INTO `user_options`(`uuid`, ?) VALUES (?, ?)", 3));
		GET_USER_DATA = SQLQueries.registerQuery(
				new SQLQueryData("SELECT `id`, `coins`, `nick`, `exp`, `rank`, `subrank`, `xpPerc`, `lang`,"
						+ "`coinMultiplier`, `coinMultiplierTime`, `xpMultiplier`, `xpMultiplierTime`, `vipTime` "
						+ "FROM `users` WHERE `uuid` = ?", 1));
		UPDATE_COINS_MULTIPLIERS = SQLQueries.registerQuery(new SQLQueryData(
				"UPDATE `users` SET `coinMultiplierTime`=0 AND `coinMultiplier`=1 WHERE `uuid`=?;", 1));
		UPDATE_XP_MULTIPLIERS = SQLQueries.registerQuery(
				new SQLQueryData("UPDATE `users` SET `xpMultiplierTime`=0 AND `xpMultiplier`=1 WHERE `uuid`=?;", 1));
		INSERT_USER = SQLQueries.registerQuery(
				new SQLQueryData("INSERT INTO `users`(`uuid`, `regTime`, `nick`, `name`) VALUES (?, ?, ?, ?)", 4));
		INSERT_LOG = SQLQueries.registerQuery(new SQLQueryData(
				"INSERT INTO `logs`(`type`, `logger`, `logged`, `message`, `extra`) VALUES " + "(?, ?, ?, ?, ?);", 5));
		UPDATE_NICK_FROM_UUID = SQLQueries
				.registerQuery(new SQLQueryData("UPDATE `users` SET `nick` = ? WHERE `uuid` = ?", 2));
		INSERT_IGNORE = SQLQueries
				.registerQuery(new SQLQueryData("INSERT INTO `user_ignore`(`ignore`, `ignored`) VALUES (?, ?);", 2));
		DELETE_IGNORE = SQLQueries
				.registerQuery(new SQLQueryData("DELETE FROM `user_ignore` WHERE `ignore` = ? AND `ignored` = ?", 2));
		//GET_UUID_COINS = SQLQueries.registerQuery(new SQLQueryData("SELECT `coins` FROM `users` WHERE `uuid` = ?", 1));
		RESET_COINS = SQLQueries.registerQuery(new SQLQueryData("UPDATE `users` SET `coins` = 0 WHERE `uuid` = ?", 1));
		GET_PERMISSIONS = SQLQueries.registerQuery(new SQLQueryData("SELECT * FROM `permissions`", 0));
	}

}
