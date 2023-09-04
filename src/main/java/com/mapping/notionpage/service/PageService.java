package com.mapping.notionpage.service;

import com.mapping.notionpage.repository.JdbcRepository;
import com.mapping.notionpage.repository.dto.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

@Service
public class PageService {
    private final DataSource dataSource;

    public PageService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcRepository pageRepository() {
        return new JdbcRepository(dataSource);
    }


    public Page getResult(int request){
        JdbcRepository jdbcRepository=pageRepository();
        //  초기값 설정
        Page page=new Page(1,"tt","test",1,null);
        String result= jdbcRepository.save(page);
        System.out.println(result);
        //jdbcRepository.findByUserId(1);
        return jdbcRepository.findByUserId(request).get();
    }

}
