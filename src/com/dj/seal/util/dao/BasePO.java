package com.dj.seal.util.dao;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class BasePO implements Serializable, Cloneable {
	static Logger logger = LogManager.getLogger(BasePO.class.getName());
    public BasePO() {    }

    @Override
	public abstract int hashCode();   

    @Override
	public abstract boolean equals(Object obj);

}