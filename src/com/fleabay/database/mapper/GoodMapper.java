package com.fleabay.database.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fleabay.database.data.Good;

public interface GoodMapper {
	@Insert("INSERT INTO good_table"
		  + " (id, name, type, spec, remark, stock, price)"
		  + " values(#{id}, #{name}, #{type}, #{spec}, #{remark},  #{stock}, #{price})")
	public int insertGood(Good good);
	
	@Select("SELECT * FROM good_table")
	public List<Good> getGoodList();
	
	@Select("SELECT DISTINCT type FROM good_table")
	public List<String> getTypeList();
	
	@Select("SELECT * FROM good_table WHERE type = #{type}")
	public List<Good> getGoodListByType(String type);
	
	@Update("UPDATE good_table SET id=#{id}, name=#{name}, type=#{type}, spec=#{spec}, "
			+ "remark=#{remark}, stock=#{stock}, price=#{price}"
			+ " WHERE id=#{id}")
	public int updateGoodById(Good good);
	
	@Select("SELECT * FROM good_table WHERE id=#{id}")
	public Good getGoodById(String id);
	
	@Delete("DELETE FROM good_table WHERE id=#{id}")
	public int deleteGoodById(String id);
	
	@Select("SELECT * FROM good_table WHERE name LIKE #{keyword} OR type LIKE #{keyword} OR remark LIKE #{keyword}")
	public List<Good> getGoodListByKeyword(String keyword);
	
}
