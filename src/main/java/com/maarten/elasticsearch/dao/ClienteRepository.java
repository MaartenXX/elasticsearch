package com.maarten.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.maarten.elasticsearch.model.Cliente;

public interface ClienteRepository extends ElasticsearchRepository<Cliente, String>{
	
}
