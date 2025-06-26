package org.dailycodework.dreamshops.service.product;

import lombok.RequiredArgsConstructor;
import org.dailycodework.dreamshops.dto.ImageDto;
import org.dailycodework.dreamshops.dto.ProductDto;
import org.dailycodework.dreamshops.exception.AlreadyExitsException;
import org.dailycodework.dreamshops.exception.ProductNotFountException;
import org.dailycodework.dreamshops.exception.ResourceNotFoundException;
import org.dailycodework.dreamshops.model.Category;
import org.dailycodework.dreamshops.model.Image;
import org.dailycodework.dreamshops.model.Product;
import org.dailycodework.dreamshops.repository.CategoryRepository;
import org.dailycodework.dreamshops.repository.ImageRepository;
import org.dailycodework.dreamshops.repository.ProductRepository;
import org.dailycodework.dreamshops.request.AddProductRequest;
import org.dailycodework.dreamshops.request.ProductUpdateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
//    private  ProductUpdateRequest request;

    @Override
    public Product addProduct(AddProductRequest request) {

        if(productExists(request.getName(),request.getBrand())){
            throw new AlreadyExitsException(request.getBrand()+" "+request.getName()+" already exists, you may update this product instead!");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));

    }

    private Boolean productExists(String name,String brand) {
        return productRepository.existsByNameAndBrand(name,brand);
    }

    private Product createProduct(AddProductRequest request, Category category) {

        return new Product(
                request.getName (),
                request.getBrand (),
                request.getPrice (),
                request.getInventory (),
                request.getDescription (),
                category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProduct(Long id) {
       productRepository.findById(id)
               .ifPresentOrElse(productRepository::delete,
               ()->{throw new ProductNotFountException("Product not found!");});
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct,request))
                .map(productRepository :: save)
                .orElseThrow(()-> new ProductNotFountException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return  existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findAllByCategoryNameIgnoreCase(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrandIgnoreCase(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findAllByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findAllByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductsByBrandAndName(brand,name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
         ProductDto productDto = modelMapper.map(product, ProductDto.class);
         List<Image>  images = imageRepository.findByProductId(product.getId());
         List<ImageDto> imageDtos=images.stream()
                 .map(image -> modelMapper.map(image, ImageDto.class))
                 .toList();
         productDto.setImages(imageDtos);
         return productDto;
    }
}
