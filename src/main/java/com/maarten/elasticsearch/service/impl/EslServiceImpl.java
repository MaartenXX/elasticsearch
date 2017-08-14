package com.maarten.elasticsearch.service.impl;

import java.util.Iterator;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.maarten.elasticsearch.dao.ClienteRepository;
import com.maarten.elasticsearch.model.Cliente;
import com.maarten.elasticsearch.service.EslService;

@Service
public class EslServiceImpl implements EslService {

	@Autowired  
    private ClienteRepository clienteDao;  
  
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(EslServiceImpl.class);  
    
    @Override  
    public Cliente findCliente(String queryString){  
        queryString="男";//搜索关键字
		QueryStringQueryBuilder builder=new QueryStringQueryBuilder(queryString);
//		Iterable<Cliente> searchResult = clienteDao.search(builder);
		
		BoolQueryBuilder bu = QueryBuilders.boolQuery();
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("firstname", "徐");
		QueryBuilder queryBuilder1 = QueryBuilders.matchQuery("lastname", "梦成");
		bu.must(queryBuilder);
		bu.must(queryBuilder1);
		Iterable<Cliente> searchResult1 =  clienteDao.search(bu);
		
		Iterator<Cliente> iterator = searchResult1.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
        return null;  
    }

	@Override
	public void saveCliente(Cliente cliente) {
		clienteDao.save(cliente);
	}  

}
