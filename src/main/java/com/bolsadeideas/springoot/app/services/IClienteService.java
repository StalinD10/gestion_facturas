package com.bolsadeideas.springoot.app.services;

import com.bolsadeideas.springoot.app.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    public List<Cliente> listarClientes();

    public Page<Cliente> listarClientes(Pageable pageable);

    public Cliente buscarClientePorId(Cliente cliente);

    public void guardarCliente(Cliente cliente);

    public void eliminar(Cliente cliente);
}
