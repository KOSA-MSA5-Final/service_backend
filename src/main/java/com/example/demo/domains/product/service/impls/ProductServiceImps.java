package com.example.demo.domains.product.service.impls;

import com.example.demo.domains.product.dto.DiseaseProductDTO;
import com.example.demo.domains.product.dto.ProductDTO;
import com.example.demo.domains.product.entity.DiseaseProduct;
import com.example.demo.domains.product.entity.Product;
import com.example.demo.domains.product.entity.ProductDetailImg;
import com.example.demo.domains.product.entity.ProductImg;
import com.example.demo.domains.product.repository.DiseaseProductRepository;
import com.example.demo.domains.product.repository.ProductDetailImgRepository;
import com.example.demo.domains.product.repository.ProductImgRepository;
import com.example.demo.domains.product.repository.ProductRepository;
import com.example.demo.domains.product.service.interfaces.ProductService;
import com.example.demo.domains.profile_medical.dto.ProfileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImps implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;
    private final ProductDetailImgRepository productDetailImgRepository;
    private final DiseaseProductRepository diseaseProductRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    // Product -> ProductDTO 변환 메서드
    public ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setMaker(product.getMaker());
        dto.setType(product.getType());
        dto.setSubtype(product.getSubtype());
        dto.setPrice(product.getPrice());
        dto.setOrigin(product.getOrigin());
        dto.setAllRawMaterial(product.getAll_rawmaterial());
        dto.setIngredient(product.getIngredient());
        dto.setCalories(product.getCalories());
        dto.setWeight(product.getWeight());
        dto.setAgeGroup(product.getAge_group());
        dto.setFunction(product.getFunction());
        dto.setMainimageurl(product.getMain_image_url());

        // Product의 Animal 엔티티의 이름을 animalType 필드에 설정
        if (product.getAnimal() != null) {
            dto.setAnimalName(product.getAnimal().getName()); // Animal 엔티티의 name 필드를 가져옴
        }

        // ProductImg와 ProductDetailImg를 DTO로 변환하여 추가
        List<ProductImg> productImages = productImgRepository.findByProduct(product);
        List<String> imageUrls = productImages.stream()
                .map(ProductImg::getImageUrl)
                .collect(Collectors.toList());
        dto.setImageUrls(imageUrls);

        List<ProductDetailImg> productDetailImages = productDetailImgRepository.findByProduct(product);
        List<String> detailImageUrls = productDetailImages.stream()
                .map(ProductDetailImg::getImageUrl)
                .collect(Collectors.toList());
        dto.setDetailImageUrls(detailImageUrls);

        // DiseaseProduct 정보를 DTO로 변환하여 추가
        List<DiseaseProductDTO> diseaseProductDTOs = diseaseProductRepository.findByProductId(product.getId())
                .stream()
                .map(diseaseProduct -> {
                    DiseaseProductDTO diseaseProductDTO = new DiseaseProductDTO();
                    diseaseProductDTO.setId(diseaseProduct.getId());
                    diseaseProductDTO.setDiseaseName(diseaseProduct.getDiseaseNames().getName());
                    diseaseProductDTO.setProductId(diseaseProduct.getProduct().getId());
                    return diseaseProductDTO;
                })
                .collect(Collectors.toList());
        dto.setDiseaseProducts(diseaseProductDTOs);

        return dto;
    }

    // 모든 Product 엔티티를 ProductDTO로 변환하여 반환하는 메서드
    public List<ProductDTO> getAllProductDTOs() {
        return productRepository.findAll().stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    // 특정 타입에 따른 ProductDTO 리스트 반환 메서드 예시
    public List<ProductDTO> getProductsByType(String type) {
        return productRepository.findByType(type).stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductsById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return convertToProductDTO(product);
    }
}
