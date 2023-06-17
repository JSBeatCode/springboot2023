package com.example.demo.utility.type.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.google.gson.Gson;

public class MapTypeHandler extends BaseTypeHandler<Map<String, Object>> {

    private static final Gson gson = new Gson();
    
    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return gson.fromJson(rs.getString(columnName), HashMap.class);
    }
    
    @Override
    public Map<String, Object> getNullableResult(ResultSet rs, int columnName) throws SQLException {
        return gson.fromJson(rs.getString(columnName), HashMap.class);
    }
    
    @Override
    public Map<String, Object> getNullableResult(CallableStatement stmt, int columnIndex) throws SQLException {
        return gson.fromJson(stmt.getString(columnIndex), HashMap.class);
    }

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		// stmt.setString(paramIndex, (paramValue != null ? paramValue.toString() : null ));
		ps.setString(i, gson.toJson(parameter));
	}
    
}