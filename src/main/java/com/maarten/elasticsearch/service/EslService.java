package com.maarten.elasticsearch.service;

import com.maarten.elasticsearch.model.Cliente;

public interface EslService{
	/**
	 * 
	 * @param queryString 关键字
	 * @return
	 */
    Cliente findCliente(String queryString);
    
    void saveCliente(Cliente cliente);
}
