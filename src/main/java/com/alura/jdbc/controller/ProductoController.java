package com.alura.jdbc.controller;
//package com.alura.jdbc.controller;

/*
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.Statement;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
*/
import java.util.List;
import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	public ProductoController() {
		// TODO Auto-generated constructor stub
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}
	
	
	/*¿Cuál es el riesgo de utilizar un Statement en lugar del PreparedStatement?
	 * El Statement no mantiene una versión de la query compilada en la base de datos.
	 * El PreparedStatement mantiene la query compilada en la base de datos, de forma parametrizada. 
	 * Así el usuario puede ejecutar la misma consulta diversas veces con parámetros distintos.
	 * 
	 */

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) { // throws SQLException{
		/*
		// TODO
		ConnectionFactory factory = new ConnectionFactory();
	    final Connection con = factory.recuperaConexion();
		
	    try(con){
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
		    
		/*
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
	    }
		*/
		return productoDAO.modificar(nombre, descripcion, cantidad, id);
	}

	public int eliminar(Integer id) { // throws SQLException{
		/*
		// TODO
		ConnectionFactory factory = new ConnectionFactory();
	    final Connection con = factory.recuperaConexion();
		
	    try(con){
			/* ¿Para qué sirve el retorno del método execute de la interfaz 
			 * java.sql.Statement?
			 * El método devuelve true cuando el resultado devuelve un 
			 * java.sql.ResultSet (resultado de un SELECT) y false cuando 
			 * el resultado no devuelve contenido (resultado de un DELETE, 
			 * UPDATE o DELETE).
			 */
			/*
			//Statement statement = con.createStatement();
			final PreparedStatement statement = con.prepareStatement("delete from producto where id = ?;");
			
			try(statement){
				statement.setInt(1, id);
				statement.execute();
				
				int updateCount = statement.getUpdateCount();
			    // con.close();
		
			    return updateCount;
			}
	    }
		*/
	    
	    return productoDAO.eliminar(id);
	}

	public List<Producto> listar(){ // throws SQLException{
	//public List<Map<String, String>> listar(){ // throws SQLException{
		// TODO
		//java.sql.Connection, java.sql.Statement y java.sql.ResultSet son interfaces.
		
		/* // Aplico el uso de DAO
		final Connection con = new ConnectionFactory().recuperaConexion();

		try(con){
			// Statement statement = con.createStatement();
			final PreparedStatement statement = con.prepareStatement("select id, nombre, descripcion, cantidad from producto;");
			try(statement){
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				
				List<Map<String, String>> resultado = new ArrayList<>();
				while(resultSet.next()) {
					Map<String, String> fila = new HashMap<>();
					fila.put("id", String.valueOf(resultSet.getInt("id")));
					fila.put("nombre", resultSet.getString("nombre"));
					fila.put("descripcion", resultSet.getString("descripcion"));
					fila.put("cantidad", String.valueOf(resultSet.getInt("cantidad")));
					resultado.add(fila);
				}
				//con.close(); // ya no es necesario cerrar		
				return resultado; 
			}		
			
		}
		*/
		return productoDAO.listar();		
	}
	
	public List<Producto> listar(Categoria categoria){		
		return productoDAO.listar(categoria.getId());
	}

	public void guardar(Producto producto, Integer categoriaId) { //throws SQLException {
	
		/* // Dejo de usar toda esta logica para usar otra mas simple
    //public void guardar(Map<String, String> producto) throws SQLException {
		// TODO
		ConnectionFactory factory = new ConnectionFactory();
        final Connection con = factory.recuperaConexion();
		
		/* // Esto era para el map que habia armado
    	String nombre = producto.get("nombre");
    	String descripcion = producto.get("descripcion");
    	Integer cantidad = Integer.valueOf(producto.get("cantidad"));
    	*/
		// Quito la logica de la cantidad maxima por producto
    	//Integer maximoCantidad = 50;
        
        /* // Quito el extraer valores para trabajar directamente con el producto 
        String nombre = producto.getNombre();
    	String descripcion = producto.getDescripcion();
    	Integer cantidad = producto.getCantidad();
    	*/
        
		/*
        try(con){
	        // tomo el control de la transaccion, para que la operacion quede dentro de una sola transaccion
	        // para cuando ocurra un error o se guarde todo o no se guarde nada
	        con.setAutoCommit(false);
	        
	        /*¿Cuál es el estándar de JDBC (del driver) para manejar transacciones de base de datos?
	         * Auto-Commit.  Este es el estándar, que puede ser modificado por el método setAutoCommit, 
	         * de la interfaz Connection. */
	        
	    	// Statement statement = con.createStatement();
		/*
	    	final PreparedStatement statement = con.prepareStatement("insert into producto(nombre, descripcion, cantidad) "
	    			+ "values(?, ? , ?);", Statement.RETURN_GENERATED_KEYS);
	    	
	    	try(statement){
		    	// si dentro del try ocurre un error del que sea, con rollback vuelvo a dejar todo como estaba
		    	// se cancela la transaccion
		    	// try { este se puede quitar porque ya esta el de arriba con el statement
			    	// do {
			    		//int cantidadGuardar = Math.min(cantidad, maximoCantidad);
			    		//ejecutaRegistro(nombre, descripcion, cantidadGuardar, statement);
			    		ejecutaRegistro(producto, statement);
			    		//cantidad -= maximoCantidad;
			    	// }while(cantidad > 0);
			    	
			    	// cuando seteo Auto commit false tengo que poner esto para que los datos se guarden
			    	// si sale todo bien tengo un coomit y si sale mal un rollback
			    	con.commit();
			    	System.out.println("Transaccion SQL realizada con exito");
		    	}catch(Exception e) {    		
		    		con.rollback();
		    		System.out.println("Fallo en la transaccion SQL, se ejecuto un Rollback");
		    }
	    	
	    	
	    	// con los 2 final de conecxion y statemente yo no es necesario esto
	    	// statement.close();
	    	// con.close();
        }
        */
		
		// Comente todo la funcionalidad que usaba antes para usar esto
		// ProductoDAO productoDAO  = new ProductoDAO(new ConnectionFactory().recuperaConexion());
		producto.setCategoriaId(categoriaId);
		productoDAO.guardar(producto);
	}

	 
	/* // Comento esta funcion porque voy a trabajar con otra clase
	private void ejecutaRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
	//private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement statement)
	//		throws SQLException {
		
		/* // Antes cuando pasaba cata dato por parametro
		statement.setString(1, nombre);    	
		statement.setString(2, descripcion);    	
		statement.setInt(3, cantidad);
		*/ 
		
	/*  // una forma mejor de trabajar
		statement.setString(1, producto.getNombre());    	
		statement.setString(2, producto.getDescripcion());    	
		statement.setInt(3, producto.getCantidad());
		
		statement.execute();
	*/
		
		/* // lanza una excepcion para simular un error y probar las transacciones
		if(cantidad < 50) {
			throw new RuntimeException("Ocurrio un error");
		}		
		*/

    	// autocloseable en la version de java 7
    	/*
    	try (ResultSet resultSet = statement.getGeneratedKeys();){	    	   	
	    	while(resultSet.next()) {
	    		System.out.println(
	    				String.format(
	    						"Fue insertado el producto de ID %d", resultSet.getInt(1)));     		
	    	}
	    	// esultSet.close(); // esto no hace falta si se aplica autocloseable
    	}
    	
    	¿Por qué cuando utilizamos el try-with-resources no hay más la necesidad de explicitar el 
    	comando close para cerrar los recursos (PreparedStatement, Connection, PreparedStatement)?
    	Por el hecho de que estos recursos extienden la interfaz AutoCloseable.
    	Como estas interfaces extienden la interfaz AutoCloseable, el try-with-resources ejecuta el 
    	comando close implícitamente.
    	*/    	
    	
	/*
    	// autocloseable en la version de java 9
    	final ResultSet resultSet = statement.getGeneratedKeys();
    	try (resultSet){	    	   	
	    	while(resultSet.next()) {
	    		producto.setId(resultSet.getInt(1));
	    		System.out.println(
	    				//String.format(
	    				//		"Fue insertado el producto de ID %d", resultSet.getInt(1)));
	    				String.format(
	    						"Fue insertado el producto %s", producto.toString()));     		
	    	}
	    	// esultSet.close(); // esto no hace falta si se aplica autocloseable
    	}    	
	}
	*/

}
