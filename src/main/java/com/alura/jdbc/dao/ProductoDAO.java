package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
// import java.util.Map;
import java.util.ArrayList;
// import java.util.HashMap;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

/*¿Cuáles son las ventajas de utilizar clases con el estándar DAO?
 * Tiene que ver con la capacidad de aislar en un lugar centralizado, 
 * toda la lógica de acceso al repositorio de datos de la entidad.
 * Así estaremos evitando duplicación de código y centralización de la lógica.
 * 
 */

// Esta clase seria Data Access Object (DAO) y cambio el nombre de la clase 
// de persistenciaProducto a Producto DAO, dao es un patron de diseño
// DAO tiene la responsabilidad de unicamente manipular la base de datos sobre una entidad
public class ProductoDAO {
// public class PersistenciaProducto {
	
	final private Connection con; // lo declaro final con this sale error
	
	public ProductoDAO(Connection con) {
		// TODO Auto-generated constructor stub
		this.con = con;
	}
	
	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) { //throws SQLException{
		// TODO
		//ConnectionFactory factory = new ConnectionFactory();
	    //final Connection con = factory.recuperaConexion();
		
	    try {
	    	final PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
					    + " NOMBRE = ?, "  + " DESCRIPCION = ?, " + " CANTIDAD = ? " + " WHERE ID = ?;");
			try(statement){
					statement.setString(1, nombre);
			    	statement.setString(2, descripcion);
			    	statement.setInt(3, cantidad);
			    	statement.setInt(4, id);
			    	statement.execute();		
			    	
					int updateCount = statement.getUpdateCount();
				    // con.close();
				    return updateCount;
			    }
			//try(con){
				/* // haciendolo de esta forma se puede inyectar script sql que vulneran al sistema, como delete from produtos;
				Statement statement = con.createStatement();		
				statement.execute("UPDATE PRODUCTO SET "
					    + " NOMBRE = '" + nombre + "'"
					    + ", DESCRIPCION = '" + descripcion + "'"
					    + ", CANTIDAD = " + cantidad
					    + "WHERE ID = " + id);
				*/
				
				// de esta forma se evita la inyeccion de querys sql como delete 
				// si se introduce una query, este va a ser todamado como texto para el producto en este caso
			    
			    // System.out.println("UPDATE PRODUCTO SET "
				//	    + " NOMBRE = ?, "  + " DESCRIPCION = ?, "  + " CANTIDAD = ? " + " WHERE ID = ?;");
			    
			//}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	
	public int eliminar(int id) {
		
		//ConnectionFactory factory = new ConnectionFactory();
	    //final Connection con = factory.recuperaConexion();
		
	    try {
	    	final PreparedStatement statement = con.prepareStatement("delete from producto where id = ?;");
			
	    	try(statement){
				statement.setInt(1, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
			    // con.close();
		
			    return updateCount;
			}
	    	
	    	//try(con){
				/* ¿Para qué sirve el retorno del método execute de la interfaz 
				 * java.sql.Statement?
				 * El método devuelve true cuando el resultado devuelve un 
				 * java.sql.ResultSet (resultado de un SELECT) y false cuando 
				 * el resultado no devuelve contenido (resultado de un DELETE, 
				 * UPDATE o DELETE).
				 */
				
				//Statement statement = con.createStatement();
			//}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		
	}
	
	public List<Producto> listar(){ 
	//public List<Map<String, String>> listar(){ // throws SQLException{
		// TODO
		//java.sql.Connection, java.sql.Statement y java.sql.ResultSet son interfaces.
		
		List<Producto> resultado = new ArrayList<>();
		// Aplico el uso de DAO
		final Connection con = new ConnectionFactory().recuperaConexion();	

		try(con){
			// Statement statement = con.createStatement();
			final PreparedStatement statement = con.prepareStatement("select id, nombre, descripcion, cantidad from producto;");
			try(statement){
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				
				
				while(resultSet.next()) {
					/*
					Map<String, String> fila = new HashMap<>();
					fila.put("id", String.valueOf(resultSet.getInt("id")));
					fila.put("nombre", resultSet.getString("nombre"));
					fila.put("descripcion", resultSet.getString("descripcion"));
					fila.put("cantidad", String.valueOf(resultSet.getInt("cantidad")));
					resultado.add(fila);
					*/
					
					/* // de esta manera
					Producto producto = new Producto();
					producto.setCantidad(resultSet.getInt("cantidad"));
					producto.setId(resultSet.getInt("id"));
					producto.setDescripcion(resultSet.getString("descripcion"));
					producto.setNombre(resultSet.getString("nombre"));
					*/
					
					// o tambien usando el constructor
					Producto producto = 
							new Producto(resultSet.getString("nombre"),
										resultSet.getString("descripcion"),
										resultSet.getInt("cantidad"),
										resultSet.getInt("id")
							);
					resultado.add(producto);
				}
				//con.close(); // ya no es necesario cerrar		
				
			}
			return resultado;
			
		}catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	public void guardar(Producto producto){ // throws SQLException {
		/* No uso mas en casa funcion crear un objeto nuevo
		 * ahora uso del atributo de la clase en cada uno de las funciones
		ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();
        */
        try {
			try(con){
				//con.setAutoCommit(false);
				final PreparedStatement statement = con.prepareStatement("insert into producto(nombre, descripcion, cantidad, categoria_id) "
						+ "values(?, ? , ?, ?);", Statement.RETURN_GENERATED_KEYS);
				
				try(statement){
					ejecutaRegistro(producto, statement);
					//con.commit();
				    	System.out.println("Transaccion SQL realizada con exito");
			    }catch(SQLException e) {    		
			    		//con.rollback();
			    		System.out.println("Fallo en la transaccion SQL, se ejecuto un Rollback");
			    		throw new RuntimeException(e);
			    }
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ejecutaRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
		statement.setString(1, producto.getNombre());    	
		statement.setString(2, producto.getDescripcion());    	
		statement.setInt(3, producto.getCantidad());
		statement.setInt(4, producto.getCategoriaId());
		
		statement.execute();
    	final ResultSet resultSet = statement.getGeneratedKeys();
    	try (resultSet){	    	   	
	    	while(resultSet.next()) {
	    		producto.setId(resultSet.getInt(1));
	    		System.out.println(
	    				String.format(
	    						"Fue insertado el producto %s", producto.toString()));     		
	    	}
    	}    	
	}

	public List<Producto> listar(Integer categoriaId) {
		List<Producto> resultado = new ArrayList<>();
		final Connection con = new ConnectionFactory().recuperaConexion();	

		try(con){
			var querySelect = "select id, nombre, descripcion, cantidad "
							+ " from producto where categoria_id = ?;";
			
			System.out.println(querySelect);
			
			final PreparedStatement statement = 
					con.prepareStatement(querySelect);
			try(statement){
				statement.setInt(1, categoriaId);
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				
				while(resultSet.next()) {
					Producto producto = 
							new Producto(resultSet.getString("nombre"),
										resultSet.getString("descripcion"),
										resultSet.getInt("cantidad"),
										resultSet.getInt("id")
							);
					resultado.add(producto);
				}
			}
			return resultado;
			
		}catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
}