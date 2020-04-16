package server.shop.bl.repositories;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import server.shop.entities.dto.ImageProductDto;
import server.shop.entities.model.ImageProductModel;
import server.shop.entities.model.ProductModel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Component
public class StoreRepositoryJpaImpl implements StoreRepositoryJpa {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<ProductModel> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<ProductModel> findAll() {

        return entityManager.createQuery("SELECT p FROM ProductModel p ORDER BY p.id", ProductModel.class)
                .getResultList();
    }

    public List<ImageProductModel> findImageByProductId(Long id) {
        throw new IllegalStateException("not supported");
        //Query query = entityManager.createQuery("SELECT p FROM ImageProductModel p WHERE p.productModel = :id");

        //query.setParameter("id", id);

        //return  query.getResultList();
    }

    @Override
    @Transactional
    public Optional<ProductModel> findById(Long id) {
        return Optional.of(entityManager.find(ProductModel.class, id));
    }

    @Override
    @Transactional
    public boolean buyProduct(ProductModel model, Integer count) {

        System.out.println(model.getCount() + " " + count);

        if (model.getCount() - count >= 0) {
            System.out.println(count);
            System.out.println(model.getId());

            model.setCount(model.getCount() - count);

            entityManager.merge(model);
            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public ProductModel save(ProductModel entity) {

        List<ImageProductModel> images = entity.getImages();
        entityManager.persist(entity);


        for (ImageProductModel i : images) {
            entityManager.persist(i);
        }


        return entity;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public List<ProductModel> findBySimilarName(String name) {
        return entityManager
                .createQuery("SELECT p from ProductModel p WHERE upper(p.name) like concat('%', upper(?1), '%')",
                        ProductModel.class).setParameter(1, name).getResultList();

    }

    @Override
    public List<ProductModel> findByPagination(int page, int size) {
        return entityManager.createQuery("SELECT p FROM  ProductModel p ORDER BY p.name ASC", ProductModel.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size).getResultList();
    }

    @Override
    public Long productsCount() {
        BigInteger bigInteger = (BigInteger) entityManager.createNativeQuery("SELECT count(1) FROM ProductModel").getSingleResult();
        return bigInteger.longValue();
    }
}
