package br.com.empresa.vendamais.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 0, message = "Quantidade deve ser maior ou igual a zero")
    @Column(nullable = false)
    private Integer quantidade;

    @Column(length = 500)
    private String descricao;

    @NotNull(message = "Preço é obrigatório")
    @Min(value = 0, message = "Preço deve ser maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private Double preco;
}

