package com.lucasg.cursomc.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StandardError implements Serializable {

    private Long timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}
