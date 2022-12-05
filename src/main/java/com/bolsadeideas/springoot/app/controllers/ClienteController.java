package com.bolsadeideas.springoot.app.controllers;

import com.bolsadeideas.springoot.app.models.entity.Cliente;
import com.bolsadeideas.springoot.app.services.ClienteServiceImpl;
import com.bolsadeideas.springoot.app.services.IClienteService;
import com.bolsadeideas.springoot.app.services.IUploadFileService;
import com.bolsadeideas.springoot.app.services.UploadFileServiceImpl;
import com.bolsadeideas.springoot.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@SessionAttributes("cliente")
public class ClienteController {


    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IUploadFileService fileService;

    @GetMapping("/listar")
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Cliente> clientes = clienteService.listarClientes(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", pageRender);
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Cliente cliente, Model model) {
        model.addAttribute("titulo", "Formulario de cliente");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid Cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile foto,
                           RedirectAttributes flash, SessionStatus status) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Clientes");
            return "form";
        }
        if (!foto.isEmpty()) {
            if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null && cliente.getFoto().length() > 0) {
                fileService.delete(cliente.getFoto());
            }
            fileService.load(foto, cliente);

        }
        String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con éxito" : "Cliente creado con Exito";
        clienteService.guardarCliente(cliente);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable(value = "id") Cliente cliente, Model model, RedirectAttributes flash) {
        if (cliente.getId() > 0) {
            cliente = clienteService.buscarClientePorId(cliente);
            if (cliente == null) {
                flash.addAttribute("error", "El ID del cliente no existe");
            }
        } else {
            flash.addFlashAttribute("error", "El ID del cliente no puede ser cero");
            return "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Cliente cliente, Model model, RedirectAttributes flash) {
        if (cliente.getId() > 0) {
            cliente = clienteService.buscarClientePorId(cliente);
            clienteService.eliminar(cliente);
            flash.addFlashAttribute("success", "Cliente eliminado con éxito");
            if (fileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito");
            }
        }
        return "redirect:/listar";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Cliente cliente, Model model, RedirectAttributes flash) {
        cliente = clienteService.buscarClientePorId(cliente);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
        return "ver";
    }
}
