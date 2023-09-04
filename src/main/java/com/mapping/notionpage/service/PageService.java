package com.mapping.notionpage.service;

import com.mapping.notionpage.repository.JdbcRepository;
import com.mapping.notionpage.repository.dto.Page;
import com.mapping.notionpage.repository.dto.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PageService {
    private final DataSource dataSource;

    public PageService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcRepository pageRepository() {
        return new JdbcRepository(dataSource);
    }


    public PageResult getResult(Long request){
        JdbcRepository jdbcRepository=pageRepository();
        //요청들어온 page 객체
//        Page requestObj=jdbcRepository.findByUserId(request).get();
        Page requestObj = jdbcRepository.findByUserId(request).orElseThrow();

        //  초기값 설정
        Object[][] input= {
                {1,"1","test",1,0},
                {2,"2","test",1,1},
                {3,"3","test",1,1},
                {4,"4","test",1,2},
                {5,"5","test",1,2},
                {6,"6","test",1,3},
                {8,"8","test",1,3},
                {9,"9","test",1,5},
                {10,"10","test",1,5},
                {11,"11","test",1,5},

        };
        for(int i=0; i<input.length;i++){
            Page page=new Page(Long.parseLong( input[i][0].toString()),(String) input[i][1],(String) input[i][2], Long.parseLong(input[i][3].toString()), Optional.of( Long.parseLong(input[i][4].toString())));
            String result= jdbcRepository.save(page);
        }


        // 입력받은 값의 topNode조회
        Long topNode=jdbcRepository.findByUserId(request).get().getTopPageId();

        //같은 topNode  가진 원소들 확인
        List<Page> nodeList=jdbcRepository.findByUsersId(topNode).get();

        //서브페이지 리스트 구하기
        ArrayList<Long> subPages=getSubPages(request,nodeList);

        //Bread Crumbs 구하기
        ArrayList<Long>emptyList=new ArrayList<>();
        ArrayList<Long> breadCrumbs=getBreadCrumbs(request,nodeList,topNode,emptyList);
        Collections.reverse(breadCrumbs);
        breadCrumbs.add(request);       // 마지막에 자기자신 추가

        System.out.println(subPages);
        System.out.println(breadCrumbs);

        //결과 반환
        PageResult pageResult=new PageResult(requestObj.getId(),requestObj.getTitle(),requestObj.getContent(),subPages,breadCrumbs);

        return pageResult;
    }

    public ArrayList<Long> getSubPages(Long request,List<Page> nodeList){       //부모로 request가진 것들 추가
        ArrayList<Long> subPageArray=new ArrayList<>();
        for(int i=0;i<nodeList.size();i++){
            if(nodeList.get(i).getParentPageId().get()==request){
                subPageArray.add(nodeList.get(i).getId());
            }
        }
        return subPageArray;
    }
    public ArrayList<Long> getBreadCrumbs(Long request,List<Page> nodeList,Long topNode,ArrayList<Long> breadRecursive){

        int requestId=0;
        for(int i=0;i<nodeList.size();i++){
            if(nodeList.get(i).getId()==request){
                requestId=i;
                break;
            }
        }
        breadRecursive.add(nodeList.get(requestId).getParentPageId().get());

        if(nodeList.get(requestId).getParentPageId().get()==topNode){       //탑노드 도착시 리턴
            return breadRecursive;
        }
        breadRecursive= getBreadCrumbs(nodeList.get(requestId).getParentPageId().get(),nodeList,topNode,breadRecursive);
        return breadRecursive;
    }

}
