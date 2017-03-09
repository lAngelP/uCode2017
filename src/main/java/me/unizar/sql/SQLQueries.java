package me.unizar.sql;


import java.util.ArrayList;
import java.util.List;

/**
 * Used for SQL queries, registering and managing
 */
public class SQLQueries{
	
	private static List<SQLQueryData> queries = new ArrayList<>();

	public static SQLQueryData getDataForQuery(QueryId id) {
		return queries.get(id.getId());
	}

	public static QueryId registerQuery(SQLQueryData data){
		queries.add(data);
		return new QueryId();
	}

	public static class QueryId {
		private static int queries = 0;
		private int id;
		
		private QueryId(){
			id = queries++;
		}
		
		private int getId() {
			return id;
		}

	}

}
