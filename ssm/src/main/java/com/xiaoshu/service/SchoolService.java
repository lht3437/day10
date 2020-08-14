package com.xiaoshu.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.AreaMapper;
import com.xiaoshu.dao.SchoolMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Area;

import com.xiaoshu.entity.School;
import com.xiaoshu.entity.SchoolVo;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;

@Service
public class SchoolService {

	@Autowired
	SchoolMapper schoolMapper;

	@Autowired
	AreaMapper areaMapper;


	public PageInfo<SchoolVo> findList(SchoolVo schoolVo, Integer pageNum, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<SchoolVo> list=schoolMapper.findList(schoolVo);
		return new PageInfo<>(list);
	}




	public void updateUser(School school) {
		// TODO Auto-generated method stub
		
	}




	public void addUser(School school) {
		// TODO Auto-generated method stub
		school.setCreatetime(new Date());
		schoolMapper.insert(school);
		
	}




	public List<Area> findAll() {
		// TODO Auto-generated method stub
		return areaMapper.selectAll();
	}




	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		schoolMapper.deleteByPrimaryKey(id);
	}




	public List<SchoolVo> findLog(SchoolVo schoolVo) {
		// TODO Auto-generated method stub
		return schoolMapper.findList(schoolVo);
	}




	public void importSchool(MultipartFile schoolFile) throws InvalidFormatException, IOException {
		// TODO Auto-generated method stub
		Workbook workbook = WorkbookFactory.create(schoolFile.getInputStream());
		//获取sheet对象
		Sheet sheet = workbook.getSheetAt(0);
		

		int lastRowNum = sheet.getLastRowNum(); //获取最后一行的下标
		for (int i = 0; i < lastRowNum; i++) {
			//获取行对象
			Row row = sheet.getRow(i+1);//第一行是表头信息 无需获取
			
			//通过poi解析数据
			//1字符串tostring
			//2时间 getDateCellValue
			String schoolname = row.getCell(0).toString();
			String aname = row.getCell(1).toString();//公司名称
			String phone = row.getCell(2).toString();
			String address = row.getCell(3).toString();
			String status = row.getCell(4).toString();
			Date  createtime=row.getCell(5).getDateCellValue();
			//解析的数据 封装到实体类
			School s=new School();
		
			s.setSchoolname(schoolname);
	
			s.setPhone(phone);
			s.setAddress(address);
			s.setStatus(status);
			s.setCreatetime(createtime);
			
			
			//根据公司名称查询公司id
			Area param=new Area();
			param.setAreaname(aname);
			Area area = areaMapper.selectOne(param);

			s.setAreaid(area.getId());
			
			//保存入库
			schoolMapper.insert(s);
			
	}}

}
