package com.brain.service;

import java.io.InputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.brain.bean.Employee;
import com.brain.bean.EmployeeExample;
import com.brain.dao.EmployeeMapper;


@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;
	
	/**
	 * 查询所有员工
	 * @return
	 */
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return employeeMapper.selectByExample(null);
	}

	/**
	 * 按条件查询员工
	 * @return
	 */
	public List<Employee> getByLimit(Employee employee,int pageStart,int pageSize) {
		return employeeMapper.selectByLimit(employee,pageStart,pageSize);
	}
	
	public List<String> getColumnName(String tableName) {
		return employeeMapper.selectColumn_Name(tableName);
	}
	
	/**
	 * 保存员工信息
	 * @param employee
	 */
	public void saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}

	/**
	 * 按照员工ID查询
	 * @param id
	 * @return
	 */
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}
	
	
}
