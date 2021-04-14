package repositories;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Departamento;

public class RepositoryDepartamentos {

    private Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new SQLServerDriver());
        String cadena
                = "jdbc:sqlserver://sqlserverjavapgs.database.windows.net:1433;databaseName=SQLAZURE";
        Connection cn = DriverManager.getConnection(cadena, "adminsql", "Admin123");
        return cn;
    }

    public List<Departamento> getDepartamentos() throws SQLException {
        Connection cn = this.getConnection();
        String sql = "select * from dept";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ArrayList<Departamento> departamentos = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("DEPT_NO");
            String nombre = rs.getString("DNOMBRE");
            String localidad = rs.getString("LOC");
            Departamento dept = new Departamento(id, nombre, localidad);
            departamentos.add(dept);
        }
        rs.close();
        cn.close();
        return departamentos;
    }

    public Departamento findDepartamento(int id) throws SQLException {
        Connection cn = this.getConnection();
        String sql = "select * from dept where dept_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int iddept = rs.getInt("DEPT_NO");
            String nombre = rs.getString("DNOMBRE");
            String localidad = rs.getString("LOC");
            Departamento dept = new Departamento(iddept, nombre, localidad);
            rs.close();
            cn.close();
            return dept;
        } else {
            rs.close();
            cn.close();
            return null;
        }
    }

    public void insertarDepartamento(int id, String nombre, String localidad) throws SQLException {
        Connection cn = this.getConnection();
        String sql = "insert into dept values (?,?,?)";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, id);
        pst.setString(2, nombre);
        pst.setString(3, localidad);
        pst.executeUpdate();
        cn.close();
    }

    public void modificarDepartamento(int id, String nombre, String localidad) throws SQLException {
        Connection cn = this.getConnection();
        System.out.println("Modificar repo: " + id + " " + nombre
                + " " + localidad);
        String sql = "update dept set dnombre=?, loc=? where dept_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setString(1, nombre);
        pst.setString(2, localidad);
        pst.setInt(3, id);
        pst.executeUpdate();
        cn.close();
    }

    public void eliminarDepartamento(int id) throws SQLException {
        Connection cn = this.getConnection();
        String sql = "delete from dept where dept_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
        cn.close();
    }
}
