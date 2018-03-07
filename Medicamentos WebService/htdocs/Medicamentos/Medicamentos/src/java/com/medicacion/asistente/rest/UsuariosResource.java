/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medicacion.asistente.rest;

import com.google.gson.Gson;
import com.medicacion.asistente.bd.UsuarioBD;
import com.medicacion.asistente.pojos.Usuario;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Juanjo
 */
@Path("usuarios")
public class UsuariosResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UsuariosResource
     */
    public UsuariosResource() {
    }

    /**
     * Retrieves representation of an instance of com.medicacion.asistente.UsuariosResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("pruebaconexion")
    @Produces(MediaType.TEXT_PLAIN)
    public String getXml() {
        return "Conectado";
    }
    
    /**
     * Retrieves representation of an instance of com.medicacion.asistente.UsuariosResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsuarios() {
        
        List<Usuario> usuarios;
        try {
            usuarios = UsuarioBD.getAllUsuarios();
            
            String json = new Gson().toJson(usuarios);
        
            return Response.ok(json,MediaType.APPLICATION_JSON).build();
            
        } catch (Exception ex) {
            return Response.status(Response.Status.SEE_OTHER).entity("Error al consultar la tabla de datos").build();
        }
    }

    // Añadir registro
    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response addUsuario(Usuario registro){
        
        try{
            UsuarioBD.addUsuario(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString()+": Se ha añadido - addUsuario "+registro.getPK_ID());
            String json = "{ \"id\": \"" + String.valueOf(registro.getPK_ID())+"\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        
        } catch (Exception e){
            return Response.status(Response.Status.SEE_OTHER).entity("Error al insertar registro: "+registro.getPK_ID()).build();
        }
    }
    
    // Actualizar registro
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuario(Usuario registro, @PathParam("id") int id) {
        System.out.println("Llegó a la actualización");
        
        try{
            UsuarioBD.updateUsuario(registro);
            Date fecha = new Date();
            System.out.println(fecha.toString()+": Se ha actualizado - updateUsuario "+registro.getPK_ID());
            String json = "{ \"id\": \"" + String.valueOf(registro.getPK_ID())+"\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        
        } catch (Exception e){
            return Response.status(Response.Status.SEE_OTHER).entity("Error al insertar registro: "+registro.getPK_ID()).build();
        }
    }
    
    // Borrar registro
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delUsuario(@PathParam("id") int id){
        try{
            UsuarioBD.delUsuario(id);
            Date fecha = new Date();
            System.out.println(fecha.toString()+": Se ha borrado - delUsuario "+id);
            String json = "{ \"id\": \"" +id+"\" }";
            return Response.ok(json, MediaType.APPLICATION_JSON).build();
        
        } catch (Exception e){
            return Response.status(Response.Status.SEE_OTHER).entity("Error al borrar registro: "+id).build();
        }
    }
}
