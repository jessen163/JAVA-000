package com.jessen.demo1;

import com.jessen.demo1.entity.Order;
import com.jessen.demo1.entity.Product;
import com.jessen.demo1.entity.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * jdbc crud demo
 */
@Slf4j
public class JdbcHikariDemo {
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
        if (conn != null) {
            return conn;
        }
        Properties properties = new Properties();
        InputStream in = JdbcHikariDemo.class.getClass().getResourceAsStream(DB_CONFIG_FILE);
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
        config.setMaximumPoolSize(200);
        HikariDataSource dataSource = new HikariDataSource(config);

        return dataSource.getConnection();
    }


    /**
     * 插入用户
     *
     * @return
     */
    public int insertUser(User user) throws SQLException {
        String sql = "insert demo_user (account, password, nickname, realname, status, register_date, last_login_time) values(?, ?, ?, ?, ?, now(), now())";
        try {
            ResultSet rs = null;
            conn = getConnection();
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getAccount());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getNickname());
            stmt.setString(4, user.getRealname());
            stmt.setInt(5, user.getStatus());

            int result = stmt.executeUpdate();// 返回值代表收到影响的行数
            rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("插入账号成功" + result);
            user.setUserId(id);
            conn.commit();
            return id;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            conn.rollback();
            return 0;
        }
    }


    /**
     * 插入订单
     *
     * @return
     */
    public int insertProduct(Product product) throws SQLException {
        String sql = "insert demo_product (product_name, product_category, price, status, create_date, last_update_time) values(?, ?, ?, ?, now(), now())";
        try {
            ResultSet rs = null;
            conn = getConnection();
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductCategory());
            stmt.setBigDecimal(3, product.getPrice());
            stmt.setInt(4, product.getStatus());

            int result = stmt.executeUpdate();// 返回值代表收到影响的行数
            rs = stmt.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("插入产品成功" + result);
            product.setProductId(id);
            conn.commit();
            return id;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            conn.rollback();
            return 0;
        }
    }

    /**
     * 插入订单
     *
     * @return
     */
    public boolean insertOrderBatch(List<Order> orders) throws SQLException {
        String sql = "insert demo_order (user_id, product_id, product_num, total_price, status, create_date, last_update_time) values(?, ?, ?, ?, ?, now(), now())";
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (Order o : orders) {
                stmt.setInt(1, o.getUserId());
                stmt.setInt(2, o.getProductId());
                stmt.setInt(3, o.getProductNum());
                stmt.setBigDecimal(4, o.getTotalPrice());
                stmt.setInt(5, o.getStatus());

                int result = stmt.executeUpdate();// 返回值代表收到影响的行数
                System.out.println("插入订单成功" + result);
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
        long start = System.currentTimeMillis();
        log.info("start");
        JdbcHikariDemo jdbcHikariDemo = new JdbcHikariDemo();
        List<Integer> productIds = new ArrayList<Integer>();
        for (int i = 0; i< 1000; i++) {
            Product u = new Product();
            u.setProductName("产品"+i);
            u.setProductCategory("分类"+new Random().nextInt());
            u.setPrice(BigDecimal.valueOf(100));
            u.setStatus(1);

            int productId = jdbcHikariDemo.insertProduct(u);
            productIds.add(productId);
        }
        for (int i = 0; i< 30; i++) {
            User u = new User();
            u.setAccount("test1000"+i);
            u.setPassword("14342"+new Random().nextInt());
            u.setNickname("test"+i);
            u.setRealname("张三"+i);

            int userId = jdbcHikariDemo.insertUser(u);
            int orderNums = new Random().nextInt(50000);
            List<Order> orders = new ArrayList<Order>();
            for (int j = 0; j < orderNums; j++) {
                Order order = new Order();
                int pi = new Random().nextInt(productIds.size()-1);
                order.setProductId(productIds.get(pi));
                order.setUserId(userId);
                order.setProductNum(new Random().nextInt());
                order.setStatus(1);
                order.setTotalPrice(BigDecimal.valueOf(2000));
                orders.add(order);
                if (orders.size() > 2000) {
                    jdbcHikariDemo.insertOrderBatch(orders);
                    orders.clear();
                }
                long end = System.currentTimeMillis();
                System.out.println("cost:"+(end-start)/1000+"ms");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("cost:"+(end-start)/1000+"ms");
    }
}
