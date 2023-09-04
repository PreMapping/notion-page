package com.mapping.notionpage.repository;

import com.mapping.notionpage.repository.dto.Page;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.jdbc.datasource.DataSourceUtils.getConnection;

@Repository
public class JdbcRepository {

    private DataSource dataSource;

    public JdbcRepository(DataSource dataSource){
        this.dataSource=dataSource;
    }

    public String save(Page page){
        String SQL= "INSERT INTO page (id,title,content,top_node,parent_page_id) VALUES(?,?,?,?,?)";
        Connection connection=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {

            connection= getConnection(dataSource);
            pstmt=connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            System.out.println(page.getId());
            System.out.println(page.getTitle());
            pstmt.setLong(1,page.getId());
            pstmt.setString(2,page.getTitle());
            pstmt.setString(3,page.getContent());
            pstmt.setLong(4, page.getTopPageId());

            if(page.getParentPageId()!=null){
                pstmt.setLong(5,page.getParentPageId().get());
            }
            else {
                pstmt.setNull(5,java.sql.Types.INTEGER);
            }



            pstmt.executeUpdate();
            rs=pstmt.getGeneratedKeys();

            if(rs.next()){
                page.setId(rs.getLong(1));
               // return page.getId();
            }
            return "input success";
        }catch (SQLException e){
            System.out.println(e);
            return e.toString();
        }finally {
            DbClose.close(connection,pstmt,rs,dataSource);
        }
    }

    public Optional<Page> findByUserId(long userId) {
        String SQL = "SELECT id,title,content,top_node,parent_page_id FROM page WHERE id = (?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection(dataSource);
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, userId);
            rs = pstmt.executeQuery();

            while(rs.next()){
                Page page = new Page(rs.getLong("id"), rs.getString("title"), rs.getString("content"), rs.getLong("top_node"),Optional.ofNullable( rs.getLong("parent_page_id")));
                page.setId(rs.getLong("id"));


                System.out.println(page);
                return Optional.ofNullable(page);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
           DbClose.close(connection,pstmt,rs,dataSource);
        }
        return Optional.empty();
    }
    public Optional <List<Page>> findByUsersId(long topNode) {
        String SQL = "SELECT id,title,content,top_node,parent_page_id FROM page WHERE top_node = (?)";
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            connection = getConnection(dataSource);
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, topNode);
            rs = pstmt.executeQuery();

            List <Page> results=new ArrayList<>();
            while(rs.next()){
                Page page = new Page(rs.getLong("id"), rs.getString("title"), rs.getString("content"), rs.getLong("top_node"),Optional.ofNullable( rs.getLong("parent_page_id")));
                page.setId(rs.getLong("id"));

                results.add(page);
                System.out.println(page);

            }
            return Optional.ofNullable( results);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DbClose.close(connection,pstmt,rs,dataSource);
        }
        return Optional.empty();
    }

}
