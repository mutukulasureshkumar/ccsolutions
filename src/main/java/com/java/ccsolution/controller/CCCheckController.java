package com.java.ccsolution.controller;

import javax.naming.directory.InvalidAttributeIdentifierException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.ccsolution.model.Address;
import com.java.ccsolution.model.CCCheck;
import com.java.ccsolution.repository.CCCheckRepository;
import com.java.ccsolution.services.CCCheckService;

@Controller
public class CCCheckController {
	
	@Autowired
	private CCCheckService service;
	
	@GetMapping("/cccheck")
    public String greetingForm(Model model) {
        model.addAttribute("cccheck", new CCCheck());
        return "cccheck";
    }
	
	@GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("cccheck", new CCCheck());
        return "register";
    }
	
	@PostMapping("/register")
    public String register(@ModelAttribute CCCheck cccheck) {
        if(cccheck == null)
        	return "nocard";
        else{
    		Address address = new Address();
    		address.setFullName(cccheck.getName());
    		address.setAddress("MIYAPUR, HYDERABAD.");
    		address.setZipCode(cccheck.getZip());
    		address.setEmail(cccheck.getMessage());
    		address.setCardDetails(cccheck);
    		CCCheckRepository.CCCheckDB.put(cccheck.getNumber(), address);
        }
        return "cccheck";
    }

    @PostMapping("/cccheck")
    public String greetingSubmit(@ModelAttribute CCCheck cccheck) throws InvalidAttributeIdentifierException, InterruptedException {
    	Address addressInDB = CCCheckRepository.CCCheckDB.get(cccheck.getNumber());
		if (addressInDB == null)
			return "nocard";
    	cccheck = service.checkTransaction(cccheck, addressInDB);
    	if(cccheck.getMessage() == "WAITING")
    		return "waiting";
    	else if(cccheck.getMessage() == "YES")
    		return "success";
    	else
    		return "error";
    }
	
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
