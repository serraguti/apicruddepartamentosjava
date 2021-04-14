package services;

import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Departamento;
import repositories.RepositoryDepartamentos;

@Path("/departamentos")
public class ServiceDepartamentos {

    RepositoryDepartamentos repo;

    public ServiceDepartamentos() {
        this.repo = new RepositoryDepartamentos();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDepartamentos() throws SQLException {
        List<Departamento> departamentos = this.repo.getDepartamentos();
        Gson converter = new Gson();
        String json = converter.toJson(departamentos);
        return json;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public String findDepartamento(@PathParam("id") String id) throws SQLException {
        int iddept = Integer.parseInt(id);
        Departamento dept = this.repo.findDepartamento(iddept);
        Gson converter = new Gson();
        String json = converter.toJson(dept);
        return json;
    }

    //METODO PARA INSERTAR (POST)
    //TENEMOS DOS FORMAS DE REALIZAR LA ACCION PARA INSERTAR:
    //1) ENVIAR LOS PARAMETROS UNO A UNO
    //@Path("/post/{iddept}/{nombre}/{localidad}")
    //2) ENVIAR EL OBJETO COMPLETO EN FORMATO JSON
    //@Path("/post") { idDepartamento: 10, dnombre: "VALOR", localidad: "LOCALIDAD" }
    //CUANDO HACEMOS POST, PUT O DELETE, NO SE DEVUELVE NADA
    //PERO TENEMOS QUE ENVIAR INFORMACION AL SERVIDOR PARA SABER SI
    //SE HA REALIZADO CORRECTAMENTE (success) 200
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/post")
    public Response insertarDepartamento(String deptjson) throws SQLException {
        Gson converter = new Gson();
        //DEBEMOS CONVERTIR EL STRING JSON A LA CLASE DEPARTAMENTO
        //LO QUE SE LLAMA DESERIALIZAR
        Departamento dept
                = converter.fromJson(deptjson, Departamento.class);
        //CON EL DEPARTAMENTO, YA TENEMOS LOS DATOS E INSERTAMOS
        this.repo.insertarDepartamento(dept.getIdDepartamento(), dept.getNombre(),
                dept.getLocalidad());
        //DEVOLVEMOS UNA RESPUESTA CORRECTA
        return Response.status(200).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/put")
    public Response modificarDepartamento(String deptjson) throws SQLException {
        System.out.println("Modificar: " + deptjson);
        Gson converter = new Gson();
        Departamento dept
                = converter.fromJson(deptjson, Departamento.class);
        this.repo.modificarDepartamento(dept.getIdDepartamento(),
                dept.getNombre(), dept.getLocalidad());
        return Response.status(200).build();
    }

    @DELETE
    @Path("/delete/{id}")
    public Response eliminarDepartamento(@PathParam("id") String id) throws SQLException {
        int iddept = Integer.parseInt(id);
        this.repo.eliminarDepartamento(iddept);
        return Response.status(200).build();
    }
}
