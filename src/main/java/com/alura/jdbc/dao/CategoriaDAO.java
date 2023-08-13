package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class CategoriaDAO {
	
	private Connection con;
	
	/* ¿Cuál es el problema de la aplicación tener queries N + 1?
	 * Porque son utilizadas múltiples queries, aumentando la cantidad 
	 * de acceso a la base de datos y, por consecuencia, empeorando la 
	 * performance de la aplicación y del propio sistema de base de datos.
	 * Cuando las consultas son sencillas no hay problemas. Pero cuanto 
	 * más complejidad van teniendo nuestras consultas hay la necesidad 
	 * de buscar más informaciones de múltiples tablas, aumentando el 
	 * acceso exponencialmente. Eso impacta gravemente la performance 
	 * de la aplicación y del sistema de base de datos.
	 * 
	 */
	
	public CategoriaDAO(Connection con) {
		this.con = con;
	}
	
	public List<Categoria> listar(){
		List<Categoria> resultado = new ArrayList<>();
		
		try {
			var querySelect =  "select id, nombre from categoria;";
			System.out.println(querySelect);
			
			final PreparedStatement statement = con.prepareStatement(
					querySelect);
			
			try(statement){
				final ResultSet resultSet = statement.executeQuery();
				
				try(resultSet){
					while(resultSet.next()) {
						var categoria = new Categoria(resultSet.getInt("id"), resultSet.getString("nombre"));
						resultado.add(categoria);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return resultado;
	}

	public List<Categoria> listarConProductos() {
		// TODO Auto-generated method stub
		List<Categoria> resultado = new ArrayList<>();
				
		try {
			var querySelect = "select c.id, c.nombre, p.id, p.nombre, p.cantidad from categoria c " + 
					" inner join producto p on c.id = p.categoria_id;";
			System.out.println(querySelect);
			
			final PreparedStatement statement = con.prepareStatement(
					querySelect);
			
			try(statement){
				final ResultSet resultSet = statement.executeQuery();
				
				try(resultSet){
					while(resultSet.next()) {
						//var categoria = new Categoria(resultSet.getInt("id"), resultSet.getString("nombre"));
						Integer categoriaId = resultSet.getInt("id");
						String categoriaNombre = resultSet.getString("nombre");
						
						var categoria = resultado
							.stream()
							.filter(cat -> cat.getId().equals(categoriaId))
							.findAny().orElseGet(()->{
								Categoria cat = new Categoria(categoriaId, categoriaNombre);
								resultado.add(cat);
								return cat;
							});
						//resultado.add(categoria);
						
						Producto producto = new Producto(resultSet.getInt("p.id"),
								resultSet.getString("p.nombre"), resultSet.getInt("p.cantidad"));
						
						categoria.agregar(producto);
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return resultado;
	}
}
