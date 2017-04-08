package test;

import java.util.ArrayList;
import java.util.List;

import com.dao.OrderDAO;
import com.model.Order;
import com.model.Orderitem;

public class TestOrder {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Order order = new Order(0, 0, 0, "test", 0);
		Orderitem orderitem = new Orderitem(0, 0, 0, "test");
		List<Orderitem> list = new ArrayList<Orderitem>();
		list.add(orderitem);
		list.add(orderitem);
		OrderDAO dao = new OrderDAO();
		boolean flag = OrderDAO.newOrder(order, list);
		System.out.println("test OrderDAO__addorder:" + flag);

	}

}
