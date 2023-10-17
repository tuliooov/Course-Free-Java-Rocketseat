/**
 * Toda requisição enviada, irá passar por aqui, pelo filtro.
 */

package br.com.victormartins.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.victormartins.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Falando que quero que o spring gerencie esta classe
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository; // Adicionando o UserRepository para ter acesso aos métodos

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Pegando a rota que está sendo solicitada
        var servletPath = request.getServletPath();

        // Se a rota solicitada for a de tasks...
        if (servletPath.startsWith("/tasks/")) {

            // 1° - Receber a autenticação (user + password)
            // Armazenando os dados da autenticação são recebidos no header do documento
            var authorization = request.getHeader("Authorization");
            System.out.println("Authorization: ");
            System.out.println(authorization);

            // Isolando o conteúdo recebido na authorization
            var authEncoded = authorization.substring("Basic".length()).trim();

            // Decodificando o Base64 recebido na authorization
            byte[] authDecode = Base64.getDecoder().decode(authEncoded);

            // Transformando o bytecode em String
            var authString = new String(authDecode);

            // Dividindo a string para separar o usuário da senha
            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            // 2° - Validar se o usuário existe
            var user = this.userRepository.findByUsername(username); // Procurando se o usuário existe

            // Se retornar nulo...
            if (user == null) {
                response.sendError(401);

            } else {
                // Validar a senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

                // Se a senha for correta...
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId()); // Setando um atributo idUser com o valor do ID da autenticação

                    filterChain.doFilter(request, response);

                } else {
                    // Se for incorreta, retorna o erro 401
                    response.sendError(401);

                }

            }

        } else {
            filterChain.doFilter(request, response);
        }

    }

}
