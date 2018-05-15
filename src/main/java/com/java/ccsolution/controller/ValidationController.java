package com.java.ccsolution.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.ccsolution.repository.CCCheckRepository;

@RequestMapping( value = "api")
@RestController
public class ValidationController {
	
	@RequestMapping(value="validatetransaction", method = RequestMethod.GET)
	public String validateTransaction(@RequestParam("uniqueid") long uniqueid, @RequestParam("genuine") String genuine){
		if(CCCheckRepository.CCCheckFraudTrans.get(uniqueid) != null){
			CCCheckRepository.STATUS.put(uniqueid, genuine);
			synchronized (CCCheckRepository.CCCheckFraudTrans.get(uniqueid)){ 
				CCCheckRepository.CCCheckFraudTrans.get(uniqueid).notify();
			}
			return "Thanks for update!!";
		}
		return "URL Expired..!!";
	}
}
