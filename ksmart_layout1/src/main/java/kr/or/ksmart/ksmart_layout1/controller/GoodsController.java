package kr.or.ksmart.ksmart_layout1.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ksmart.ksmart_layout1.mapper.GoodsMapper;
import kr.or.ksmart.ksmart_layout1.mapper.MemberMapper;
import kr.or.ksmart.ksmart_layout1.service.GoodsService;
import kr.or.ksmart.ksmart_layout1.vo.Goods;

@Controller
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@GetMapping("/goodsList")
	public String goodsList(Model model) {
		List<Goods> list = goodsService.goodsList();
		model.addAttribute("goodsList", list);
		return "goods/glist/goodsList";
	}
	
	@PostMapping("/goodsList")
	public String goodsList(@RequestParam(value="sk") String sk, @RequestParam(value="sv") String sv, @RequestParam(value="firstPrice") String firstPrice, @RequestParam(value="lastPrice") String lastPrice, Model model) {
		List<Goods> list = goodsService.searchGoods(sk, sv, firstPrice, lastPrice);
		model.addAttribute("goodsList", list);
		return "goods/glist/goodsList";
	}
	
	@GetMapping("/addGoods")
	public String addGoods() {
		return "goods/ginsert/addGoods";
	}
	
	@PostMapping("/addGoods")
	public String addGoods(Goods goods, HttpSession session) {
		goodsService.addGoods(goods, session);
		return "redirect:/goodsList";
	}
	
	@GetMapping("/modifyGoods")
	public String modifyGoods(@RequestParam(value="goodsCode") String goodsCode, Model model) {
		// System.out.println(goodsCode + " <-- goodsCode modifyGoods GoodsController.java");
		model.addAttribute("goodsList", goodsService.getGoodsList(goodsCode));
		return "goods/gupdate/modifyGoods";
	}
	
	@PostMapping("/modifyGoods")
	public String modifyGoods(Goods goods) {
		// System.out.println(goods + " <-- goods modifyGoods GoodsController.java");
		goodsService.modifyGoods(goods);
		return "redirect:/goodsList";
	}
	
	@GetMapping("/removeGoods")
	public String removeGoods(@RequestParam(value="goodsCode") String goodsCode, Model model, HttpSession session) {
		String memberId = goodsService.getGoodsList(goodsCode).getMemberId();
		model.addAttribute("goodsCode", goodsCode);
		model.addAttribute("memberId", memberId);
		return "goods/gdelete/delGoods";
	}
	
	@PostMapping("/removeGoods")
	public String removeGoods(@RequestParam(value="goodsCode") String goodsCode, Goods goods, Model model) {
		Map<String, Object> map = goodsService.removeCheck(goods);
		String result = (String)map.get("result");
		if(result.equals("삭제가능")) {
			goodsService.removeGoods(goodsCode);
			return "redirect:/goodsList";
		} else {
			model.addAttribute("alert", result);
			model.addAttribute("goodsCode", goodsCode);
			model.addAttribute("memberId", goods.getMemberId());
			return "goods/gdelete/delGoods";
		}	
	}
	
}
