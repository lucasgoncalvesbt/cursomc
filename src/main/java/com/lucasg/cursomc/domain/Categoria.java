package com.lucasg.cursomc.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

}
