package pe.edu.cibertec.BackProyectoFinalApps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.BackProyectoFinalApps.dto.LoginRequestDTO;
import pe.edu.cibertec.BackProyectoFinalApps.dto.LoginResponseDTO;
import pe.edu.cibertec.BackProyectoFinalApps.dto.LogoutRequestDTO;
import pe.edu.cibertec.BackProyectoFinalApps.dto.LogoutResponseDTO;
import pe.edu.cibertec.BackProyectoFinalApps.service.AutenticacionService;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    AutenticacionService autenticacionService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {

        try {
            //Thread.sleep(Duration.ofSeconds(5));
            String[] datosUsuario = autenticacionService.validarUsuario(loginRequestDTO);
            System.out.println("Resultado: " + Arrays.toString(datosUsuario));
            if (datosUsuario == null) {
                return new LoginResponseDTO("01", "Error: Usuario no encontrado", "", "","");
            }
            return new LoginResponseDTO("00", "", datosUsuario[0], datosUsuario[1],datosUsuario[2]);

        } catch (Exception e) {
            return new LoginResponseDTO("99", "Error: Ocurrió un problema", "", "","");
        }

    }

    @PostMapping("/logout")
    public LogoutResponseDTO cerrarSesion(@RequestBody LogoutRequestDTO logoutRequestDTO) {
        try {
            Thread.sleep(Duration.ofSeconds(1));
            String[] datosUsuario = autenticacionService.logout(logoutRequestDTO);
            System.out.println("Resultado: " + Arrays.toString(datosUsuario));
            if (datosUsuario == null) {
                return new LogoutResponseDTO("01", "Error: Usuario no encontrado");
            }
            return new LogoutResponseDTO("00", "Cierre de sesión exitoso");
        } catch (Exception e) {
            return new LogoutResponseDTO("99", "Error al registrar cierre de sesión");
        }
    }

    @GetMapping("/listar-integrantes")
    public ResponseEntity<List<String>> listarIntegrantes() {
        try {
            List<String> integrantes = autenticacionService.listarIntegrantes();
            return ResponseEntity.ok(integrantes);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de integrantes: " + e.getMessage());
            // Retornamos un estado 500 (Internal Server Error) con un mensaje de error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of("Error al obtener la lista de integrantes."));
        }
    }





}
