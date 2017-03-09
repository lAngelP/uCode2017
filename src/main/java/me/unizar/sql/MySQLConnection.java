package me.unizar.sql;

import me.unizar.UCode2017;
import me.unizar.sql.SQLQueries.QueryId;
import me.unizar.sql.parameters.ISQLParameter;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLConnection {

	private final HikariDataSource con;

	/**
	 * Creates a new empty MySQL connection.
	 * <p>
	 * <b>PRECONDITION:</b> The plugin who creates the connection can only be
	 * the API.
	 */
	
	public MySQLConnection(String host, String user, String pass, String db) {
		this(host, 3306, user, pass, db);
	}

	/**
	 * Creates a new empty MySQL connection.
	 * <p>
	 * <b>PRECONDITION:</b> The plugin who creates the connection can only be
	 * the API.
	 */
	
	public MySQLConnection(String host, int port, String user, String pass, String db) {
		con = new HikariDataSource();
		con.setMaximumPoolSize(10);
		con.setPoolName("DubAPI_SQL");
		con.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		con.addDataSourceProperty("serverName", host);
		con.addDataSourceProperty("port", port);
		con.addDataSourceProperty("databaseName", db);
		con.addDataSourceProperty("user", user);
		con.addDataSourceProperty("password", pass);
	}

	/**
	 * Closes all the connections [set] has opened.
	 * <p>
	 * <b>NOTE:</b> Must be called after a call to
	 * {@link #runAsyncRawQuery(String)},
	 * {@link #runAsyncQuery(QueryId, ISQLParameter...)}.
	 */
	public static void closeStatement(ResultSet set) {
		try {
			set.getStatement().getConnection().close();
		} catch (SQLException | NullPointerException e1) {
			// e1.printStackTrace();
		}
		try {
			set.getStatement().close();
		} catch (SQLException | NullPointerException e) {
			// e.printStackTrace();
		}

		try {
			set.close();
		} catch (SQLException | NullPointerException e) {
			// e.printStackTrace();
		}
	}

	/**
	 * Closes all the connections opened with the DB.
	 * <p>
	 * <b>NOTE: </b>This method mustn't be called outside the API.
	 * </p>
	 */
	
	public void onDisable() {
		con.close();
	}

	/**
	 * Perform an <b>ASYNC</b> update.
	 * <p>
	 * <b>NOTE:</b> Don't call this method unless the query is so complex that
	 * the API cannot handle it (e.g. columns needs to be built at runtime).
	 * <p>
	 * Use {@link #runAsyncUpdate(QueryId, ISQLParameter[])} instead.
	 */
	
	public boolean runAsyncRawUpdate(String query) {
		Connection con = null;
		boolean updated = false;

		PreparedStatement p = null;

		try {
			con = this.con.getConnection();

			p = con.prepareStatement(query);

			p.executeUpdate();
			updated = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// IF STH FAILS WE WILL CLOSE EVERYTHING
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (p != null) {
				try {
					p.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return updated;
	}

	/**
	 * Performs an <b>ASYNC</b> update.
	 * <p>
	 * <b>See also: </b>SQLQueries.registerQuery()
	 * </p>
	 *
	 * @param queryId    : Valid registered queryId.
	 * @param parameters : List of parameters (one for each char ?).
	 */
	
	public boolean runAsyncUpdate(QueryId queryId, ISQLParameter... parameters) {
		Connection con = null;
		boolean updated = false;

		SQLQueryData queryData = SQLQueries.getDataForQuery(queryId);
		String query = queryData.getQuery();
		int params = queryData.getParams();

		if (parameters.length < params) {
			UCode2017.LOGGER.warning("Couldn't execute query.");
			UCode2017.LOGGER.warning("Query: " + query);
			UCode2017.LOGGER.warning("Parameters expected: " + params);
			UCode2017.LOGGER.warning("Parameters got: " + parameters.length);
		}

		PreparedStatement p = null;

		try {
			con = this.con.getConnection();

			p = con.prepareStatement(query);

			if (parameters.length > 0 && params > 0) {
				for (int i = 0; i < params; i++) {
					p = parameters[i].handleParameter(i + 1, p);
				}
			}

			p.executeUpdate();

			updated = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// IF STH FAILS WE WILL CLOSE EVERYTHING
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (p != null) {
				try {
					p.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return updated;
	}

	/**
	 * Perform an <b>ASYNC</b> update.
	 * <p>
	 * <b>NOTE:</b> Don't call this method unless the query is so complex that
	 * the API cannot handle it (e.g. columns needs to be built at runtime).
	 * <p>
	 * Use {@link #runAsyncQuery(QueryId, ISQLParameter[])}.
	 * <p>
	 * <b>IMPORTANT:</b> Call {@link #closeStatement(ResultSet)} after ResultSet is used.
	 * </p>
	 */
	
	public ResultSet runAsyncRawQuery(String query) {
		Connection con;
		ResultSet res = null;

		PreparedStatement p;

		try {
			con = this.con.getConnection();
			p = con.prepareStatement(query);

			res = p.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	/**
	 * Performs an <b>ASYNC</b> query and returns it's content.
	 * <p>
	 * <b>See also: </b>SQLQueries.registerQuery()
	 * </p>
	 *
	 * @param queryId    : Valid registered queryId.
	 * @param parameters : List of parameters (one for each char ?).
	 */
	
	public ResultSet runAsyncQuery(QueryId queryId, ISQLParameter... parameters) {
		Connection con;
		ResultSet res = null;

		SQLQueryData queryData = SQLQueries.getDataForQuery(queryId);
		String query = queryData.getQuery();
		int params = queryData.getParams();

		if (parameters.length < params) {
			UCode2017.LOGGER.warning("Couldn't execute query.");
			UCode2017.LOGGER.warning("Query: " + query);
			UCode2017.LOGGER.warning("Parameters expected: " + params);
			UCode2017.LOGGER.warning("Parameters got: " + parameters.length);
		} else {
			PreparedStatement p;

			try {
				con = this.con.getConnection();

				p = con.prepareStatement(query);

				if (parameters.length > 0 && params > 0) {
					for (int i = 0; i < params; i++) {
						p = parameters[i].handleParameter(i + 1, p);
					}
				}

				res = p.executeQuery();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	/**
	 * Counts the rows of [<i>table</i>].
	 *
	 * @param where : String = DO NOT INCLUDE 'WHERE', ONLY CONDITIONS.
	 * @return The number of rows of that table, with that where clauses.
	 */
	
	public int runAsyncNumRows(String table, String where) {
		return runAsyncNumRows(table, "", "", where);
	}

	/**
	 * Counts the rows of [<i>table</i>].
	 *
	 * @param innerJoinTable : Table to join with [<i>table</i>].
	 * @param innerJoinOn    : Column to join both tables.
	 * @param where          : String = DO NOT INCLUDE 'WHERE', ONLY CONDITIONS.
	 * @return The number of rows of [<i>table</i>]
	 */
	
	public int runAsyncNumRows(String table, String innerJoinTable, String innerJoinOn, String where) {
		Connection con;
		int rows = 0;
		PreparedStatement p;

		String query = "SELECT count(*) FROM `" + table + "`";

		if (innerJoinTable != null && !innerJoinTable.isEmpty() && innerJoinOn != null && !innerJoinOn.isEmpty()) {
			query += " INNER JOIN `" + innerJoinTable + "` ON " + innerJoinOn;
		}

		if (where != null && !where.isEmpty()) {
			query += " WHERE " + where;
		}

		ResultSet set = null;
		try {
			con = this.con.getConnection();

			p = con.prepareStatement(query);

			set = p.executeQuery();
			set.first();
			rows = set.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// IF STH FAILS WE WILL CLOSE EVERYTHING
			if (set != null) {
				closeStatement(set);
			}
		}

		return rows;
	}

}
