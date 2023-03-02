package com.example.produktapi.repository;

import com.example.produktapi.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest // Lägg till
class ProductRepositoryTest {

    @Autowired // Lägg till för att ersätta en konstruktor
    private ProductRepository underTest;

    @Test
    void testingOurRepository() {

            List <Product> products = underTest.findAll();
            Assertions.assertFalse(products.isEmpty());
        }

    @Test
    void whenSearchingForExistingTitle_thenReturnProduct() {

        Product product = new Product("Dator", 20000.00, "Elektronik",
                "Används för att koda i java", "urlSträng");


        underTest.save(product);

        Optional <Product> result = underTest.findByTitle(product.getTitle());
        Assertions.assertAll(
                ()-> assertTrue(result.isPresent()),
                ()-> assertEquals(result.get().getTitle(), "Dator")

        );
    }

    @Test
    void whenSearchingForNonExistingTitle_thenReturnEmptyOptional(){

        // given
        String title = "Titeln finns inte";

        // when
        Optional <Product> result = underTest.findByTitle(title);

        //then
        Assertions.assertAll(
                () -> assertFalse(result.isPresent()),
                () -> assertTrue(result.isEmpty()),
                () -> assertThrows(Exception.class,()->result.get(),"Detta meddelande syns om det blir fel")
        );
    }

    @Test
    void findByCategory() {

        String category = "Elektronik";

        // given
        Product product = new Product("Dator", 20000.00, category,
                "Används för att koda i java", "urlSträng");

        underTest.save(product);

        // when
        List <Product> result = underTest.findByCategory(product.getCategory());

        // then
        Assertions.assertAll(
                ()-> assertFalse(result.isEmpty()),
                ()-> assertEquals("Elektronik",result.get(0).getCategory()));

    }

    @Test
    void findAllCategories() {

        // given
        List <String> existingCategory = new ArrayList<>(Arrays.asList("electronics",
                "jewelery",
                "men's clothing",
                "women's clothing"));


        List <String> uniqueCategories = existingCategory.stream().distinct().collect(Collectors.toList());

        System.out.println(uniqueCategories);

        // when
        List <String> result = underTest.findAllCategories();

        // then
        Assertions.assertEquals(4, underTest.findAllCategories().size()); // Kollar antalet kategorier
        Assertions.assertEquals(existingCategory, result); // Kollar om det finns duplicates



    }



}