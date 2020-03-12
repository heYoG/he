<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<%
	String varity=(String)request.getAttribute("vf");
	String varStr[]=new String[]{"","","",""};
	String result="";
	if(varity.indexOf(",")!=-1){
	   result="成功";
	   varStr=varity.split(",");
	}else{
	   result="失败";
	   varStr[0]=varity;
    }
	%>
	<body>
				<table align="center">
					<tr align="center">
					  <td width="20%" > 
							验证结果： <font color=red><%=result%></font>
						</td>
						</tr>
						<tr align="center"><td > <input size="65" type="text" readonly value="<%=varStr[0]%>"></td></tr>
					    <tr align="center"><td > <input size="65" type="text" readonly value="<%=varStr[1]%>"></td></tr>
					    <tr align="center"><td > <input size="65" type="text" readonly value="<%=varStr[2]%>"></td></tr>
					    <tr align="center"><td > <input size="65" type="text" readonly value="<%=varStr[3]%>"></td></tr>
					
				</table>
	</body>
</html>
