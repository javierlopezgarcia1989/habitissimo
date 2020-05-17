package com.jllz.habitissimo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Status {

    @Id
    private String name;

    @OneToMany(mappedBy = "status", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonIgnore
    @Transient
    private List<Budget> budget;

    public Status() {
        this.name = "Pendiente";
    }
}
