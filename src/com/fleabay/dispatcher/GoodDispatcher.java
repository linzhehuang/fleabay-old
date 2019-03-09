package com.fleabay.dispatcher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fleabay.database.DB;
import com.fleabay.database.data.Good;
import com.fleabay.database.mapper.GoodMapper;


@Controller
public class GoodDispatcher {
	@RequestMapping("getTypeList")
	@ResponseBody
	public List<String> getTypeList() {
		SqlSession sqlSession = DB.getSqlSession();
		GoodMapper goodMapper = sqlSession.getMapper(GoodMapper.class);
		List<String> typeList = goodMapper.getTypeList();
		sqlSession.close();
		return typeList;
	}
	
	@RequestMapping("getGoodList")
	@ResponseBody
	public List<Good> getGoodList() {
		SqlSession sqlSession = DB.getSqlSession();
		GoodMapper goodMapper = sqlSession.getMapper(GoodMapper.class);
		List<Good> goodList = goodMapper.getGoodList();
		sqlSession.close();
		return goodList;
	}
	
	@RequestMapping("getGoodListByType")
	@ResponseBody
	public List<Good> getGoodListByType(@RequestBody Map<String,Object> requestData) {
		String type = (String)requestData.get("type");
		SqlSession sqlSession = DB.getSqlSession();
		GoodMapper goodMapper = sqlSession.getMapper(GoodMapper.class);
		List<Good> goodList = goodMapper.getGoodListByType(type);
		sqlSession.close();
		return goodList;
	}
	
	@RequestMapping("getGoodById")
	@ResponseBody
	public Good getGoodById(@RequestBody Map<String,Object> requestData) {
		String id = (String)requestData.get("id");
		SqlSession sqlSession = DB.getSqlSession();
		GoodMapper goodMapper = sqlSession.getMapper(GoodMapper.class);
		Good good = goodMapper.getGoodById(id);
		sqlSession.close();
		return good;
	}
	
	@RequestMapping("deleteGoodById")
	@ResponseBody
	public Map<String, Object> deleteGoodById(@RequestBody Map<String,Object> requestData, HttpServletRequest request) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		String id = (String)requestData.get("id");
		SqlSession sqlSession = DB.getSqlSession();
		try {
			GoodMapper goodMapper = sqlSession.getMapper(GoodMapper.class);
			goodMapper.deleteGoodById(id);
			sqlSession.commit();
			// 获取图片存放路径
			String imagePath = request.getSession().getServletContext().getRealPath("good-image") + File.separator;
			// 删除图片
			new File(imagePath + id + ".jpg").delete();
		} catch(Exception e) {
			responseData.put("success", false);
			return responseData;
		} finally {
			sqlSession.close();
		}
		responseData.put("success", true);
		return responseData;
	}
	
	@RequestMapping("getGoodListByKeyword")
	@ResponseBody
	public List<Good> getGoodListByKeyword(@RequestBody Map<String,Object> requestData) {
		String keyword = (String)requestData.get("keyword");
		SqlSession sqlSession = DB.getSqlSession();
		GoodMapper goodMapper = sqlSession.getMapper(GoodMapper.class);
		System.out.println(keyword);
		List<Good> goodList = goodMapper.getGoodListByKeyword("%"+keyword+"%");
		sqlSession.close();
		return goodList;
	}
	
	@RequestMapping("uploadGoodImage")
	@ResponseBody
	public Map<String, Object> uploadGoodImage(@RequestParam(value="image",required = false) MultipartFile image, HttpServletRequest request) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		// 生成id
		String id = UUID.randomUUID().toString().replace("-", "").toLowerCase();
		// 获取图片存放路径
		String imagePath = request.getSession().getServletContext().getRealPath("good-image") + File.separator;
		// 存放图片
		try {
			System.out.println(imagePath + id + ".jpg");
			image.transferTo(new File(imagePath + id + ".jpg"));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			responseData.put("success", false);
			return responseData;
		}
		// 响应数据
		responseData.put("success",true);
		responseData.put("id", id);
		return responseData;
	}
	
	@RequestMapping("updateGoodImage")
	@ResponseBody
	public Map<String, Object> updateGoodImage(@RequestParam(value="image",required = false) MultipartFile image, @RequestParam(value="id",required = false) String id, HttpServletRequest request) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		// 获取图片存放路径
		String imagePath = request.getSession().getServletContext().getRealPath("good-image") + File.separator;
		// 修改图片
		try {
			System.out.println(imagePath + id + ".jpg");
			image.transferTo(new File(imagePath + id + ".jpg"));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			responseData.put("success", false);
			return responseData;
		}
		// 响应数据
		responseData.put("success",true);
		responseData.put("id", id);
		return responseData;
	}
	
	@RequestMapping("addGood")
	@ResponseBody
	public Map<String, Object> addGood(@RequestBody Good good, HttpServletRequest request) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		SqlSession sqlSession = DB.getSqlSession();
		try {
			GoodMapper goodMapper = sqlSession.getMapper(GoodMapper.class);
			goodMapper.insertGood(good);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 获取图片存放路径
			String imagePath = request.getSession().getServletContext().getRealPath("good-image") + File.separator;
			// 删除上传图片
			new File(imagePath + good.getId() + ".jpg").delete();
			responseData.put("success", false);
			return responseData;
		} finally {
			sqlSession.close();
		}
		// 响应数据
		responseData.put("success", true);
		return responseData;
	}
	
	@RequestMapping("updateGoodById")
	@ResponseBody
	public Map<String, Object> updateGoodById(@RequestBody Good good) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		SqlSession sqlSession = DB.getSqlSession();
		try {
			GoodMapper goodMapper = sqlSession.getMapper(GoodMapper.class);
			goodMapper.updateGoodById(good);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			responseData.put("success", false);
			return responseData;
		} finally {
			sqlSession.close();
		}
		// 响应数据
		responseData.put("success", true);
		return responseData;
	}
}
