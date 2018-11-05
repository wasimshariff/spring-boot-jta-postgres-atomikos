package com.example.postgres.model;

import org.springframework.context.annotation.ComponentScan;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PETS")
public class Pets {

    @Id
    private String id;

    @NotNull(message = "Pets msg cannot be null")
    private String msg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
