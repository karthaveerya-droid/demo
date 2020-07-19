package com.example.demo.model;



import com.sun.istack.NotNull;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cars")
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Basic(optional = false)
    private Long id;
    @NotNull
    private String make_name;
    public Car(){
        super();
    }
    public Car(Long id, String make_name) {
        super();
        this.id = id;
        this.make_name = make_name;
    }

    @Id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMake_name() {
        return make_name;
    }
    public void setMake_name(String make_name) {
        this.make_name = make_name;
    }
}