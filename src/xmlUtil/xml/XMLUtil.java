package xmlUtil.xml;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author qer29
 * 
 */
public class XMLUtil {
	static Logger logger = LogManager.getLogger(XMLUtil.class.getName());
	private static String encoding = "utf-8";
	@SuppressWarnings("unchecked")
	public static Object xml2obj(String xmlStr, Class clazz) throws Exception {
		return xml2obj(xmlStr, clazz, true);
	}

	@SuppressWarnings("unchecked")
	public static Object xml2obj(String xmlStr, Class clazz, boolean head)
			throws Exception {
		XMLNote xml = null;
		if (head) {
			xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		} else {
			xml = XMLNote.toNote(xmlStr);
		}
		Object obj = null;
		if (clazz.isArray()) {
			String szName = clazz.getName();
			String className = szName.substring(2, szName.length() - 1);
			String sName = clazz.getSimpleName().substring(0,
					clazz.getSimpleName().length() - 2);
			int count = xml.countOfChild(sName);
			Class subClazz=Class.forName(className);
			obj = Array.newInstance(subClazz, count);
			for (int i = 0; i < count; i++) {
				String subStr = xml.getByName(sName + "[" + i + "]")
						.getSrc_str();
				Object o =subClazz.newInstance();
				o=xml2obj(subStr, subClazz, false);
				Array.set(obj, i, o);
			}
		} else {
			obj = clazz.newInstance();
			Field[] fs = clazz.getDeclaredFields();
			String name = clazz.getSimpleName();
			for (Field field : fs) {
				String subName = field.getType().getSimpleName();
				if (String.class.equals(field.getGenericType())) {
					String sName = name + "." + field.getName();
					field.set(obj, xml.getValue(sName));
				} else {
					if (!field.getType().isArray()) {
						if (xml.getByName(subName) != null) {
							String xmlS = xml.getByName(subName).getSrc_str();
							field.set(obj,
									xml2obj(xmlS, field.getType(), false));
						}
					} else {
						subName = subName.substring(0, subName.length() - 2);
						XMLNote subNode = xml.getByName(field.getName());
						field.set(obj, xml2obj(subNode.getSrc_str(), field
								.getType(), false));
					}
				}
			}
		}
		return obj;
	}

	public static String obj2xml(Object obj) throws Exception {
		return obj2xml(obj, true);
	}

	@SuppressWarnings("unchecked")
	public static String obj2xml(Object obj, boolean head) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (head) {
			sb.append(xmlHead());
		}
		Class c = obj.getClass();
		String name = c.getSimpleName();
		if (c.isArray()) {
			int len = Array.getLength(obj);
			for (int i = 0; i < len; i++) {
				Object o = Array.get(obj, i);
				sb.append(obj2xml(o, false));
			}
		} else {
			sb.append(begin(name));
			Field[] fs = c.getDeclaredFields();
			for (Field field : fs) {
				if (String.class.equals(field.getGenericType())) {
					if (field.get(obj) == null) {
						// sb.append(shortNode(field.getName()));
					} else {
						sb.append(begin(field.getName()));
						//sb.append(field.get(obj)).append("\r\n");
						sb.append(field.get(obj));
						sb.append(end(field.getName()));
					}
				} else {
					if (field.getType().isArray()) {
						sb.append(begin(field.getName()));
						if (field.get(obj) != null) {
							sb.append(obj2xml(field.get(obj), false));
						}
						sb.append(end(field.getName()));
					} else {
						if (field.get(obj) != null) {
							sb.append(obj2xml(field.get(obj), false));
						}
					}
				}
			}
			sb.append(end(name));
		}
		return sb.toString();
	}

	private static String xmlHead() {
		return xmlHead(encoding);
	}

	private static String xmlHead(String coding) {
		return "<?xml version=\"1.0\" encoding=\"" + coding + "\" ?>\r\n";
	}

	private static String begin(String str) {
		return "<" + str + ">";
	}

	private static String end(String str) {
		return "</" + str + ">\r\n";
	}

	public static String shortNode(String str) {
		return "<" + str + "/>\r\n";
	}
}
