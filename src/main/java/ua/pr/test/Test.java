package ua.pr.test;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.xdo.template.FOProcessor;
import ua.pr.xslt.DataXSLT;
import ua.pr.xslt.ReportXSLT;

public class Test {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			String strCon = "jdbc:sqlserver://46.201.240.87:1433;"
						  + "user=ukreni;password=pfdpbgdq;databaseName=KPVP";
			conn = DriverManager.getConnection(strCon);
			
			System.out.println("----------------------" + conn);
			
			DataXSLT dX = new DataXSLT(conn);
			ByteArrayOutputStream baData = dX.getData("select * from lstSubstation where idSubstation > 0", 
					null, null, null);

			ReportXSLT rX = new ReportXSLT();
			rX.setXdoConfPath("d:/TempNet/xdo.xml");
			
			byte format = FOProcessor.FORMAT_IMAGE_GIF;
			String sh = "d:/TempNet/reps/foTemplates/shSubstations.xml";
			System.out.println(sh + "_" + format);
			rX.getReport(baData, sh, format, "d:/res.gif");

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
