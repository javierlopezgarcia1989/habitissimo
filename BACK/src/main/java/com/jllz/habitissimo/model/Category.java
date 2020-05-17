package com.jllz.habitissimo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JsonIgnore
    @Transient
    private List<Budget> budget;

    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @Transient
    @JsonIgnore
    private List<Subcategory> subcategories;
}
