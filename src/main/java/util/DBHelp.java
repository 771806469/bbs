package util;

import exception.DataAccessException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class DBHelp {

    private static Logger logger = LoggerFactory.getLogger(DBHelp.class);

    private static Connection getConnection() throws SQLException {
        return ConnectionManager.getConnection();
    }


    public static void update(String sql,Object... params) {
        QueryRunner queryRunner = new QueryRunner();

        Connection conn = null;
        try {
            conn = getConnection();
            queryRunner.update(conn,sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }

    }

    public static <T>T query(String sql, ResultSetHandler<T> handler, Object... params) {
        //QueryRunner queryRunner = new QueryRunner(ConnectionManager.getBasicDataSource());
        QueryRunner queryRunner = new QueryRunner();

        T t = null;
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            t = queryRunner.query(conn,sql,handler,params);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }

        return t;
    }

    public static Integer insert(String sql,Object... params) {
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getBasicDataSource());
        Integer id;
        try {
            id = queryRunner.insert(sql, new ScalarHandler<Long>(), params).intValue();
        } catch(SQLException ex) {
            logger.debug("执行insert语句{}，插入帖子时出现错误",sql);
            throw new DataAccessException("执行insert语句" + sql + "时出现错误！");
        }
        return id;
    }

    public static void close(Connection conn) {
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
