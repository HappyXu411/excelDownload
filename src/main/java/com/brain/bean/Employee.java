package com.brain.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Employee {
	@Excel(name = "编号", height = 20, width = 30, isImportField = "true_st")
    private Integer empid;
    
    @Excel(name = "姓名", height = 20, width = 30, isImportField = "true_st")
    private String empname;
    
    @Excel(name = "性别", replace = { "男_1", "女_2" }, isImportField = "true_st")
    private String gender;

    @Excel(name = "邮箱", isImportField = "true_st", width = 20)
    private String email;
    
    @Excel(name = "部门编号", height = 20, width = 30, isImportField = "true_st")
    private Integer did;

    public Employee() {
    	
    }
    
    public Employee(Integer empid,String empname,String gender,String email,Integer did) {
    	this.empid = empid;
    	this.empname = empname;
    	this.gender = gender;
    	this.email = email;
    	this.did = did;
    }
    
	public Integer getEmpId() {
		return empid;
	}

	public void setEmpId(Integer empid) {
		this.empid = empid;
	}

	public String getEmpName() {
		return empname;
	}

	public void setEmpName(String empname) {
		this.empname = empname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getDId() {
		return did;
	}

	public void setDId(Integer did) {
		this.did = did;
	}
    
    
}