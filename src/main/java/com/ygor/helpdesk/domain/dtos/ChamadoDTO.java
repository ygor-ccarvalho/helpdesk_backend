package com.ygor.helpdesk.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ygor.helpdesk.domain.Chamado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChamadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAbertura = LocalDate.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFechamento;

    @NotNull(message = "O campo Prioridade é obrigatório")
    private Integer prioridade;

    @NotNull(message = "O campo Status é obrigatório")
    private Integer status;

    @NotBlank(message = "O campo Título é obrigatório")
    private String titulo;

    @NotBlank(message = "O campo Observações é obrigatório")
    private String observacoes;

    @NotNull(message = "O campo Técnico é obrigatório")
    private Integer tecnico;

    @NotNull(message = "O campo Cliente é obrigatório")
    private Integer cliente;

    private String nomeTecnico;
    private String nomeCliente;

    public ChamadoDTO(Chamado obj) {
        this.id = obj.getId();
        this.dataAbertura = obj.getDataAbertura();
        this.dataFechamento = obj.getDataFechamento();
        this.prioridade = obj.getPrioridade().getCodigo();
        this.status = obj.getStatus().getCodigo();
        this.titulo = obj.getTitulo();
        this.observacoes = obj.getObservacoes();
        this.tecnico = obj.getTecnico().getId();
        this.cliente = obj.getCliente().getId();
        this.nomeCliente = obj.getCliente().getNome();
        this.nomeTecnico = obj.getTecnico().getNome();
    }
}
