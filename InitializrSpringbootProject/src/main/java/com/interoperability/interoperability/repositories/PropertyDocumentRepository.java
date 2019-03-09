package com.interoperability.interoperability.repositories;

import com.interoperability.interoperability.models.PropertyDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PropertyDocumentRepository extends CrudRepository<PropertyDocument, Long> {
    
    @Query(value="SELECT DISTINCT * FROM property p WHERE p.id=:id", nativeQuery = true)
    public PropertyDocument findPropertyDocumentById(@Param("id")Long id);
    
    @Query(value="SELECT DISTINCT * FROM property p WHERE p.label=:label", nativeQuery = true)
    public PropertyDocument findPropertyDocumentByLabel(@Param("label")String label);
}
