package me.unizar.sql.parameters;

import java.sql.PreparedStatement;

public interface ISQLParameter {
	PreparedStatement handleParameter(int index, PreparedStatement p);
}
