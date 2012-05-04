package com.chris.service;

import org.springframework.stereotype.Service;

import com.chris.hibernate.model.Url;
import com.chris.service.base.GenericManager;

@Service(value = "urlManager")
public interface UrlManager extends GenericManager<Url, Long> {

}
