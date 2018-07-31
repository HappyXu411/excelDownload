package com.brain.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.brain.bean.Employee;
import com.brain.bean.Msg;
import com.brain.service.EmployeeService;
import com.brain.utils.ExcelUtils;


@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping(value="/empColumn")
	@ResponseBody
	public Msg exportColumnName() {
		List<String> result = employeeService.getColumnName("tbl_emp");
		return Msg.success().add("columnName", result);
	}
	
	@RequestMapping(value="/export",method=RequestMethod.GET)
	@ResponseBody
    public Msg export(Employee employee,
    					@RequestParam(value="pageNum",defaultValue="1")int pageNum,
    					HttpServletResponse response) throws UnsupportedEncodingException {
		long a = System.currentTimeMillis();
		int tableLength = 0;
		int pageSize = 10000;
		int start = (pageNum - 1) * pageSize;
		List<Employee> empList = employeeService.getByLimit(employee,start,pageSize);
		tableLength = tableLength + empList.size();
		if (empList.isEmpty() == true) {
			return Msg.fail().add("time", (System.currentTimeMillis()-a)/1000f + " 秒 ").add("length", tableLength);
		}
	    //导出操作
		//String rFileName = fileName + "_" + pageNum + ".xls";
	    //ExcelUtils.exportExcel(empList, "员工", "员工信息", Employee.class,"1.xls",response);
	    //List<Employee> personList = ExcelUtils.importExcel(fileName,1,1,Employee.class);
		return Msg.success().add("excelInfo", empList).add("time", (System.currentTimeMillis()-a)/1000f + " 秒 ").add("length", tableLength);
    }
 
	/*
    @RequestMapping("importExcel")
    public void importExcel(){
        String filePath = "F:\\海贼王.xls";
        //解析excel，
        List<Employee> personList = FileUtil.importExcel(filePath,1,1,Employee.class);
        //也可以使用MultipartFile,使用 FileUtil.importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass)导入
        System.out.println("导入数据一共【"+personList.size()+"】行");
 
        //TODO 保存数据库
    }*/
	
	@RequestMapping(value="/selectColumn/{table}",method=RequestMethod.GET)
	@ResponseBody
    public Msg selectColumn(@PathVariable("table")String table){
		List<String> list = employeeService.getColumnName(table);
		return Msg.success().add("list", list);
	}
}
