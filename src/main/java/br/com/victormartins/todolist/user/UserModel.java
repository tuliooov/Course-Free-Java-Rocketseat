/**
 * Classe modelo do usuário
 * Esta classe representa um usuário
 * Esta classe esta sendo utilizada para gerar a tabela de usuários no Banco de Dados 
 */

package br.com.victormartins.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data // Definindo que este objeto será uma tabela no banco de dados
@Entity(name = "tb_users") // Definindo o nome da tabela
public class UserModel {

    @Id // Define este atributo como a primary key - ID
    @GeneratedValue(generator = "UUID") // Define que a geração do UUID será automática
    private UUID id; // Novo atributo criado para ser o ID/PK

    @Column(unique = true) // Definindo a coluna username do DB como única, evitando assim valores duplicados
    private String username;
    private String name;
    private String password;

    @CreationTimestamp // Definindo a criação do Timestamp
    private LocalDateTime createdAt; // Atributo para armazenar o momento que este objeto foi criado

}
