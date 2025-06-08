package org.dailycodework.dreamshops.repository;

import org.dailycodework.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByCategoryNameIgnoreCase(String category);


    List<Product> findAllByBrand(String brand);

    List<Product> findAllByCategoryNameAndBrand(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findAllByBrandAndName(String brand, String name);
    
    List<Product> findByBrandIgnoreCase(String brand);


    Long countProductsByBrandAndName(String brand, String name);

}
