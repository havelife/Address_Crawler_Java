package com.chris.service;

import org.springframework.stereotype.Service;

import com.chris.hibernate.model.Address;
import com.chris.service.base.GenericManager;

@Service(value = "addressManager")
public interface AddressManager extends GenericManager<Address, Long> {

}
