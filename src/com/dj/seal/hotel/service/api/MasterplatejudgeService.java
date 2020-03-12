package com.dj.seal.hotel.service.api;

import java.util.List;
import com.dj.seal.hotel.form.MasterplatejudgeForm;

import com.dj.seal.hotel.po.Masterplatejudgep;
import com.dj.seal.util.exception.DAOException;

public interface MasterplatejudgeService {
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Masterplatejudgep> showMasterplatejudges() throws Exception;
	
	/**
	 * 
	 * @param cid
	 * @return
	 * @throws DAOException
	 */
	public List<Masterplatejudgep> shwoMasterplatejudgeById(String cid) throws DAOException;
	
	/**
	 * 
	 * @param cid
	 * @return
	 * @throws DAOException
	 */
	public Masterplatejudgep getMasterplatejudge(String cid) throws DAOException;
	
	/**
	 * 
	 * @param mj
	 * @return 1 成功 0 失败
	 * @throws DAOException
	 */
	public int addMasterplatejudge(Masterplatejudgep mj) throws DAOException;
	
	/**
	 * 1 成功 0 失败
	 * @param mj
	 * @return
	 * @throws DAOException
	 */
	public int delMasterplatejudge(String cid ) throws DAOException;
	
	/**
	 *  1 成功 0 失败
	 * @param mjp
	 * @return
	 * @throws DAOException
	 */
	public int updateMasterplatejudge(Masterplatejudgep mjp) throws DAOException;
	
	public int addMasterplatejudge(MasterplatejudgeForm f) throws Exception;
}
