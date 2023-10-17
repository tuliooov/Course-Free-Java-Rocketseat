/**
 * Esta é a classe Controller do Usuário
 * É por aqui que recebemos as solicitações do /users e as direcionamos
 */

package br.com.victormartins.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController // Definindo a classe como uma RestController
@RequestMapping("/users") // Mapeando a rota
public class UserController {

    @Autowired // Fazendo o spring gerenciar o ciclo de vida
    private IUserRepository userRepository; // Chamando a interface

    @PostMapping("/") // Mapenado a rota como "/" para receber requests via POST
    public ResponseEntity create(@RequestBody UserModel userModel) { // O método retorna um Response entity

        var user = this.userRepository.findByUsername(userModel.getUsername()); // Fazendo a busca do username no DB

        // Se o usuário já existir, retorna uma mensagem de erro e um Status Code
        if (user != null) {
            // Retorna para a requisição o Status de Bad Request e o mensagem de que o
            // usuário já existe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe");
        }

        /**
         * Criptografando a senha recebida
         * Definimos uma variável passwordHashed que irá receber o método hashToString
         * do BCrypt
         * O método recebe um cost(intensidade da criptografia - 12 é o default) e
         * recebe o valor a ser criptografado
         */
        var passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

        userModel.setPassword(passwordHashed); // Passando a senha criptografada para o objeto userModel

        var userCreated = this.userRepository.save(userModel); // O userRepository salva o objeto
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated); // Retorna o status code CREATED e os dados
                                                                            // do usuário criado
    }

}
