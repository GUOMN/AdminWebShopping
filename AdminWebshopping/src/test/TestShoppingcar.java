package test;

import java.sql.SQLException;

import com.dao.ShoppingCarDAO;

public class TestShoppingcar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ShoppingCarDAO shoppingCar = new ShoppingCarDAO();

		// ShoppingCar temp=new ShoppingCar();
		// temp.setGoodID(1);
		// temp.setUserID(6);
		// temp.setAmount(1);
		// temp.setColor("red");
		// temp.setMaterial("PVC");
		// // temp.setState(state);
		// temp.setAddedTime(new Date());
		//
		// //

		// try {
		// boolean back=shoppingCar.addGood(temp);
		// } catch (SQLException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// //
		// try {
		// List list = shoppingCar.querryShoppingCar(1);
		// JSONObject jsonObject = JSONObject.fromObject(temp);
		// //Shoppingcar中使用了DATE对象，报错
		// JSONArray jsonArray= JSONArray.fromObject(list);
		// System.out.println(jsonArray.toString());
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		try {
			System.out.println(shoppingCar.modifyShoppingCar(2, 4));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
