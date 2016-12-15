package util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class DBHelp {

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
