package com.model2.mvc.web.purchase;

import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

//==> 구매관 Controller
@Controller
@RequestMapping("purchase/*")
public class PurchaseController {

	//field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	PurchaseService purchaseService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;
		
	
	public PurchaseController() {
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
	
	//@RequestMapping("/addPurchaseView.do")
	@RequestMapping(value = "addPurchase", method = RequestMethod.GET)
	public ModelAndView addPurchase(@RequestParam("prod_no") int prodNo) throws Exception{
		
		System.out.println("/purchase/addPurchase : GET");
	
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchaseView.jsp");
		
		modelAndView.addObject("product", productService.getProduct(prodNo));
			
		return modelAndView; 
	}
	
	//@RequestMapping("/addPurchase.do")
	@RequestMapping(value = "addPurchase", method = RequestMethod.POST)
	public ModelAndView addPurchaseView(@ModelAttribute("purchase") Purchase purchase, 
										@RequestParam("prodNo") int prodNo,
										@ModelAttribute("product") Product product,
										HttpSession session) throws Exception{
		
		System.out.println("/purchase/addPurchase : POST");
		
		
		purchase.setBuyer((User)session.getAttribute("user"));
		purchase.setOrderDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		purchase.setPurchaseProd(product);
		purchase.setTranCode("1");
		
		purchaseService.addPurchase(purchase);		
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchase.jsp");
		
		return modelAndView;
	}
	
	//@RequestMapping("/getPurchase.do")
	@RequestMapping(value = "getPurchase", method = RequestMethod.GET)
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo) throws Exception{
		
		System.out.println("/addPurchase : GET");
		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/getPurchase.jsp");
		
		modelAndView.addObject("purchase",purchaseService.getPurchaseByTranNo(tranNo));

		return modelAndView;
	}
	
	//@RequestMapping("/listPurchase.do")
	@RequestMapping(value = "listPurchase")
	public ModelAndView listPurchase( @ModelAttribute("search") Search search ,
									HttpSession session, 
									Model model )throws Exception{
		
		System.out.println("/listPurchase : SERVICE");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		// Business logic 수행
		Map<String , Object> map= purchaseService.getPurchaseList( search , ((User)session.getAttribute("user")).getUserId() );
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/listPurchase.jsp");
		
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
				
		return modelAndView;
	}
	
	//@RequestMapping("/listSale.do")
	@RequestMapping(value = "listSale")
	public ModelAndView listSale(HttpServletRequest request) throws Exception{

		System.out.println("/listSale");
		
		
		Search search = (Search)request.getAttribute("search");
//		if(search.getCurrentPage() ==0 ){
//			search.setCurrentPage(1);
//		}
//		search.setPageSize(pageSize);
//		
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/listSale.jsp");
		
		// Business logic 수행
		Map<String , Object> map = purchaseService.getSaleList(search);
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		
		// Model 과 View 연결
		System.out.println("ㅎㅎㅎ\n " + map);
		
		modelAndView.addObject("list", map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		//modelAndView.addObject("search", search);
		
		
		return modelAndView;
	}
	
	//@RequestMapping("/updatePurchase.do")
	@RequestMapping(value = "updatePurchase")
	public ModelAndView updateProduct(@RequestParam("tranNo") int tranNo,
								@ModelAttribute("purchase") Purchase purchase ) throws Exception{
		
		System.out.println("/purchase/updatePurchase : SERVICE");
		
		System.out.println("변경전 : " + purchase);
		//Business Logic
		purchaseService.updatePurcahse(purchase);
		System.out.println("변경 후 : " +purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/updatePurchase.jsp");
		modelAndView.addObject("purchase",purchase);
		
		return modelAndView;
	}

	//@RequestMapping("/updatePurchaseView.do")
	@RequestMapping(value = "updatePurchase", method = RequestMethod.GET)
	public ModelAndView updateProductView( @RequestParam("tranNo") int tranNo, Model model) throws Exception{
		
		System.out.println("/purchase/updatePurchaseView : GET");
		
		//Business Logic
		Purchase purchase = purchaseService.getPurchaseByTranNo(tranNo);

		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("forward:/purchase/updatePurchaseView.jsp");
		modelAndView.addObject("purchase",purchase);
		
		return modelAndView;
	}
	
	//@RequestMapping("/updateTranCode.do")
	@RequestMapping(value = "updateTranCode", method= RequestMethod.GET)
	public ModelAndView updateTranCode(@RequestParam("tranCode") String tranCode,
							@RequestParam("tranNo") int tranNo) throws Exception{
		
		System.out.println("/updateTranCode.do");
		Purchase purchase =  purchaseService.getPurchaseByTranNo(tranNo);
		purchase.setTranCode(tranCode);
		purchaseService.updateTranCode(purchase);
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("/listPurchase.do");
				
		return modelAndView;
	}
	
	//@RequestMapping("/updateTranCodeByProd.do")
	@RequestMapping(value = "updateTranCodeByProd", method = RequestMethod.GET)
	public ModelAndView updateTranCodeByProd(@RequestParam("tranCode") String tranCode,
									@RequestParam("prodNo") int prodNo) throws Exception{
		
		System.out.println("/updateTranCodeByProd.do");
		
		purchaseService.updateTranCode(purchaseService.getPurchaseByProdNo(prodNo));
		
		// Model 과 View 연결
		ModelAndView modelAndView = new ModelAndView("forward:/listProduct.do?menu=manage");
				
		return modelAndView;
	}
}
			




















