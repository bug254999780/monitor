package com.telecom.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JDBCUtil {

	/*
	 * 将ResultSet 转换为Map List集合
	 */
	public static List<Map<String, Object>> toListMap(ResultSet rs) throws SQLException {  
        List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();  
        ResultSetMetaData rsmd = rs.getMetaData();  
        int count = rsmd.getColumnCount();  
        while(rs.next()){
        	Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= count; i++) {  
				String key = rsmd.getColumnLabel(i);
				Object value = rs.getObject(i); 
				if(value == null)
					value = "";
				map.put(key, value);  
			}
			resultMap.add(map);
		}
        return resultMap;  
	}
	
	/*
	 * 根据表名和参数生成insert sql语句
	 */
	public static String toInsertSQL(String tableName, Map<String, Object> map){
		StringBuffer sql = new StringBuffer();
		StringBuffer columnSql = new StringBuffer();
		StringBuffer valueSql = new StringBuffer();
		
		for (Entry<String, Object> entry : map.entrySet()) {
			String column = entry.getKey();
			Object value = entry.getValue();
			
			if(!"".equals(columnSql.toString())){
				columnSql.append(",");
			}
			columnSql.append(column);
			
			if(!"".equals(valueSql.toString())){
				valueSql.append(",");
			}
			//valueSql.append("'"+value+"'");
			valueSql.append("?");
		}
		sql.append(" insert into "+tableName+"("+columnSql.toString()+") values("+valueSql.toString()+")");
		return sql.toString();
	}
	
	/*
	 * 根据表名和参数生成update sql语句
	 */
	public static String toUpdateSQL(String tableName, Map<String, Object> map){
		StringBuffer sql = new StringBuffer();
		StringBuffer columnSql = new StringBuffer();
		
		for (Entry<String, Object> entry : map.entrySet()) {
			String column = entry.getKey();
			
			if(!"".equals(columnSql.toString())){
				columnSql.append(",");
			}
			columnSql.append(column+"=?");
			
		}
		sql.append(" update set "+columnSql.toString()+" from "+tableName+" where 1 = 1 ");
		return sql.toString();
	}
	
	/*
	 * 将sql语句中的占位符填充值
	 */
	public static void setSQLParamerter(PreparedStatement pstmt, Map<String, Object> map) throws SQLException{
		int index = 1;
		for (Entry<String, Object> entry : map.entrySet()) {
			Object object = entry.getValue();
			String paramType = object.getClass().toString().split(" ")[1].toString();
			switch (paramType) {
				case "java.lang.String":
					pstmt.setString(index, object.toString());
		            break;
				case "java.lang.Date":
					pstmt.setTimestamp(index, new Timestamp(((Date)object).getTime()));
		            break;
				case "java.lang.Integer":
					pstmt.setInt(index, Integer.parseInt(object.toString()));
					break;
				case "java.lang.Boolean":
					pstmt.setBoolean(index, (Boolean)object);
					break;
				case "java.lang.Long":
					//Long.valueOf(object.toString());
					pstmt.setLong(index, Long.valueOf(object.toString()));
					break;
	        }
			index++;
		}
	}
	
	/*
	 * 关闭连接
	 */
	public static void close(Connection conn, PreparedStatement pst,
			ResultSet rs) throws Exception {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new Exception(" Connection、PreparedStatement、ResultSet 关闭出错 ");
		}
	}
}
