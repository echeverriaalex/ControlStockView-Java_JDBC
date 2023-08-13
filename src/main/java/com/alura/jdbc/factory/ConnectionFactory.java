package com.alura.jdbc.factory;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/*¿Cuál es la ventaja de utilizar una ConnectionFactory en nuestra aplicación?
 * Proveer una forma más sencilla de crear un objeto. Los objetos son creados 
 * sin exponer la lógica o las configuraciones de creación al cliente. 
 * Además, es posible referirnos al objeto recién creado utilizando una 
 * interfaz (una abstracción), desacoplando la implementación.
 * 
 */


/*En un escenario donde múltiples clientes pueden acceder a una misma aplicación simultáneamente. 
 * ¿Cuál sería el mejor abordaje?
 * Reutilizar un conjunto de conexiones de tamaño fijo o dinámico.
 * Esta es la estratégia de mantener un pool de conexiones. Vamos a abrir una 
 * cantidad específica de conexiones y reutilizarlas.
 */

/*En un pool de conexiones con 9 conexiones disponibles. Si todas las 9 conexiones están ocupadas y 
 * entra una décima requisición, ¿el usuario logra conectarse?
 * El décimo usuario va a esperar que una de las 9 conexiones se libere.
 * En el momento que una conexión quede disponible, el décimo cliente va a 
 * tener su requisición procesada.
 */


public class ConnectionFactory {
	
	private DataSource datasource;
	
	public ConnectionFactory() {
		var  pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("");
		
		this.datasource = pooledDataSource;
	}
	
	public Connection recuperaConexion() { // throws SQLException {
		/*
		return DriverManager.getConnection(
				"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC", 
				"root",
				""); // como no tengo contraseña, dejo este campo vacio
		*/
		
		try {
			return this.datasource.getConnection();			
		}catch (SQLException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}		
	}

}
