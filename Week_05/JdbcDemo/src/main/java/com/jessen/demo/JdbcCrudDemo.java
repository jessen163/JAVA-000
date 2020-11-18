package com.jessen.demo;

import com.jessen.demo.entity.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * jdbc crud demo
 */
public class JdbcCrudDemo {
    Connection conn;

    /**
     * @return
     */
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8", "root", "123456");
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 查询文章列表
     *
     * @return
     */
    public ResultSet findArticleList() {
        ResultSet resultSet = null;

        String sql = "select * from article";
        try {
            conn = getConnection();
            Statement stmt = conn.prepareStatement(sql);
            resultSet = stmt.executeQuery(sql);
            List<Article> articleList = new ArrayList();
            while (resultSet.next()) {
                Article article = new Article(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getDate("create_date"));
                articleList.add(article);
            }
            System.out.println("文章列表:"+articleList.toString());
            return resultSet;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * 插入文章
     *
     * @return
     */
    public int insertArticle(String title, String author) {
        String sql = "insert article (title, author, create_date) values(?, ?, now())";
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, title);
            stmt.setString(2, author);
            int result = stmt.executeUpdate();// 返回值代表收到影响的行数
            System.out.println("插入成功" + title + "_" + author);
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * 修改文章
     *
     * @return
     */
    public int updateArticle(int id, String title, String author) {
        String sql = "update article set title = ?, author = ? where id = ?";
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, id);
            int result = stmt.executeUpdate();// 返回值代表收到影响的行数
            System.out.println("插入成功" + title + "_" + author);
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * 修改文章
     *
     * @return
     */
    public int deleteArticle(int id) {
        String sql = "delete from article where id = ?";
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            int result = stmt.executeUpdate();// 返回值代表收到影响的行数
            System.out.println("插入成功" + id);
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }



    /**
     * 插入文章
     *
     * @return
     */
    public boolean insertArticleBatch(List<Article> articles) throws SQLException {
        String sql = "insert article (title, author, create_date) values(?, ?, now())";
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (Article article: articles) {
                stmt.setString(1, article.getTitle());
                stmt.setString(2, article.getAuthor());
                int result = stmt.executeUpdate();// 返回值代表收到影响的行数
                System.out.println("插入成功" + article.getTitle() + "_" + article.getAuthor());
            }
            conn.commit();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            conn.rollback();
            return false;
        }
    }

    public static void main(String[] args) throws SQLException {
        JdbcCrudDemo c = new JdbcCrudDemo();
        c.getConnection();

        c.findArticleList();

        int i = c.insertArticle("测试", "张三");

        c.findArticleList();

        i = c.updateArticle(i, "测试1", "李四");

        c.findArticleList();

        c.deleteArticle(i);

        c.findArticleList();

        List<Article> articles = new ArrayList<Article>();
        for (int j = 0; j < 10; j++) {
            articles.add(new Article(10+j, "测试_"+j, "姓名_"+j, new Date()));
        }
        c.insertArticleBatch(articles);
    }
}
