package org.dailycodework.dreamshops.service.product;

import org.dailycodework.dreamshops.dto.ProductDto;
import org.dailycodework.dreamshops.model.Product;
import org.dailycodework.dreamshops.request.AddProductRequest;
import org.dailycodework.dreamshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService
{
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProduct(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);


    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
