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
    ArgumentCaptor<Product> productCaptor;

    @Test
    void whenGetAllProducts_thenExactlyOneInteractionWithRepository() {

        // when
        underTest.getAllProducts();

        // then
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);

    }

    @Test
    void whenGetAllCategories_thenExactlyOneInteractionWithRepositoryMethodFindAllCategories() {

        // when
        underTest.getAllCategories();

        // then
        verify(repository, times(1)).findAllCategories();
        verifyNoMoreInteractions(repository);

    }

    @Test
    void givenAnExistingCategory_whenGetProductsByCategory_thenShowANonEmptyList() {

        // given
        String category = "Electronics";

        // when
        underTest.getProductsByCategory(category);

        // then
        verify(repository, times(1)).findByCategory(category);
        verifyNoMoreInteractions(repository);

    }


    @Test
    void givenAnExistingId_whenAddingProduct_thenInvokeSaveMethod() {

        // given
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
        assertEquals(product, productCaptor.getValue());

    }

    @Test
    void givenAnExistingTitle_whenAddingProductWithDuplicateTitle_thenThrowBadExceptionError() {

        // given
        String title = "vår test-titel";
        Product product = new Product(
                title,
                2500.00,
                "Electronics",
                "Bra grejer",
                "url");

        // when
        given(repository.findByTitle("vår test-titel")).willReturn(Optional.of(product));

        // then
        assertThrows(BadRequestException.class,
                () -> underTest.addProduct(product));
        verify(repository, times(1)).findByTitle(title);
        verify(repository, never()).save(any());
    }

    @Test
    void givenAnExistingCategory_whenGetProductsByCategory_thenCheckHowManyProductsByCategory() {

        // given
        String category = "Computer";
        Product product = new Product(
                "Dator",
                1000.00,
                category,
                "A nice comp",
                "enUrlSträngHär"
        );

        // when
        given(repository.findByCategory(category)).willReturn(List.of(product));
        List<Product> result = underTest.getProductsByCategory(category);

        // then
        assertEquals(1, result.size());
    }

    @Test
    void givenAProduct_whenAddProduct_thenGetProductById_thenCheckIfExistingProductIsFoundById() {

        // given
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
    void givenANoNExistingId_whenGetProductById_thenThrowEntityNotFoundException() {

        // given
        Integer id = 1;

        // when
        when(repository.findById(id)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            underTest.getProductById(id);
        });

        // then
        assertEquals("Produkt med id " + id + " hittades inte", exception.getMessage());
    }


    @Test
    void givenTwoProducts_whenFindById_thenVerifyNewProduct() {

        // given
        Integer id = 1;
        Product product = new Product(
                "Computer",
                10000.00,
                "Electronics",
                "A nice comp",
                "enUrlSträngHär"
        );
        product.setId(id);

        Product productNew = new Product(
                "Computor",
                35.0,
                "Electronics",
                "Description of item",
                "URL");

        // when
        when(repository.findById(id)).thenReturn(Optional.of(product));
        underTest.updateProduct(productNew, product.getId());

        // then
        verify(repository, times(1)).findById(product.getId());
        verify(repository, times(1)).save(productNew); // This is where we check products
        verifyNoMoreInteractions(repository);

    }

    @Test
    void givenAProductId_whenFindById_thenCheckIfIdMatch_thenIfNotValidThenThrowEntityNotFoundException() {

        // given
        Integer id = 1;
        Product product = new Product(
                "Computer",
                10000.00,
                "Electronics",
                "A nice comp",
                "enUrlSträngHär"
        );
        product.setId(id);

        when(repository.findById(id)).thenReturn(Optional.empty());

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        underTest.updateProduct(product, id);
        });

        // then
        assertEquals("Produkt med id " + id + " hittades inte", exception.getMessage());
    }

    @Test
    void givenAnSpecificId_whenDeleteProduct_thenFindByIdIfProductByIdNotFound_thenThrowEntityNotFoundException() {

        // given
        Integer id = 1;

        // when
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
        underTest.deleteProduct(1);
        });

        // then
        assertEquals("Produkt med id " + id + " hittades inte", exception.getMessage());

    }

    @Test
    void givenASpecificProductById_whenSearchingForAnId_thenRunMethodExactlyOneTime_thenDeleteProductById() {

        // given
        Integer id = 1;
        Product product = new Product(
                "Computer",
                10000.00,
                "Electronics",
                "A nice comp",
                "enUrlSträngHär"
        );
        product.setId(id);

        // when
        when(repository.findById(1)).thenReturn(Optional.of(product)); //expect a fetch, return a "fetched" product;
        underTest.deleteProduct(id);

        // then
        verify(repository, times(1)).deleteById(id);
    }
}
