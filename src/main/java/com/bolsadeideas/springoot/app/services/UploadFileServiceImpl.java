package com.bolsadeideas.springoot.app.services;

import com.bolsadeideas.springoot.app.models.entity.Cliente;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private static final String pathImagen = "C://Temp//uploads";

    public Path getPath(String filename) {

        return Paths.get(pathImagen).resolve(filename).toAbsolutePath();
    }

    @Override
    public Resource load(MultipartFile foto, Cliente cliente) {
        byte[] bytes = new byte[0];
        try {
            bytes = foto.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Path rutaCompleta = Paths.get(pathImagen + "//" + foto.getOriginalFilename());
        try {
            Files.write(rutaCompleta, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cliente.setFoto(foto.getOriginalFilename());
        return null;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();
        if (archivo.exists() && archivo.canRead()) {
            if (archivo.delete()) {
                return true;
            }
        }
        return false;
    }
}
