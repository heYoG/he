package com.dj.seal.accumulationFund.service;

import java.util.UUID;

import com.dj.seal.accumulationFund.service.impl.AccumulationDealImpl;

public class AccumulationFunTest {
public static void main(String[] args) {
	AccumulationDealImpl accumulationDealImpl = new AccumulationDealImpl();
	int i = accumulationDealImpl.addSealToAccumulation();
	System.out.println("return:"+i);
}
}
