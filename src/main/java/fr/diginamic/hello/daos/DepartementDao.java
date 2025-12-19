package fr.diginamic.hello.daos;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.exceptions.DepartementException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartementDao {

    @PersistenceContext
    private EntityManager em;

    public List<Departement> getAllDepartements() {

        TypedQuery<Departement> query = em.createQuery("FROM Departement", Departement.class);
        return query.getResultList();

    }

    public Departement getDepartementByNom(String nom) throws DepartementException {

        try {
            return em.createQuery("FROM Departement d WHERE d.nom = :nom", Departement.class).setParameter("nom", nom).getSingleResult();
        } catch (Exception ex) {
            throw new DepartementException("Le département avec le nom " + nom + "n'existe pas en base de données. Impossible de le modifier.");
        }

    }

    public void addDepartement(Departement departement) {

        em.persist(departement);

    }

    public void deleteDepartement(Departement departement) {

        em.remove(departement);

    }


}
