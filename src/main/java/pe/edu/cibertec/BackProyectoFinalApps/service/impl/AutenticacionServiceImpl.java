package pe.edu.cibertec.BackProyectoFinalApps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.BackProyectoFinalApps.service.AutenticacionService;
import pe.edu.cibertec.BackProyectoFinalApps.dto.LoginRequestDTO;
import pe.edu.cibertec.BackProyectoFinalApps.dto.LogoutRequestDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException {
        String[] datosUsuario = null;
        Resource resource = resourceLoader.getResource("classpath:usuarios.txt");

        // Use InputStream to avoid issues when running from a JAR
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {

            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");
                // Adjusting the indices according to the data format in the file
                if (loginRequestDTO.codigoIntegrante().equals(datos[0]) &&
                        loginRequestDTO.password().equals(datos[1])) {

                    datosUsuario = new String[4];
                    datosUsuario[0] = datos[2]; // Nombre
                    datosUsuario[1] = datos[3]; // Correo
                    datosUsuario[2] = datos[0]; // Codigo Integrante
                    datosUsuario[3] = datos[1]; // Password
                    break;
                }
            }

        } catch (IOException e) {
            throw new IOException("Error reading usuarios.txt", e);
        }

        return datosUsuario;
    }

    @Override
    public String[] logout(LogoutRequestDTO logoutRequestDTO) throws IOException {

        String[] datosUsuario = new String[3];
        datosUsuario[0] = logoutRequestDTO.codigoIntegrante();
        datosUsuario[1] = LocalDateTime.now().toString();

        File archivo = new File("src/main/resources/logoutlog.txt");

        // Create the file if it doesn't exist
        if (!archivo.exists()) {
            archivo.createNewFile();
        }

        try (BufferedWriter bf = new BufferedWriter(new FileWriter(archivo, true))) {
            String linea = logoutRequestDTO.codigoIntegrante() + ";" + datosUsuario[1] + "\n";
            bf.write(linea);
        } catch (IOException e) {
            throw new IOException("Error writing to logoutlog.txt", e);
        }

        return datosUsuario;
    }

    @Override
    public List<String> listarIntegrantes() throws IOException {
        List<String> integrantes = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:integrantes.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(";");

                // Asegúrate de que haya al menos dos elementos en datos
                if (datos.length >= 2) {
                    integrantes.add(datos[0] + " - " + datos[1]); // Agrega el código y el nombre
                }
            }
        } catch (IOException e) {
            throw new IOException("Error reading integrantes.txt", e);
        }

        return integrantes;
    }

}
