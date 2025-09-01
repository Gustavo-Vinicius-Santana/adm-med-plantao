package br.com.projeto_med.adm_med.security;

import br.com.projeto_med.adm_med.model.Usuario;
import br.com.projeto_med.adm_med.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    // Lista de rotas públicas organizadas
    private static final List<PublicRoute> PUBLIC_ROUTES = List.of(
            new PublicRoute("/", "ALL"), // Rota raiz - todos os métodos
            new PublicRoute("/auth", "ALL"), // Todas as rotas de auth
            new PublicRoute("/usuarios", "POST") // Apenas POST em /usuarios
    );

    public JwtFilter(JwtUtil jwtUtil, UsuarioService usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Verifica se é uma rota pública
        if (isPublicRoute(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            sendError(response, "Token não fornecido");
            return;
        }

        String token = header.substring(7);

        try {
            String email = jwtUtil.extrairEmail(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Usuario usuario = usuarioService.buscarPorEmail(email)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception e) {
            sendError(response, "Token inválido ou expirado: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    // Método para verificar se a rota é pública
    private boolean isPublicRoute(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        for (PublicRoute publicRoute : PUBLIC_ROUTES) {
            if (publicRoute.matches(path, method)) {
                return true;
            }
        }
        return false;
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }

    // Classe interna para representar rotas públicas
    private static class PublicRoute {
        private final String path;
        private final String method;

        public PublicRoute(String path, String method) {
            this.path = path;
            this.method = method;
        }

        public boolean matches(String requestPath, String requestMethod) {
            if ("ALL".equals(method)) {
                // Verifica se o caminho começa com o path definido
                return requestPath.startsWith(path);
            } else {
                // Verifica caminho exato e método específico
                return requestPath.equals(path) && requestMethod.equals(method);
            }
        }
    }
}