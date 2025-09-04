package br.com.projeto_med.adm_med.exception;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ErrorResponse error = new ErrorResponse(
                HttpServletResponse.SC_FORBIDDEN,
                "Acesso negado",
                "Você não tem permissão para acessar este recurso"
        );

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        mapper.writeValue(response.getOutputStream(), error); // 👈 retorna objeto como JSON
    }
}
