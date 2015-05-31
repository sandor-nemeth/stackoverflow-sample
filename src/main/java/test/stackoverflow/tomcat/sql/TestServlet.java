package test.stackoverflow.tomcat.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class TestServlet extends HttpServlet {

  private static final long serialVersionUID = -7432424422709945682L;

  private Connection connection = null;
  private Statement statement = null;
  private PreparedStatement prepStatement = null;
  private ResultSet results = null;
  private static final String DATASOURCE_NAME = "java:comp/env/jdbc/lab";

  @Override
  public void init() throws ServletException {
    getConnection();
    try {
      connection.createStatement().executeUpdate("DROP TABLE IF EXISTS lab_user");
      connection.createStatement().executeUpdate(
          "CREATE TABLE lab_user (user_id int not null, name varchar(200), primary key (user_id))");
      connection.createStatement().executeUpdate("INSERT INTO lab_user VALUES (1, 'fabio')");
    } catch (SQLException e) {
      throw new ServletException(e);
    }
  }


  public void getConnection() {
    if (connection == null) {
      try {
        Context initialContext = new InitialContext();
        DataSource ds = (DataSource) initialContext.lookup(DATASOURCE_NAME);
        connection = ds.getConnection();
      } catch (NamingException e) {
        e.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    service(req, resp);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      getConnection();
      statement = connection.createStatement();
      results = statement.executeQuery("select * from lab_user where user_id = 1");

      if (results != null) {
        results.next();

        String id = results.getString("user_id");
        String name = results.getString("name");
      }
      results = null;

    } catch (SQLException e) {
      String error = "" + e;
      throw new ServletException(e);
    }
    resp.getWriter().println("OK");
  }
}
