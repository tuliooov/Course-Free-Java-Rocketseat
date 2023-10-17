/**
 * Interface de repositório
 * Utilizada para realizar o tratamento dos dados do objeto para inserção no banco de dados
 */

package br.com.victormartins.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    UserModel findByUsername(String username); // Criando o método findByUsername para poder procurar o username no DB
}
