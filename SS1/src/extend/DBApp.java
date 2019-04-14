package extend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBApp extends wk3.DBApp {
	
	public DBApp(String driver) throws InternalError {
		super(driver);
	}

	public boolean checkExist(String sql) throws SQLException {
		Statement s = null;
		s = conn.createStatement();
		ResultSet rs = s.executeQuery(sql);
		if (!rs.next()) {
			return false;
		} else {
			return true;
		}
	}

	public String fetchPre(String sql) throws SQLException {
		Statement s = null;
		String pre = "";
		try {
			s = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rs = s.executeQuery(sql);
		if (rs.next()) {
			pre = rs.getString("prerequisites");
		}
		return pre;

	}

}
