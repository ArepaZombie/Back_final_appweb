package pe.edu.cibertec.BackProyectoFinalApps.service;

import pe.edu.cibertec.BackProyectoFinalApps.dto.LoginRequestDTO;
import pe.edu.cibertec.BackProyectoFinalApps.dto.LogoutRequestDTO;

import java.io.IOException;
import java.util.List;

public interface AutenticacionService {

    String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException;
    String[] logout(LogoutRequestDTO logoutRequestDTO) throws IOException;

    // Nuevo método para mostrar la lista de integrantes

    List<String> listarIntegrantes() throws IOException;
}
