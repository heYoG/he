package com.dj.seal.structure.dao.po;

import com.dj.seal.util.dao.BasePO;

public class SysDepartment extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dept_no; // 部门编号
	private String priv_no; // 自身编号
	private String dept_name; // 部门名称
	private String tel_no; // 电话号码
	private String fax_no; // 传真号码
	private String dept_tab; // 部门排序号
	private String dept_parent; // 父部门编号
	private Integer dept_lever; // 部门所属级别
	private String dept_func; // 部门职能
    private String is_detpflow;//是否有下级部门
    private String is_deptuser;//部门下是否存在用户
    private String is_depttemp;//部门下是否存在印模
    private String is_deptseal;//部门下是否存在印章
	private boolean inManage; // 是否在管理范围内

	private int tree_id;//树节点值
	private int p_tree_id;//树父节点值
	private int has_children;//是否有叶子节点
	private String bank_dept;//银行外键对应的部门编号

	public String getBank_dept() {
		return bank_dept;
	}							

	public void setBank_dept(String bank_dept) {
		this.bank_dept = bank_dept;
	}

	public String getIs_deptuser() {
		return is_deptuser;
	}

	public void setIs_deptuser(String is_deptuser) {
		this.is_deptuser = is_deptuser;
	}

	public String getIs_depttemp() {
		return is_depttemp;
	}

	public void setIs_depttemp(String is_depttemp) {
		this.is_depttemp = is_depttemp;
	}

	public String getIs_deptseal() {
		return is_deptseal;
	}

	public void setIs_deptseal(String is_deptseal) {
		this.is_deptseal = is_deptseal;
	}

	public String getIs_detpflow() {
		return is_detpflow;
	}

	public void setIs_detpflow(String is_detpflow) {
		this.is_detpflow = is_detpflow;
	}

	public boolean isInManage() {
		return inManage;
	}

	public void setInManage(boolean inManage) {
		this.inManage = inManage;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SysDepartment other = (SysDepartment) obj;
		if (dept_no == null) {
			if (other.dept_no != null)
				return false;
		} else if (!dept_no.equals(other.dept_no))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((dept_no == null) ? 0 : dept_no.hashCode());
		return result;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getPriv_no() {
		return priv_no;
	}

	public void setPriv_no(String priv_no) {
		this.priv_no = priv_no;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getTel_no() {
		return tel_no;
	}

	public void setTel_no(String tel_no) {
		this.tel_no = tel_no;
	}

	public String getFax_no() {
		return fax_no;
	}

	public void setFax_no(String fax_no) {
		this.fax_no = fax_no;
	}

	public String getDept_tab() {
		return dept_tab;
	}

	public void setDept_tab(String dept_tab) {
		this.dept_tab = dept_tab;
	}

	public String getDept_parent() {
		return dept_parent;
	}

	public void setDept_parent(String dept_parent) {
		this.dept_parent = dept_parent;
	}

	public Integer getDept_lever() {
		return dept_lever;
	}

	public void setDept_lever(Integer dept_lever) {
		this.dept_lever = dept_lever;
	}

	public String getDept_func() {
		return dept_func;
	}

	public void setDept_func(String dept_func) {
		this.dept_func = dept_func;
	}

	public int getTree_id() {
		return tree_id;
	}

	public void setTree_id(int tree_id) {
		this.tree_id = tree_id;
	}

	public int getP_tree_id() {
		return p_tree_id;
	}

	public void setP_tree_id(int p_tree_id) {
		this.p_tree_id = p_tree_id;
	}

	public int getHas_children() {
		return has_children;
	}

	public void setHas_children(int has_children) {
		this.has_children = has_children;
	}

}
