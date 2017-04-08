package test;

import java.io.IOException;
import java.sql.SQLException;

import com.dao.UserDAO;

public class TestIcon {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			boolean b= UserDAO.readIcon("1");
			System.out.println(b);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
