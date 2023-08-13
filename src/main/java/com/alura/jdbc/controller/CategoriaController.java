package com.alura.jdbc.controller;

// import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

public class CategoriaController {
	
	private CategoriaDAO categoriaDAO;
	
	public CategoriaController() {
		// TODO Auto-generated constructor stub
		var factory = new ConnectionFactory();
		//this.setCategoriaDAO(new CategoriaDAO(factory.recuperaConexion()));
		this.categoriaDAO = new CategoriaDAO(factory.recuperaConexion());
	}

	public List<Categoria> listar() {
		// TODO
		return categoriaDAO.listar();
	}

    public List<Categoria> cargaReporte() {
        // TODO
        // return this.listar();
    	return this.categoriaDAO.listarConProductos();
    }

	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}

}
