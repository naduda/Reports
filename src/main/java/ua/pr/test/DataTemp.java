package ua.pr.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import ua.pr.xslt.DataXSLT;

public class DataTemp {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String strCon = "jdbc:sqlserver://46.201.240.87:1433;"
						  + "user=ukreni;password=pfdpbgdq;databaseName=KPVP";
			conn = DriverManager.getConnection(strCon);
			
			System.out.println("----------------------" + conn);
			
			DataXSLT dX = new DataXSLT(conn);
			dX.getData(null, "d:/GIT/Reports/reps/DataTemplates/dtInfo.xml", null, "d:/1.xml");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
