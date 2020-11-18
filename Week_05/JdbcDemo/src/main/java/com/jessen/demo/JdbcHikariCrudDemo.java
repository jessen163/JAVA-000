package com.jessen.demo;

import com.jessen.demo.entity.Article;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 * jdbc crud demo
 */
@Slf4j
public class JdbcHikariCrudDemo {
    Connection conn;
    private static final String DB_CONFIG_FILE = "/db.properties";
    // 数据库连接数
    private short db_max_conn = 0;

    // 数据库服务器addr
    private String db_url = null;

    // 数据库连接端口
    private short db_port = 0;

    // 数据库名称
    private String db_name = null;

    // 数据库登录用户名
    private String db_username = null;

    // 数据库登录密码
    private String db_password = null;
    /**
     * @return
     */
    public Connection getConnection() throws SQLException {
        Properties properties = new Properties();
        InputStream in = JdbcHikariCrudDemo.class.getClass().getResourceAsStream(DB_CONFIG_FILE);
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        db_max_conn = Short.valueOf(properties.getProperty("db_max_conn"));
        db_url = String.valueOf(properties.getProperty("db_url"));
        db_port = Short.valueOf(properties.getProperty("db_port"));
        db_name = String.valueOf(properties.getProperty("db_name"));
        db_username = String.valueOf(properties.getProperty("db_username"));
        db_password = String.valueOf(properties.getProperty("db_password"));

        if (db_url == null || db_url.length() == 0) {
            log.error("配置的数据库ip地址错误!");
            System.exit(0);
        }

        HikariConfig config = new HikariConfig();
        config.setMaximumPoolSize(db_max_conn);
        config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        config.addDataSourceProperty("serverName", db_url);
        config.addDataSourceProperty("port", db_port);
        config.addDataSourceProperty("databaseName", db_name);
        config.addDataSourceProperty("user", db_username);
        config.addDataSourceProperty("password", db_password);
        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource.getConnection();
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
        JdbcHikariCrudDemo c = new JdbcHikariCrudDemo();
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
