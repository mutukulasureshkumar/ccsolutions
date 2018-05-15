package com.java.ccsolution.services;

import java.util.Date;

import javax.naming.directory.InvalidAttributeIdentifierException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.ccsolution.model.Address;
import com.java.ccsolution.model.CCCheck;
import com.java.ccsolution.repository.CCCheckRepository;

@Service
public class CCCheckService {
	
	@Autowired
	private EmailService emailService;

	public CCCheck checkTransaction(CCCheck cccheck, Address addressInDB) throws InvalidAttributeIdentifierException, InterruptedException {
		if (cccheck != null) {
			if (cccheck.getZip() != addressInDB.getZipCode()) {
				long unique;
				
				//creating a unique id for the transaction
				synchronized (this) {
					unique = new Date().getTime();
				}
				
				//Sending callback url to the customer
				emailService.sendSimpleMessage(addressInDB, unique);
				
				//Thread waiting for the customer response
				synchronized (this) {
					CCCheckRepository.CCCheckFraudTrans.put(unique, this);
					this.wait(50000);
				}
				
				/*Checking status from the callback url**/
				if(CCCheckRepository.STATUS == null || CCCheckRepository.STATUS.isEmpty()){
					cccheck.setMessage("WAITING");
				}else{
					if(CCCheckRepository.STATUS.get(unique).equals("YES")){
						cccheck.setMessage("YES");
					}else if(CCCheckRepository.STATUS.get(unique).equals("NO")){
						cccheck.setMessage("NO");
					}
				}
				CCCheckRepository.CCCheckFraudTrans.remove(unique);
				CCCheckRepository.STATUS.remove(unique);
			}

		} else {
			throw new InvalidAttributeIdentifierException();
		}
		return cccheck;
	}

	public static double getRandomIntegerBetweenRange(double min, double max) {
		double x = (int) (Math.random() * ((max - min) + 1)) + min;
		return x;
	}

}
