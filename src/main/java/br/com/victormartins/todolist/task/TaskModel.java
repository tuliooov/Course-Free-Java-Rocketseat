package br.com.victormartins.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_tasks") // Nome da tabela
public class TaskModel {

    @Id // Definindo como o ID - Primary Key
    @GeneratedValue(generator = "UUID") // Geração automática do UUID
    private UUID id;
    private String description;

    @Column(length = 50) // Limitando a coluna de titulo para 50 caracteres somente
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {

        if(title.length() > 50){
            throw new Exception("O Título deve conter no máximo 50 caracteres");
        }

        this.title = title;

    }

}
