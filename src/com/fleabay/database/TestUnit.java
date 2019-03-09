package com.fleabay.database;

import java.util.List;

//import java.util.UUID;
//
import org.apache.ibatis.session.SqlSession;

import com.fleabay.database.data.Good;
import com.fleabay.database.mapper.GoodMapper;

public class TestUnit {
	public static void main(String[] args) {
		SqlSession sqlSession = DB.getSqlSession();
		GoodMapper mapper = sqlSession.getMapper(GoodMapper.class);
		List<Good> goodList = mapper.getGoodListByKeyword("%猫%");
//		List<String> typeList = mapper.getTypeList();
		for(int i = 0;i < goodList.size();i++) {
			System.out.println(goodList.get(i).getName());
			System.out.println(goodList.get(i).getType());
			System.out.println(goodList.get(i).getRemark());
		}
		sqlSession.close();
//		Good good = new Good();
//		good.setId("0ca175b9c0f726a831d895e269332461");
//		good.setName("Banan");
//		good.setType("fruit");
//		good.setSpec("kg");
//		good.setStock(100);
//		good.setRemark("This is a health fruit.");
//		mapper.insertGood(good);
//		sqlSession.commit();
//		sqlSession.close();
//		System.out.println();
	}
}
