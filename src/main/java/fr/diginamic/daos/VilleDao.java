package fr.diginamic.daos;

import fr.diginamic.entites.Ville;
import fr.diginamic.exceptions.VilleException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VilleDao {

    @PersistenceContext
    private EntityManager em;

    public List<Ville> getAllVilles() {

        TypedQuery<Ville> query = em.createQuery("FROM Ville", Ville.class);
        return query.getResultList();

    }

    public Ville getVilleById(int id) throws VilleException {

        try {
            return em.find(Ville.class, id);
        } catch (Exception ex) {
            throw new VilleException("La ville avec l'id " + id + "n'existe pas en base de données.");
        }

    }

    public Ville getVilleByNom(String nom) throws VilleException {

        try {
            return em.createQuery("FROM Ville v WHERE v.nom = :nom", Ville.class).setParameter("nom", nom).getSingleResult();
        } catch (Exception ex) {
            throw new VilleException("La ville avec le nom " + nom + "n'existe pas en base de données. Impossible de la modifier.");
        }

    }

    public void addVille(Ville ville) {

        em.persist(ville);

    }

    public void deleteVille(Ville ville) {

        em.remove(ville);

    }

}
