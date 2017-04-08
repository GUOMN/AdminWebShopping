package test;

import java.util.List;

import net.sf.json.JSONArray;

import com.dao.BBSreplyDAO;
import com.dao.BBSsectionDAO;
import com.dao.BBStopicDAO;
import com.model.BBSreply;
import com.model.BBStopic;



public class TestJSON {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		GoodsDAO g1 = new GoodsDAO();
////		Goods goods = new Goods();
//		List<Goods> list = g1.queryGoods("百");
////
////		 new JSONObject();
//////		 JSONObject jsonObject = JSONObject.fromObject(goods);
////		 JSONArray jaArray=JSONArray.fromObject(list);
////				 
////		 System.out.println(jaArray);
//	
//		JSONArray jsonArr = JSONArray.fromObject(list);
//		System.out.println(jsonArr);

//		String json="{\"userID\":3,\"name\":\"bbb\",\"tel\":\"465341561\",\"sex\":\"女\",\"age\":\"35\",\"email\":\"sdfzxcsfvdc@139.com\"}";
//		JSONObject jsonObject =JSONObject.fromObject(json);
//		
//		User u  = (User) JSONObject.toBean(jsonObject,User.class);
//		try {
//			boolean flag= UserDAO.modifyUserINFO(u);
//			System.out.println(flag);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Goods gs=new GoodsDAO().queryGoods(1);
//		JSONObject  jo=JSONObject.fromObject(gs);
//				System.out.println(String.valueOf(jo));
//		System.out.println(u.getName());
//		List<BBStopic> list = BBSsectionDAO.list_topics(1);
//		for (BBSreply bbSreply : list) {
//			System.out.println(bbSreply.toString());
//			JSONObject  jo=JSONObject.fromObject(bbSreply);
//			System.out.println(String.valueOf(jo));
//		}

		List<BBStopic> list =BBStopicDAO.query_topics("1", 5);
		JSONArray jsonArray = JSONArray.fromObject(list);
		System.out.println(String.valueOf(jsonArray));
		

	}

}
