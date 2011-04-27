package com.zsgj.itil.account.webservice;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

/**
 * HRÕ®—∂¬ºSOAP≤‚ ‘¿‡
 * @Class Name HrInfoServiceTest
 * @Author lee
 * @Create In Jan 28, 2010
 */
public class HrInfoServiceTest {
	public static void main(String[] args) throws ServiceException {
		HrInfoService hs = new HrInfoServiceLocator();
		HrInfoServiceSoap_PortType hp = hs.getHrInfoServiceSoap();
		try {
			System.out.println(hp.updatePhone("00999999",null,null,null,true));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
