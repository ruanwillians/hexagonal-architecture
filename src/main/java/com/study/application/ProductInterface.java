package com.study.application;


import java.util.UUID;

import com.study.variables.Status;

public interface ProductInterface {
	UUID getId();
	String getName();
	Status getStatus();
	double getPrice();
	Boolean isValid() throws Exception; 
	void enable() throws Exception;
	void disable() throws Exception;
}
