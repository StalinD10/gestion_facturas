package com.bolsadeideas.springoot.app.services;

import com.bolsadeideas.springoot.app.models.dao.IClienteDao;
import com.bolsadeideas.springoot.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;

    @Override
    public List<Cliente> listarClientes() {
        return (List<Cliente>) clienteDao.findAll();
    }

    @Override
    public Page<Cliente> listarClientes(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Override
    public Cliente buscarClientePorId(Cliente cliente) {
        return (Cliente) clienteDao.findById(cliente.getId()).orElse(null);
    }

    @Transactional
    @Override
    public void guardarCliente(Cliente cliente) {
        clienteDao.save(cliente);
    }

    @Transactional
    @Override
    public void eliminar(Cliente cliente) {
        clienteDao.delete(cliente);
    }
}
