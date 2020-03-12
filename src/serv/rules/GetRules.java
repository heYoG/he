package serv.rules;

public class GetRules {

	private String rule_no;
	private String seal_name;
	private String cert_name;
	private String cert_pwd;
	private String rule_desc;
	private Integer rule_type;
	private String seal_data;
	

	public String getSeal_data() {
		return seal_data;
	}
	public void setSeal_data(String seal_data) {
		this.seal_data = seal_data;
	}
	public Integer getRule_type() {
		return rule_type;
	}
	public void setRule_type(Integer rule_type) {
		this.rule_type = rule_type;
	}
	public String getRule_no() {
		return rule_no;
	}
	public void setRule_no(String rule_no) {
		this.rule_no = rule_no;
	}
	public String getSeal_name() {
		return seal_name;
	}
	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}
	public String getCert_name() {
		return cert_name;
	}
	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}
	public String getCert_pwd() {
		return cert_pwd;
	}
	public void setCert_pwd(String cert_pwd) {
		this.cert_pwd = cert_pwd;
	}
	public String getRule_desc() {
		return rule_desc;
	}
	public void setRule_desc(String rule_desc) {
		this.rule_desc = rule_desc;
	}
	
}
