package com.bolsadeideas.springoot.app.services;

import com.bolsadeideas.springoot.app.models.entity.Cliente;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

    public Resource load(MultipartFile foto, Cliente cliente);

    public boolean delete(String filename);

}
