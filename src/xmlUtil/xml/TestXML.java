package xmlUtil.xml;

public class TestXML {

	public static void main(String[] args) throws Exception {
		String test = "<?xml version=\"1.0\" encoding=\"GBK\"?><returnData><errorCode>0</errorCode><errorMsg></errorMsg><batch><objType>1</objType><uniqueCode>103</uniqueCode><operType>1</operType><dept><uniCode>103</uniCode><fullName>合作方</fullName><deptName>合作方</deptName><isCorp>0</isCorp><showNum>0</showNum><deptParent><uniCode>1</uniCode></deptParent><deptCorp><uniCode>1</uniCode></deptCorp></dept></batch></returnData>";
		System.out.println(XMLOperation.toNote(test));
	}
	
	public void test1(){
		
	}
}
