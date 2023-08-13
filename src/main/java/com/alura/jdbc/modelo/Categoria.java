package com.alura.jdbc.modelo;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
	
	private Integer id;
	private String nombre;
	private List<Producto> productos;
	
	public Categoria(Integer id, String nombre) {
		this.setId(id);
		this.setNombre(nombre);
	}
	
	public Categoria( String nombre) {
		//this.setId(id);
		this.setNombre(nombre);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	// para que lo muestre bien en el formulario
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getNombre();
	}

	public void agregar(Producto producto) {
		// TODO Auto-generated method stub
		if(this.productos == null) {
			this.productos = new ArrayList<>();
		}
		
		this.productos.add(producto);
		
	}

	public List<Producto> getProductos() {
		// TODO Auto-generated method stub
		return this.productos;
	}
}