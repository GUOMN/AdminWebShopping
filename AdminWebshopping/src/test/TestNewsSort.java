package test;

import com.dao.NewsSortDAO;

public class TestNewsSort {

	public static void main(String[] args) {

		// ------------- 测试查询全部---------------
		// NewsSortDAO n1=new NewsSortDAO();
		// List< NewsSort> list=n1.queryALL();
		// System.out.println(list);

		// ---------------- 测试通过分类名查询id------------------------
		NewsSortDAO n1 = new NewsSortDAO();
		int n = n1.querrySortId("'默认3'");
		System.out.println(n);

	}

}
