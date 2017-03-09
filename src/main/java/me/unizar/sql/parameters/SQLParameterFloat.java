package me.unizar.sql.parameters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLParameterFloat implements ISQLParameter{
	
	private float param = 0;
	
	public SQLParameterFloat(float param) {
		this.param = param;
	}
	
	public float getParam(){
		return param;
	}
	
	@Override
	public PreparedStatement handleParameter(int index, PreparedStatement p) {
		try {
			p.setFloat(index, getParam());
		} catch (SQLException e) {
			System.out.println("Couldn't set param " + index + " with value " + getParam());
			e.printStackTrace();
		}
		return p;
	}

}
