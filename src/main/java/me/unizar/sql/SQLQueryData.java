package me.unizar.sql;

public class SQLQueryData {
	
	private String query;
	private int params;
	
	/**
	 * @param params : int = Number of ? inside query.
	 */
	public SQLQueryData(String query, int params){
		this.query = query;
		this.params = params;
	}

	public String getQuery() {
		return query;
	}

	public int getParams() {
		return params;
	}

}
