package test;

import java.sql.SQLException;
import java.util.List;

import com.dao.GoodsDAO;
import com.model.Goods;

public class TestList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GoodsDAO g1 = new GoodsDAO();
		// List<?> list=g1.queryGoods_price(10,600);
		// Object[] g=list.toArray();
		// System.out.println(g.getClass());
		// System.out.println(g[0].getClass());

		// Iterator it = list.iterator();
		// System.out.println(it);
		// while(it.hasNext()){
		// Goods goods=(Goods) it.next();
		// System.out.println(goods);
		// System.out.println(goods.getBrand());
		// }

		// Goods goods=(Goods) list.get(0);
		// System.out.println(goods);
		// System.out.println(goods.getBrand());

		try {
			// 注意list.get()的实参从0开始
			@SuppressWarnings("unchecked")
			List<List<Goods>> allGoodsList = g1.getPGoods("1", "1", "1");
			System.out.println(allGoodsList);
			List<Goods> list = allGoodsList.get(0);
			System.out.println(list);
			Goods good = list.get(0);
			System.out.println(good);
			System.out.println(good.getClass());
			System.out.println(good.getCategoryID());// ?

			// Iterator it = allGoodsList.iterator();
			// while(it.hasNext()){
			// Goods goods=(Goods) it.next();
			// System.out.println(goods);
			// System.out.println(goods.getBrand());
			// }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
