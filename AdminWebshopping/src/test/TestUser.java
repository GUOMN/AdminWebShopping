package test;

import java.util.List;

import net.sf.json.JSONArray;

import com.dao.AdminDAO;
import com.dao.UserDAO;
import com.model.User;

public class TestUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		User test = new User();
//		test.setAge("12");
//		test.setEmail("asdf@test.com");
//		test.setName("testname");
//		test.setRemark("test");
//		test.setSex("male");
//		test.setTel("123456789123");
//		test.setUserID(1);
//		int flag = 0;
//		try {
//			flag = UserDAO.modifyUserINFO2(test);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			flag= UserDAO.logIn("郭梦男", "1234");
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
		AdminDAO adminDAO=new AdminDAO();
		List<User> list = adminDAO.listUsers();
		JSONArray j=JSONArray.fromObject(list);
		System.out.println(j.toString());

	}

}
