package com.alura.jdbc.modelo;

public class Producto {
	
	private Integer id;
	private String nombre;
	private String descripcion;
	private Integer cantidad;
	private Integer categoriaId;
	
	public Producto() {
		// TODO Auto-generated constructor stub
		this.setCantidad(0);
		this.setDescripcion("");
		//this.setId(0);
		this.setNombre("");
	}
	
	public Producto(String nombre, String descripcion, Integer cantidad) {
		// TODO Auto-generated constructor stub
		this.setCantidad(cantidad);
		this.setDescripcion(descripcion);
		//this.setId(id);
		this.setNombre(nombre);
	}
	
	public Producto(String nombre, String descripcion, Integer cantidad, Integer id) {
		// TODO Auto-generated constructor stub
		this.setCantidad(cantidad);
		this.setDescripcion(descripcion);
		this.setId(id);
		this.setNombre(nombre);
	}
	
	
	
	public Producto(int id, String nombre, int cantidad) {
		// TODO Auto-generated constructor stub
		this.setCantidad(cantidad);
		this.setId(id);
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
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format(
				"{id: %s, nombre: %s, descripcion: %s, cantidad: %d", 
				this.getId(),
				this.getNombre(),
				this.getDescripcion(),
				this.getCantidad());
	}

	

}
