package kr.or.ksmart.ksmart_layout1.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.ksmart.ksmart_layout1.mapper.GoodsMapper;
import kr.or.ksmart.ksmart_layout1.vo.Goods;

@Service
@Transactional
public class GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;
	
	public List<Goods> goodsList(){
		return goodsMapper.goodsList();	
	}
	
	public int addGoods(Goods goods, HttpSession session) {
		String memberId = (String)session.getAttribute("SID");
		goods.setMemberId(memberId);
		int codeNum = goodsMapper.goodsCode()+1;
		String g_code = "goods_" + codeNum;
		goods.setGoodsCode(g_code);
		// System.out.println(goods + " <-- goods addGoods() GoodsService.java");
		return goodsMapper.addGoods(goods);	
	}
	
	public List<Goods> searchGoods(String sk, String sv, String firstPrice, String lastPrice) {
		// System.out.println(sk + " <-- sk GoodsService.java");
		// System.out.println(sv + " <-- sv GoodsService.java");
		return goodsMapper.searchGoods(sk, sv, firstPrice, lastPrice);
	}
	
	public Goods getGoodsList(String goodsCode) {
		return goodsMapper.getGoodsList(goodsCode);
	}
	
	public int modifyGoods(Goods goods) {
		return goodsMapper.modifyGoods(goods);
	}
	
	public Map<String, Object> removeCheck(Goods goods){
		String result = "";
		Map<String, Object> map = new HashMap<String, Object>();
		
		Goods removeCheck = goodsMapper.removeCheck(goods.getMemberId(), goods.getMemberPw(), goods.getGoodsCode());
		
		if(removeCheck == null) {
			result = "비밀번호가 일치하지 않습니다";
			// System.out.println("비번 불일치");
		} else {
			result = "삭제가능";
			// System.out.println("비번 일치");
			
			map.put("removeCheck", removeCheck);
		}
		map.put("result", result);
		return map;
	}
	
	public int removeGoods(String goodsCode) {
		return goodsMapper.removeGoods(goodsCode);
	}
	
}
