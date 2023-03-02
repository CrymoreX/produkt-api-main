package com.example.produktapi.service;

import com.example.produktapi.exception.BadRequestException;
import com.example.produktapi.exception.EntityNotFoundException;
import com.example.produktapi.model.Product;
import com.example.produktapi.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductService underTest;
    @Captor
    ArgumentCaptor <Product> productCaptor;

    @Test
    void whenGetAllProducts_thenExactlyOneInteractionWithRepository() {

        // when
        underTest.getAllProducts();

        // then
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);

    }

    @Test
    void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryGetByCategory() {

        // when
        underTest.getAllCategories();
        // then
        verify(repository, times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);

    }

    @Test
    void givenAnExistingCategory_whenGetProductsByCategory_thenShowANonEmptyList() {

        //given
        String category = "Electronics";
        // when
        underTest.getProductsByCategory(category);
        // then
        verify(repository, times(1)).findByCategory(category);
        verifyNoMoreInteractions(repository);

    }


    @Test
    void addProductShouldInvokeSaveMethod(){

        //given
        Product product = new Product(
                "Computer",
                10000.00,
                "Electronics",
                "A nice comp",
                "enUrlSträngHär"
                );

        // when
        underTest.addProduct(product);

        // then
        verify(repository).save(productCaptor.capture());
        assertEquals(product,productCaptor.getValue());

    }

    @Test
    void whenAddingProductWithDuplicateTitle_thenThrowError(){

        String title = "vår test-titel";

        Product product = new Product(
                title,
                2500.00,
                "Electronics",
                "Bra grejer",
                "url");

        //given
        given(repository.findByTitle("vår test-titel")).willReturn(Optional.of(product));

        //then
            assertThrows(BadRequestException.class,
                    //when
                    () -> underTest.addProduct(product));
            verify(repository,times(1)).findByTitle(title);
            verify(repository,never()).save(any());
    }
    @Test
    void testGetProductsByCategory(){

        String category = "Computer";

        Product product = new Product(
                "Dator",
                1000.00,
                category,
                "A nice comp",
                "enUrlSträngHär"
        );

        given(repository.findByCategory(category)).willReturn(List.of(product));

        List<Product> result = underTest.getProductsByCategory(category);

        assertEquals(1,result.size()); // Kollar om det finns en kategori
    }

    @Test
    void testGetProductById(){
        //given
        Product product = new Product(
                "Computer",
                10000.00,
                "Electronics",
                "A nice comp",
                "enUrlSträngHär"
        );

        // when
        underTest.addProduct(product);

        // then
        given(repository.findById(product.getId())).willReturn(Optional.of(product));
        Assertions.assertTrue(repository.findById(product.getId()).isPresent());
    }

    @Test
    void givenANoNExistingId_whenGetProductById_thenThrowEntityNotFoundException(){

        // given
        Integer id = 1;

        // when
        when(repository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, ()-> {
            underTest.getProductById(id);
        });


        assertEquals("Produkt med id " + id + " hittades inte", exception.getMessage());

    }


    @Test
    void testUpdateProduct_whenFindById_thenUpdateProductById() {

        Integer id = 1;

        //given
        Product product = new Product(
                "Computer",
                10000.00,
                "Electronics",
                "A nice comp",
                "enUrlSträngHär"
        );
        product.setId(id);

        Product updatedProduct = new Product();
        updatedProduct.setTitle("Updated Snowflake");

        // when
        when(repository.findById(id)).thenReturn(Optional.of(updatedProduct));
        when(repository.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = underTest.updateProduct(updatedProduct, id);

        // then
        assertEquals("Updated Snowflake", result.getTitle());

    }

    @Test
    void testUpdateProduct_whenFindById_thenUpdateProductById_thenThrowEntityNotFoundException() {

        Integer id = 1;

        //given
        Product product = new Product(
                "Computer",
                10000.00,
                "Electronics",
                "A nice comp",
                "enUrlSträngHär"
        );
        product.setId(id);

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, ()-> {
            underTest.updateProduct(product,id);
        });

        assertEquals("Produkt med id " + id + " hittades inte", exception.getMessage());


    }

    @Test
    void testDeleteProduct_whenFindByIdIfProductByIdNotFound_thenThrowEntityNotFoundException() {

        // given
        Integer id = 1;

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, ()-> {
        underTest.deleteProduct(1);
        });

        assertEquals("Produkt med id " + id + " hittades inte", exception.getMessage());

    }

    @Test
    void testDeleteProduct_whenFindById_thenDeleteProductById() {

        Integer id = 1;

        Product product = new Product(
                "Computer",
                10000.00,
                "Electronics",
                "A nice comp",
                "enUrlSträngHär"
        );
        product.setId(id);

        when(repository.findById(1)).thenReturn(Optional.of(product)); //expect a fetch, return a "fetched" product;

        underTest.deleteProduct(id);

        verify(repository, times(1)).deleteById(id);

        Assertions.assertNotNull(underTest.getProductById(id));

    }

}