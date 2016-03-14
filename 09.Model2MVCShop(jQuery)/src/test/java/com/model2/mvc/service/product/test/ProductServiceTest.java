package com.model2.mvc.service.product.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class ProductServiceTest {

	// ==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	
	 @Test
	public void testAddProduct() throws Exception {

		Product product = new Product();
		product.setFileName("123");
		product.setManuDate("20160307");
		product.setPrice(1234);
		product.setProdDetail("56783q");
		product.setProdName("���ĥ1");
		product.setProTranCode("1");
		
		productService.addProduct(product);

		product = productService.getProduct(10144);

		// ==> console Ȯ��
		// System.out.println(user);

		// ==> API Ȯ��
		System.out.println(product);
	}
	
	@Test
	public void testGetProduct() throws Exception {

		Product product = new Product();
		// ==> �ʿ��ϴٸ�...
		// user.setUserId("testUserId");
		// user.setUserName("testUserName");
		// user.setPassword("testPasswd");
		// user.setSsn("1111112222222");
		// user.setPhone("111-2222-3333");
		// user.setAddr("��⵵");
		// user.setEmail("test@test.com");

		product = productService.getProduct(10080);

		// ==> console Ȯ��
		// System.out.println(user);

		// ==> API Ȯ��
		Assert.assertEquals(10080, product.getProdNo());
	}
	/*
	// @Test
	public void testUpdateUser() throws Exception {

		User user = userService.getUser("testUserId");
		Assert.assertNotNull(user);

		Assert.assertEquals("testUserName", user.getUserName());
		Assert.assertEquals("111-2222-3333", user.getPhone());
		Assert.assertEquals("��⵵", user.getAddr());
		Assert.assertEquals("test@test.com", user.getEmail());

		user.setUserName("change");
		user.setPhone("777-7777-7777");
		user.setAddr("change");
		user.setEmail("change@change.com");

		userService.updateUser(user);

		user = userService.getUser("testUserId");
		Assert.assertNotNull(user);

		// ==> console Ȯ��
		// System.out.println(user);

		// ==> API Ȯ��
		Assert.assertEquals("change", user.getUserName());
		Assert.assertEquals("777-7777-7777", user.getPhone());
		Assert.assertEquals("change", user.getAddr());
		Assert.assertEquals("change@change.com", user.getEmail());
	}
	*/
	/*
	// @Test
	public void testCheckDuplication() throws Exception {

		// ==> �ʿ��ϴٸ�...
		// User user = new User();
		// user.setUserId("testUserId");
		// user.setUserName("testUserName");
		// user.setPassword("testPasswd");
		// user.setSsn("1111112222222");
		// user.setPhone("111-2222-3333");
		// user.setAddr("��⵵");
		// user.setEmail("test@test.com");
		//
		// userService.addUser(user);

		// ==> console Ȯ��
		// System.out.println(userService.checkDuplication("testUserId"));
		// System.out.println(userService.checkDuplication("testUserId"+System.currentTimeMillis())
		// );

		// ==> API Ȯ��
		Assert.assertFalse(userService.checkDuplication("testUserId"));
		Assert.assertTrue(userService.checkDuplication("testUserId" + System.currentTimeMillis()));

	}
	*/
	/*
	// ==> �ּ��� Ǯ�� �����ϸ�....
	// @Test
	public void testGetUserListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = userService.getUserList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console Ȯ��
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = userService.getUserList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console Ȯ��
		// System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	*/
	/*
	// @Test
	public void testGetUserListByUserId() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("admin");
		Map<String, Object> map = userService.getUserList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(1, list.size());

		// ==> console Ȯ��
		// System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("0");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = userService.getUserList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console Ȯ��
		// System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	*/
	/*
	@Test
	public void testGetUserListByUserName() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
		search.setSearchKeyword("SCOTT");
		Map<String, Object> map = userService.getUserList(search);

		List<Object> list = (List<Object>) map.get("list");
		Assert.assertEquals(3, list.size());

		// ==> console Ȯ��
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setSearchCondition("1");
		search.setSearchKeyword("" + System.currentTimeMillis());
		map = userService.getUserList(search);

		list = (List<Object>) map.get("list");
		Assert.assertEquals(0, list.size());

		// ==> console Ȯ��
		System.out.println(list);

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);
	}
	*/
}