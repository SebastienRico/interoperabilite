package com.interoperability.interoperability.models;

import java.io.Serializable;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "property")
public class PropertyDocument implements Serializable{
    
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "label", nullable = false)
    private String label;
}
