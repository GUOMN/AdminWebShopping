package test;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dao.GoodsDAO;
import com.model.Goods;

public class JsonTOmodel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// [{"attachment":"","categoryID":0,"color":"","del":"","goodID":1,"handinventory":0,"image":"莲花戒指.jpg","introduct":"","material":"","name":"莲花戒指","number":"","priceCommon":0,"priceSpecial":0,"sex":""},{"attachment":"","categoryID":0,"color":"","del":"","goodID":2,"handinventory":0,"image":"海马挂饰.jpg","introduct":"","material":"","name":"海马挂饰","number":"","priceCommon":0,"priceSpecial":0,"sex":""},{"attachment":"","categoryID":0,"color":"","del":"","goodID":3,"handinventory":0,"image":"海螺之恋吊坠.jpg","introduct":"","material":"","name":"海螺之恋吊坠","number":"","priceCommon":0,"priceSpecial":0,"sex":""},{"attachment":"","categoryID":0,"color":"","del":"","goodID":4,"handinventory":0,"image":"百年好合手镯.jpg","introduct":"","material":"","name":"百年好合手镯","number":"","priceCommon":0,"priceSpecial":0,"sex":""},{"attachment":"","categoryID":0,"color":"","del":"","goodID":5,"handinventory":0,"image":"百年好合戒指.jpg","introduct":"","material":"","name":"百年好合戒指","number":"","priceCommon":0,"priceSpecial":0,"sex":""},{"attachment":"","categoryID":0,"color":"","del":"","goodID":6,"handinventory":0,"image":"百年好合腕饰.jpg","introduct":"","material":"","name":"百年好合腕饰","number":"","priceCommon":0,"priceSpecial":0,"sex":""}]
		// JSONArray
		// testJSONArray=JSONArray.fromObject("[{'attachment':'','categoryID':0,'color':'','del':'','goodID':1,'handinventory':0,'image':'莲花戒指.jpg','introduct':'','material':'','name':'莲花戒指','number':'','priceCommon':0,'priceSpecial':0,'sex':''},"
		// +
		// "{'categoryID':0,'color':'','del':'','goodID':2,'handinventory':0,'image':'海马挂饰.jpg','introduct':'','material':'','name':'海马挂饰','number':'','priceCommon':0,'priceSpecial':0,'sex':''},"
		// +
		// "{'categoryID':0,'color':'','del':'','goodID':3,'handinventory':0,'image':'海螺之恋吊坠.jpg','introduct':'','material':'','name':'海螺之恋吊坠','number':'','priceCommon':0,'priceSpecial':0,'sex':''},"
		// +
		// "{'categoryID':0,'color':'','del':'','goodID':4,'handinventory':0,'image':'百年好合手镯.jpg','introduct':'','material':'','name':'百年好合手镯','number':'','priceCommon':0,'priceSpecial':0,'sex':''},"
		// +
		// "{'categoryID':0,'color':'','del':'','goodID':5,'handinventory':0,'image':'百年好合戒指.jpg','introduct':'','material':'','name':'百年好合戒指','number':'','priceCommon':0,'priceSpecial':0,'sex':''},"
		// +
		// "{'introduct':'','material':'','name':'百年好合腕饰','number':'','priceCommon':0,'priceSpecial':0,'sex':''}]");
		List<Goods> list = new GoodsDAO().queryGoods(0, 600);
		JSONArray testJSONArray = JSONArray.fromObject(list);
		// JSONArray testJSONArray=JSONArray.fromObject();
		Collection<Goods> testArray = JSONArray.toCollection(testJSONArray);
		if (testArray != null && !testArray.isEmpty()) {
			Iterator<Goods> it = testArray.iterator();
			while (it.hasNext()) {
				JSONObject testJSONObj = JSONObject.fromObject(it.next());
				Goods good = (Goods) JSONObject
						.toBean(testJSONObj, Goods.class);
				System.out.println(good.getName());
			}
		}

	}
}
