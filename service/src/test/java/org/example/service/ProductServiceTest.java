package org.example.service;

import com.querydsl.core.types.Predicate;
import org.example.dto.filter.PriceFilterEnum;
import org.example.dto.filter.ProductFilter;
import org.example.dto.orderDto.OrderDtoCreateResponse;
import org.example.dto.productDto.ProductDtoCreateResponse;
import org.example.dto.productDto.ProductDtoRequest;
import org.example.dto.statusDto.StatusDto;
import org.example.dto.userDto.UserDtoRequest;
import org.example.entity.Category;
import org.example.entity.Product;
import org.example.entity.Status;
import org.example.entity.User;
import org.example.mapper.ProductMapper;
import org.example.mapper.StatusMapper;
import org.example.repository.CategoryRepository;
import org.example.repository.ProductRepository;
import org.example.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private UserService userService;
    @Mock
    private ImageService imageService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private StatusMapper statusMapper;
    @Mock
    private OrderService orderService;
    private Product product1;
    private Product product2;
    private ProductDtoRequest productDtoRequest;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product2 = new Product();
        productDtoRequest = ProductDtoRequest.builder()
                .user(UserDtoRequest.builder().build())
                .build();
    }


    @Test
    void getProductSliceByFilterTest() {
        List<Product> products = Arrays.asList(product1, product2);
        Slice<Product> productsSlice = new PageImpl<>(products);

        doReturn(productsSlice).when(productRepository).findAll(any(Predicate.class), any(Pageable.class));
        doReturn(ProductDtoRequest.builder().build()).when(productMapper).toProductDto(any(Product.class));

        productService.getProductSliceByFilter(0, 4, new ProductFilter("dfdf", PriceFilterEnum.Descending.name()));

        verify(productRepository, times(1)).findAll(any(Predicate.class), any(Pageable.class));
        verify(productMapper, times(2)).toProductDto(any(Product.class));
    }

    @Test
    void getProductSliceTest() {
        List<Product> products = Arrays.asList(product1, product2);
        Slice<Product> productsSlice = new SliceImpl<>(products);

        doReturn(productsSlice).when(productRepository).findAllByStatus(eq(Status.ON_SALE), any(Pageable.class));
        doReturn(ProductDtoRequest.builder().build()).when(productMapper).toProductDto(any(Product.class));

        productService.getProductSlice(PageRequest.of(1, 2));

        verify(productRepository, times(1)).findAllByStatus(eq(Status.ON_SALE), any(Pageable.class));
        verify(productMapper, times(2)).toProductDto(any(Product.class));
    }

    @Test
    void getProductsByStatus() {
        List<Product> products = Arrays.asList(product1, product2);

        doReturn(products).when(productRepository).findAllByStatus(eq(Status.ON_SALE));
        doReturn(ProductDtoRequest.builder().build()).when(productMapper).toProductDto(any(Product.class));
        doReturn(Status.ON_SALE).when(statusMapper).toStatus(any(StatusDto.class));

        productService.getProductsByStatus(StatusDto.ON_SALE);

        verify(productRepository, times(1)).findAllByStatus(eq(Status.ON_SALE));
        verify(statusMapper, times(1)).toStatus(eq(StatusDto.ON_SALE));
        verify(productMapper, times(2)).toProductDto(any(Product.class));
    }

    @Test
    void getUserProductsOnSale() {
        List<Product> products = Arrays.asList(product1, product2);

        doReturn(products).when(productRepository).findAllByUserAndStatus(any(User.class), eq(Status.ON_SALE));
        doReturn(Optional.of(new User())).when(userRepository).findById(any(Long.class));
        doReturn(ProductDtoRequest.builder().build()).when(productMapper).toProductDto(any(Product.class));

        productService.getUserProductsOnSale(1L);

        verify(productRepository, times(1)).findAllByUserAndStatus(any(User.class), eq(Status.ON_SALE));
        verify(productMapper, times(2)).toProductDto(any(Product.class));
    }

    @Test
    void findImageByProductIdTest() {
        product1.setImage("image");
        doReturn(Optional.of(product1)).when(productRepository).findById(any(Long.class));
        doReturn(Optional.empty()).when(imageService).get(any(String.class));

        productService.findImageByProductId(1L);

        verify(productRepository, times(1)).findById(any(Long.class));
        verify(imageService, times(1)).get(any(String.class));
    }

    @Test
    void findByIdTest() {
        doReturn(Optional.of(product1)).when(productRepository).findById(any(Long.class));
        doReturn(ProductDtoRequest.builder().build()).when(productMapper).toProductDto(any(Product.class));

        productService.findById(1L);

        verify(productRepository, times(1)).findById(any(Long.class));
        verify(productMapper, times(1)).toProductDto(any(Product.class));
    }

    @Test
    void createProductTest() {
        doReturn(product1).when(productRepository).save(any(Product.class));
        doReturn(Optional.of(new Category())).when(categoryRepository).findByCategoryName(null);
        doReturn(Optional.of(new User())).when(userRepository).findById(null);
        doReturn(new Product()).when(productMapper).toProduct(any(ProductDtoCreateResponse.class));
        doNothing().when(imageService).uploadImage(null);

        productService.createProduct(ProductDtoCreateResponse.builder().build());

        verify(productRepository, times(1)).save(any(Product.class));
        verify(categoryRepository, times(1)).findByCategoryName(null);
        verify(userRepository, times(1)).findById(null);
        verify(productMapper, times(1)).toProduct(any(ProductDtoCreateResponse.class));
        verify(imageService, times(1)).uploadImage(null);
    }


    @Test
    void verifyProductTest() {
        doReturn(Optional.of(product1)).when(productRepository).findById(any(Long.class));

        productService.verifyProduct(1L);

        verify(productRepository, times(1)).findById(any(Long.class));
    }

    @Test
    void deleteProductTest() {
        doReturn(Optional.of(product1)).when(productRepository).findById(any(Long.class));
        doNothing().when(productRepository).delete(any(Product.class));
        doNothing().when(productRepository).flush();
        doNothing().when(imageService).deleteImage(null);

        productService.delete(1L);

        verify(productRepository, times(1)).findById(any(Long.class));
        verify(productRepository, times(1)).delete(any(Product.class));
        verify(productRepository, times(1)).flush();
        verify(imageService, times(1)).deleteImage(null);
    }

    @Test
    void buyProduct() {
        doReturn(Optional.of(product1)).when(productRepository).findById(null);
        doNothing().when(productRepository).flush();
        doNothing().when(orderService).createOrder(any(OrderDtoCreateResponse.class));
        doReturn(UserDtoRequest.builder().build()).when(userService).updateBalance(eq(null), eq(null), any(BinaryOperator.class));

        productService.buyProduct(productDtoRequest, UserDtoRequest.builder().build());

        verify(productRepository, times(1)).findById(null);
        verify(productRepository, times(1)).flush();
        verify(orderService, times(1)).createOrder(any(OrderDtoCreateResponse.class));
        verify(userService, times(2)).updateBalance(eq(null), eq(null), any(BinaryOperator.class));
    }

//
//    @Transactional
//    public void buyProduct(ProductDtoRequest productDto, UserDtoRequest userDto) {
//        try {
//            productRepository.findById(productDto.getId())
//                    .map(product -> {
//                        product.setStatus(Status.SALES);
//                        productRepository.flush();
//                        return product;
//                    });
//
//            orderService.createOrder(
//                    OrderDtoCreateResponse.builder()
//                            .product(productDto)
//                            .user(userDto)
//                            .build()
//            );
//
//            BinaryOperator<BigDecimal> minus = BigDecimal::subtract;
//            userService.updateBalance(
//                    productDto.getPrice(),
//                    userDto.getId(),
//                    minus
//            );
//            BinaryOperator<BigDecimal> plus = BigDecimal::add;
//            userService.updateBalance(
//                    productDto.getPrice(),
//                    productDto.getUser().getId(),
//                    plus
//            );
//        } catch (Exception e) {
//            throw new ServiceException(e);
//        }
//    }
//


}
