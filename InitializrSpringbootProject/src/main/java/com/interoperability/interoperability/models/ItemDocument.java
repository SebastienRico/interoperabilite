package com.interoperability.interoperability.models;

import javax.persistence.Column;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "item")
public class ItemDocument {
    
    @Id
    @Column(name = "id", nullable = false)
    private String id;
    
    @Column(name = "label", nullable = false)
    private String label;
    
}
