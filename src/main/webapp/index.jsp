<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<script type="text/javascript" src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
<link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="${APP_PATH }/static/js/JsonExportExcel.min.js"></script>
<meta charset="UTF-8">
<title>查询</title>
</head>
<body>
	<div class="container" id="container">
		<div id="addColumn">
			<label for="exampleInputColumnName">要查询的字段名</label>
			<select class="form-control" name="column" id="columnSelect">
				
			</select>
			<button type="submit" class="btn btn-default" id="addColumn_btn">添加</button>
		</div>
		<div id="column_area">
			<div class="form-group">
	    		<label for="exampleInputNum">希望查询的范围（1代表1-10000,2代表10001-20000）</label>
	    		<input type="text" name="pageNum" class="form-control" id="find_byPage" placeholder="1">
	  		</div>
			<div class="form-group">
	    		<label for="exampleInputName2">FileName</label>
	    		<input type="text" name="fileName" class="form-control" id="build_fileName" placeholder="默认输出表">
	  		</div>
		</div>
		<button type="submit" class="btn btn-default" id="find_btn">查询</button>
		<div class="row">
			<div class="col-md-6" id="info_area">
			
			</div>
		</div>
	</div>
	<script>
		var columnArr = [];
		var baseColumnArr = ['DOCID','DOCTITLE','DOCPUBURL','email','d_id'];
		function validate_find_form(){
			var nameRegex = /^[\u2E80-\u9FFF]+$/;
			var emailRegex = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			var name = $("#find_name").val();
			var gender = $("#find_gender").val();
			if (!nameRegex.test(name)){
				alert("必须是输入中文的内容进行查询");
				return false;
			} else if (!nameRegex.test(gender)) {
				alert("输入信息有误");
				return false;
			} else {
				return true;
			}
		}
		
		function build_excel(jsonData,fileName,sheetHeader) {
			var option = {};
			option.fileName = $("#build_fileName").val();
			var data = [];
			for (var i = 0;i < jsonData.length;i++) {
				var arr = [];
				for (var j = 0;j < Object.keys(jsonData[i]).length;j++) {
					var str = Object.keys(jsonData[i])[j];
					arr.push(jsonData[i][str]);
				}
				data.push(arr);
			}
			option.datas = [{
				sheetData : data,
				sheetHeader : sheetHeader
			}];
			var toExcel=new ExportJsonExcel(option);
			toExcel.saveExcel();
		}
		
		$(function(){
			$.ajax({
				url: "/excelTest/empColumn",
				type:"GET",
				success:function(result){
					//console.log(result.extend.columnName);
					$.each(result.extend.columnName,function(key,value){
						var optionEle = $("<option></option>").append(value).attr("value", value);
						optionEle.appendTo("#columnSelect");
					});
				}
			});
		});
		
		$("#addColumn_btn").click(function(){
			if ($('#columnSelect option:selected').val() != null){
				columnArr.push($('#columnSelect option:selected').val());
				$("#column_area").append(
					$("<div></div>").addClass("form-group").append(
						$("<label></label>").attr("for","exampleInput" + $('#columnSelect option:selected').val()).append($('#columnSelect option:selected').val())
					).append(
						$("<input>").addClass("form-control").attr("id","find_" + $('#columnSelect option:selected').val())
					) 
				);
				$('#columnSelect option:selected').remove();
			} else {
				$('#addColumn').remove();
			}
		});
		
		$("#find_btn").click(function(){
			//1.将填写好的表单内容提交给服务器进行保存
			//提交之前先对数据进行合法性检验

			/*if (!(validate_find_form())) {
				return false;
			}*/
			var str = "";
			/*if (columnArr.isEmpty()){
				for (var i = 0;i < columnArr.length;i++){
					str = str + columnArr[i] + "=" + $("#find_" + columnArr[i]).val() + "&"
				}
				str = str + "pageNum=" + $("#find_byPage").val();
				console.log(str);
			} else {*/
				for (var i = 0;i < columnArr.length;i++){
					str = str + columnArr[i] + "=" + $("#find_" + columnArr[i]).val() + "&"
				}
				str = str + "pageNum=" + $("#find_byPage").val();
			//}
			
			//2.开始进行查询
			$.ajax({
				url: "/excelTest/export",
				data: str,
				type:"GET",
				success:function(result){
					$("#info_area").empty();
					$("#info_area").append(
							"当前执行了" + result.extend.time + ",有" + 
							result.extend.length + "条结果");
					build_excel(result.extend.excelInfo,basecolumnArr);
				}
			});
		});
	</script>
</body>
</html>