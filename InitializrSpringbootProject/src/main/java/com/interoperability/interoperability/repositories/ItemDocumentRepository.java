package com.interoperability.interoperability.repositories;

import com.interoperability.interoperability.models.ItemDocument;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ItemDocumentRepository extends CrudRepository<ItemDocument, Long> {
    
    @Query(value="SELECT DISTINCT * FROM item i WHERE i.id=:id", nativeQuery = true)
    public ItemDocument findItemDocumentById(@Param("id")Long id);
    
    @Query(value="SELECT DISTINCT * FROM item i WHERE i.label=:label", nativeQuery = true)
    public ItemDocument findItemDocumentByLabel(@Param("label")Long label);
}
