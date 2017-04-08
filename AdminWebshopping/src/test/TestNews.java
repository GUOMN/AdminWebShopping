package test;

import java.util.List;

import com.dao.NewsDAO;
import com.model.News;

public class TestNews {

	public static void main(String[] args) throws Exception {

		// ------------ 测试按照ID查询------------------
		// NewsDAO n1=new NewsDAO();
		//
		// List<News> list=n1.queryNews(2);
		// System.out.println(list);

		// -----------------测试按照筛选条件查询------------------------------
		// NewsDAO n1=new NewsDAO();
		//
		// List<News> result=n1.query("aa");
		// for(int i=0;i<result.size();i++){
		// System.out.println(result.get(i).toString());
		// }

		// -----------------测试模糊查询------------------------------
		// NewsDAO n1=new NewsDAO();
		// List<News> result=n1.query1("a");
		// for(int i=0;i<result.size();i++){
		// System.out.println(result.get(i).toString());
		// }

		// -----------------测试优化后的查询------------------------------
		// NewsDAO n1=new NewsDAO();
		// List<Map<String,Object>> params=new ArrayList<Map<String,Object>>();
		// Map<String,Object> param=new HashMap<String,Object>();
		//
		//

		// ----------准确查询-----------
		// // param.put("name", "title");
		// // param.put("rela", "=");
		// // param.put("value", "'aa'");
		//
		//

		// ------- 模糊查询-----------
		// param.put("name", "title");
		// param.put("rela", "like");
		// param.put("value", "'a'");
		// params.add(param);
		// List<News> result=n1.query(params);
		//
		// for(int i=0;i<result.size();i++){
		// System.out.println(result.get(i).toString());
		// }

		// ------------ 测试按照SortId查询------------------
		NewsDAO n1 = new NewsDAO();

		News ns = n1.queryNews(2);
		System.out.println(ns);

	}
}
