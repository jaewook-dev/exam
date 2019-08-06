package kr.or.ksmart.ksmart_layout1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.ksmart.ksmart_layout1.vo.Goods;

@Mapper
public interface GoodsMapper {

	public List<Goods> goodsList();
	
	public int addGoods(Goods goods);
	
	public List<Goods> searchGoods(String sk, String sv, String firstPrice, String lastPrice);
	
	public int goodsCode();
	
	public Goods getGoodsList(String goodsCode);
	
	public int modifyGoods(Goods goods);
	
	public Goods removeCheck(String memberId, String memberPw, String goodsCode);
	
	public int removeGoods(String goodsCode);
}
