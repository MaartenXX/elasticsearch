package com.maarten.elasticsearch.controller;

import java.util.Iterator;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.maarten.elasticsearch.dao.ClienteRepository;
import com.maarten.elasticsearch.model.Cliente;
import com.maarten.elasticsearch.service.EslService;

@Controller
@RequestMapping(value="/esc")
public class ElasticsearchController {
	@Autowired  
    private EslService esl; 
	
	@Autowired  
    private ClienteRepository clienteDao; 
	
	@RequestMapping(value = "test", method = RequestMethod.POST)  
    public String test(){  
		
		Iterable<Cliente> searchResult = null;
		Iterator<Cliente> iterator = null;
		
		//单值匹配全文查询
		QueryStringQueryBuilder builder=new QueryStringQueryBuilder("徐1");
		searchResult =  clienteDao.search(builder);
		iterator = searchResult.iterator();
		System.out.println("全部字段匹配查询：");
		while(iterator.hasNext()){
			
			System.out.println(iterator.next());
		}
		
		//单值匹配多字段查询
		QueryBuilder builder1 = QueryBuilders.multiMatchQuery("徐123",  
		           "firstname", "lastname");//搜索firstname中或lastname中包含有“徐”的文档 
		searchResult =  clienteDao.search(builder1);
		iterator = searchResult.iterator();
		System.out.println("单值匹配多字段查询：");
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		//多字段分类匹配
		BoolQueryBuilder bu = QueryBuilders.boolQuery();
		QueryBuilder queryBuilder = QueryBuilders.matchQuery("firstname", "徐");
		QueryBuilder queryBuilder1 = QueryBuilders.matchQuery("lastname", "成");
		bu.must(queryBuilder);
		bu.must(queryBuilder1);
		searchResult =  clienteDao.search(bu);
		iterator = searchResult.iterator();
		System.out.println("多字段分类匹配：");
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		
		//多字段 分页 排序
		NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
		SortBuilder sortBuilder = SortBuilders.fieldSort("id")
                .order(SortOrder.DESC).ignoreUnmapped(true);
		nativeSearchQueryBuilder.withSort(sortBuilder);
		nativeSearchQueryBuilder.withPageable(new PageRequest(0, 10));  
		
		//匹配字段字段
		BoolQueryBuilder qb=QueryBuilders.boolQuery();  
		qb.must(queryBuilder);
		qb.must(queryBuilder1);
		nativeSearchQueryBuilder.withQuery(qb);//可以与前面几种查询方式结合起来，灵活配置
		
		SearchQuery searchQuery = nativeSearchQueryBuilder.build();
		searchResult =  clienteDao.search(searchQuery);
		iterator = searchResult.iterator();
		System.out.println("多字段 分页 排序：");
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		return "ok";
    }  
	
	@RequestMapping(value = "save", method = RequestMethod.GET)  
    public void save(){  
        Cliente cliente = new Cliente();
        cliente.setId("12345");
        cliente.setAccount_number(123);
        cliente.setBalance(123333);
        cliente.setAddress("测试地址");
        cliente.setFirstname("徐");
        cliente.setLastname("梦成");
        cliente.setAge(28);
        cliente.setGender("男");
        esl.saveCliente(cliente);
    } 
	
}
