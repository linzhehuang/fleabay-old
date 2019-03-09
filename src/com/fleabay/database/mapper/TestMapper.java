package com.fleabay.database.mapper;

import org.apache.ibatis.annotations.Select;

public interface TestMapper {
	@Select("SELECT v_name FROM video WHERE v_number = #{name}")
	public String getNameByNumber(int number);
}
