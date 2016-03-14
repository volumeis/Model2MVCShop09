package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.user.UserService;

//==> 물품관리 Controller
@Controller
@RequestMapping("/product/*")
public class ProductController {

	//field
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
		
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping(value = "addProduct", method = RequestMethod.POST)
	public String addProduct(@ModelAttribute("product") Product product) throws Exception {
		
		System.out.println("/product/addProduct.do : POST");

		product.setManuDate(product.getManuDate().replace("-", ""));
		//Business Logic 
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping(value = "getProduct", method = RequestMethod.GET)
	public String getProduct(@RequestParam("prodNo") int prodNo, @RequestParam("menu") String menu, 
								Model model) throws Exception{
		
		System.out.println("/product/getProduct.do : GET");
	
		//Business Logic
		Product product = productService.getProduct(prodNo);

		// Model 과 View 연결
		model.addAttribute("product", product);
		
		if (menu.equals("manage")) {
			//menu = manage
			return "forward:/updateProductView.do?prodNo="+prodNo+"&menu=manage";
		} else if (menu.equals("ok")){
			//menu = ok
			return "forward:/product/confirmDetailProductView.jsp";
		} else {
			//menu = search
			return "forward:/product/purchaseProductView.jsp";
		} 
	}
	
	//@RequestMapping("/listProduct.do")
	@RequestMapping(value ="listProduct")
	public String listProduct(@ModelAttribute("search") Search search,
								@RequestParam("menu") String menu,
								HttpSession session,  Model model) throws Exception {
		
		System.out.println("/product/listProduct : SERVICE");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		//세션에 담긴 user정보 Get
		User user = ((User)session.getAttribute("user"));
		String uri = "";
		System.out.println(user);
		if ( user == null || user.getRole().equals("user") ){
			
			System.out.println("--uri : /product/listProductUser.jsp ");
			uri = "/product/listProductUser.jsp";
		
		} else if (user.getRole().equals("admin")) {
		
			if(menu.equals("search")) {
				System.out.println("--uri : /product/listProductAdmin.jsp ");
				uri = "/product/listProductAdmin.jsp";
			} 
			else if(menu.equals("manage")){
				System.out.println("--uri : /listSale.do ");
				uri = "/purchase/listSale.do";
			}
		}
		
		return "forward:"+ uri;
	}
	
	//@RequestMapping("/updateProduct.do")
	@RequestMapping( value="updateProduct", method=RequestMethod.GET )
	public String updateProduct(@RequestParam("prodNo")int prodNo,
			@RequestParam("prodName")String prodName,
			@RequestParam("prodDetail")String prodDetail,
			@RequestParam("price")int price,
			HttpServletRequest request ) throws Exception{
		
		System.out.println("/product/updateProduct  : GET");
		Product product = productService.getProduct(prodNo);
		//Business Logic
		product.setProdName(prodName);
		product.setProdDetail(prodDetail);
		product.setPrice(price);
		productService.updateProduct(product);
		System.out.println("변경 후 : " +product);
		
		return "redirect:/getProduct.do?prodNo="+product.getProdNo()+"&menu=ok";
	}
	//return "forward:/updateProductView.do?prodNo="+prodNo+"&menu=manage";
	
	
	//@RequestMapping("/updateProductView.do")
	@RequestMapping(value = "updateProduct",  method = RequestMethod.POST)
	public String updateProductView(HttpServletRequest request,
			Model model) throws Exception{
		System.out.println("/product/updateProduct : POST");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		//Business Logic
		Product product = productService.getProduct(prodNo);
		
		// Model 과 View 연결
		model.addAttribute("product", product);
	//	System.out.println("아아아우" + request.getAttribute("product") );
		return "forward:/product/updateProductView.jsp";
	}
}
