package com.interoperability.interoperability.repositories;

import com.interoperability.interoperability.models.Connexion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConnexionRepository extends CrudRepository<Connexion, Long> {
    
    @Query(value="SELECT DISTINCT * FROM connexion c WHERE c.login=:login AND c.password=:password", nativeQuery = true)
    public Connexion findConnexionWithLoginAndPassword(@Param("login")String login, @Param("password")String password);
    
    @Query(value="SELECT DISTINCT * FROM connexion c WHERE c.id=:id", nativeQuery = true)
    public Connexion findConnexionById(@Param("id")Long id);
}
