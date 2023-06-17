package com.example.demo.utility.type.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JsonObjectTypeHandler extends BaseTypeHandler<JsonObject> {

    private static final Gson gson = new Gson();
    
    @Override
    public JsonObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return gson.fromJson(rs.getString(columnName), JsonObject.class);
    }
    
    @Override
    public JsonObject getNullableResult(ResultSet rs, int columnName) throws SQLException {
        return gson.fromJson(rs.getString(columnName), JsonObject.class);
    }
    
    @Override
    public JsonObject getNullableResult(CallableStatement stmt, int columnIndex) throws SQLException {
        return gson.fromJson(stmt.getString(columnIndex), JsonObject.class);
    }
    

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, JsonObject parameter, JdbcType jdbcType)
			throws SQLException {
		// TODO Auto-generated method stub
		ps.setString(i, (parameter != null ? parameter.toString() : null ));

	}

    
}