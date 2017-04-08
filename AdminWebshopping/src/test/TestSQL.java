package test;

import com.dao.DAO;
import com.mysql.jdbc.Connection;

public class TestSQL {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		 try {
		 Connection con=(Connection) DAO.getCon();
		 System.out.println("(测试类)数据库连接成功!");
		 con.close();
		 } catch (Exception e) {
		 // TODO: handle exception
		 }

		// GoodsDAO g1=new GoodsDAO();
		// Iterator<Goods> go1=g1.queryGoods(9, "").iterator();
		// System.out.println(go1.next());

		/* 从对象的list中get对象并get成员属性 */
		// List list=g1.queryGoods(10,600);
		// Iterator it = list.iterator();
		// System.out.println(it);
		// while(it.hasNext()){
		// Goods goods=(Goods) it.next();
		// System.out.println(goods);
		// System.out.println(goods.getBrand());
		// }
		//
		// Goods goods=(Goods) list.get(0);
		// System.out.println(goods);
		// System.out.println(goods.getBrand());

		// System.out.println(g1.queryGoods(9, ""));
		// OrderDAO o1=new OrderDAO();
		// System.out.println(o1.Salesnum(1));
		// Goods goods=g1.queryGoods("ja").get(1);
		// System.out.println(g1.queryGoods("ja").get(1).getName());
		// List<Goods> list=new ArrayList<Goods>();
		// list=g1.queryGoods2("0", 2, 0, 0);
		// JSONObject jsonObject = JSONObject.fromObject(new Goods());
		// JSONArray jsonArray= JSONArray.fromObject(list);
		// System.out.println(jsonArray.toString());
		// try {
		// // System.out.println(UserDAO.signUp("张三", "1234", "123@139.com"));
		// System.out.println(UserDAO.logIn("张三","1234"));
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// CategoryDAO a=new CategoryDAO();
		// System.out.println(a.queryALL());

		//
	}

}
