package com.jllz.habitissimo.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
public class Description {

    @Id
    private Long id;

    private String description;
    private String subcategory;
}
