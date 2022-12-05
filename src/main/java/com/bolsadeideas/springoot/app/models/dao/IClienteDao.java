package com.bolsadeideas.springoot.app.models.dao;

import com.bolsadeideas.springoot.app.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;


public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

}
