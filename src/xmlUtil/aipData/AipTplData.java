package xmlUtil.aipData;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AipTplData {
	static Logger logger = LogManager.getLogger(AipTplData.class.getName());
	private List<AipKV> kvs;

	public AipTplData() {
		this.kvs = new ArrayList<AipKV>();
	}

	public void add(AipTplData aipData) {
		for (AipKV obj : aipData.getKvs()) {
			this.add(obj);
		}
	}

	public void add(AipKV obj) {
		int i = 0;
		String temp = obj.getKey();
		while (isKeyIn(temp)) {
			temp = i == 0 ? "&" + obj.getKey() : "&" + obj.getKey() + i;
			i++;
		}
		obj.setKey(temp);
		this.kvs.add(obj);
	}

	public void add(String key, String value) {
		AipKV obj = new AipKV(key, value);
		this.add(obj);
	}

	public boolean isKeyIn(String key) {
		for (AipKV obj : this.kvs) {
			if (key.equals(obj.getKey()))
				return true;
		}
		return false;
	}

	public String dataStr() {
		StringBuffer sb = new StringBuffer();
		for (AipKV obj : this.kvs) {
			sb.append(obj.getKey()).append("=").append(obj.getValue());
			sb.append("\r\n");
		}
		return sb.toString();
	}

	public List<AipKV> getKvs() {
		return kvs;
	}

	public void setKvs(List<AipKV> kvs) {
		this.kvs = kvs;
	}

}
