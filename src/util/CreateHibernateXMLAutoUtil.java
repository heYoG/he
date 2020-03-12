package util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import vo.userVo.UserVo;

/**
 * 	自动生成hibernate映射文件
 * @author Administrator
 *
 */
public class CreateHibernateXMLAutoUtil {
	/**
	 * 	标准化方法，生成文件不含表间级联关系
	 * @function 根据POJO类生成简单的相应*.hbm.xml配置文件，复杂功能请在此基础上手动配置。<br/>(Tip:请在完成POJO后使用junit或者main方法预先执行此方法以生成配置文件)
	 * @author lyl
	 * @time 2016-1-13 下午03:48:20
	 * @param cls POJO的类对象,多主
	 * @param casCls	级联类,一方,为空时flag可为任意值
	 * @param identification1 数据库中主键对应的相应的POJO属性名
	 * @param identification2 数据库中外键对应的相应的POJO属性名
	 * @param tableName 数据库中的相应表名
	 * @param flag		one-to-many or many-to-one 0:一对多;1:多对一;2:无级联关系
	 */
	public static void createHibernatePOJOXML(Class<?> cls,String identification1,String tableName,Class<?> casCls,String identification2,int flag){	 
		//创建文档对象
		 Document rootDocument = DocumentHelper.createDocument();
	     rootDocument.addDocType("hibernate-mapping", "-//Hibernate/Hibernate Mapping DTD 3.0//EN", "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd");
	     //加入根
	     Element rootElement = rootDocument.addElement("hibernate-mapping");
	     //在根中加第一个子节点
	     Element elementClass = rootElement.addElement("class");
	     elementClass.addAttribute("name", cls.getName());
	     elementClass.addAttribute("table", tableName);
	     Element ElementClassID = elementClass.addElement("id");
	     Field[] declaredFields = cls.getDeclaredFields();
	     for (Field field : declaredFields) {//通过反射遍历获取POJO属性 	 
	    	 if(field.getName().equals(identification1)){//设置映射文件的主键 
	    		 ElementClassID.addAttribute("name", identification1);
	    		 ElementClassID.addAttribute("type", field.getType().getName());
	    		 Element ElementClassIDColumn = ElementClassID.addElement("column");
	    		 ElementClassIDColumn.addAttribute("name", identification1);
	    		 Element ElementClassIDGenerator = ElementClassID.addElement("generator");
	    	     ElementClassIDGenerator.addAttribute("class", "native");
	    	 }else if(casCls!=null) {//级联关系映射
	    		 if(field.getType().getSimpleName().equals(casCls.getSimpleName()))
	    		 if(flag==0) {
	    			 Element elementCas = elementClass.addElement("set");
	    			 elementCas.addAttribute("name", field.getName());
	    			 elementCas.addAttribute("lazy", "false");//不懒加载
	    			 elementCas.addAttribute("inverse", "true");//控制反转
	    			 elementCas.addAttribute("cascade", "delete");//级联删除
	    			 Element element_one_key = elementCas.addElement("key");
	    			 element_one_key.addAttribute("column", identification2);//多方外键,一般级联一方主键
	    			 Element element_one_cas = elementCas.addElement("one-to-many");
	    			 element_one_cas.addAttribute("class", cls.getName());//映射多方
	    			 
	    		 }else if(flag==1) {
	    			 Element elementCas = elementClass.addElement("many-to-one");
	    			 elementCas.addAttribute("name", field.getName());
	    			 elementCas.addAttribute("lazy", "false");
	    			 elementCas.addAttribute("column", identification2);//多方外键,一般级联一方主键
	    			 elementCas.addAttribute("class", casCls.getName());//映射一方
	    		 }else {
	    			 System.out.println("无级联关系...");
	    		 }
	    	 }else{//设置其它属性
	    		 Element ElementProperty = elementClass.addElement("property");
	    		 ElementProperty.addAttribute("name", field.getName());
	    		 ElementProperty.addAttribute("type", field.getType().getName());
	    		 Element ElementColumn = ElementProperty.addElement("column");
	    		 ElementColumn.addAttribute("name", field.getName());	    		 
	    	 }
		}
	     
	     //写xml
	     Writer writer = null;
	     String pathWay = "src/"+cls.getName().replace('.','/')+".hbm.xml";//getName()获取到的是全路径
		try {
			writer = new FileWriter(pathWay);
			System.out.println("生成映射文件成功!");
		} catch (IOException e) {
			System.out.println("HibernateXMLAutoCreateUtils:XML生成错误,看到这个绝壁是因为长得丑");
			e.printStackTrace();
		}
	     //格式化xml
	     OutputFormat format = OutputFormat.createPrettyPrint();//xml输出格式(会自动缩进、换行)
	     format.setEncoding("UTF-8");//设置xml文档编码
	     XMLWriter xml = new XMLWriter(writer,format);//xml对象和格式
	     try {
			xml.write(rootDocument);//生成xml文件
		} catch (IOException e) {		
			e.printStackTrace();		
		}finally{//关闭流
			if(writer!=null){
				try {
					writer.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}	
			}
			 if(xml!=null){
		    	 try {
		 			xml.close();
		 		} catch (IOException e) {
		 			e.printStackTrace();
		 		}
		     }
			
		}  
	}
	
	/**
	 * 	执行方法生成映射文件
	 * @param args
	 */
	public static void main(String[] args) {
	
		
	}
}
