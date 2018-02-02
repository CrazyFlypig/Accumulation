package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "DatabaseAccessServlet")
public class DatabaseAccessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    //JDBC 驱动名及数据库 URL
    static final String JDBC_ORIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/runoob";

    //数据库的用户名和密码
    static final String USER = "root";
    static final String PASS = "root";

    public DatabaseAccessServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement statement = null;
        //设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String title = "Servlet MySql 测试";
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n");
        try {
            //注册JDBC 驱动器
            Class.forName("com.mysql.jdbc.Driver");
            //打开一个连接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //执行sql查询
            statement = conn.createStatement();
            String sql;
            sql = "select id, name, url from websites";
            ResultSet rs = statement.executeQuery(sql);

            //展开结果集数据库
            while (rs.next()) {
                //通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");

                //输出数据
                out.println("ID: " + id);
                out.println(", 站点名称：" + name);
                out.println(", 站点 URL: " + url);
                out.println("<br />");
            }
            out.println("</body></html>");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null)
                    statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
