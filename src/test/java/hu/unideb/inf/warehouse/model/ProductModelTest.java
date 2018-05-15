package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Place;
import hu.unideb.inf.warehouse.pojo.Product;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProductModelTest {

    ProductModel productModel01;
    ProductModel productModel02;
    Product product01;
    Product product02;
    Product product03;
    Product productDb01;
    Product productDb02;
    Product productDb03;
    String label01;
    String label02;
    String label03;


    @Before
    public void init() {
        productModel01 = new ProductModel();
        label01 = "TóróRudi_Test";
        label02 = "CocaCola_Test";
        label03 = "Tej_Test";
        product01 = new Product(label01, "1 darab");
        product02 = new Product(label02, "2,5 liter");
        product03 = new Product(label03, "1 liter");

        productDb01 = new Product();
        productDb02 = new Product();
        productDb03 = new Product();

    }
    @Test
    public void addProduct() {
        productModel01.addProduct(product01);
        productModel01.addProduct(product02);
        productModel01.addProduct(product03);
    }

    @Test
    public void getProduct() {
        List<Product> productList = productModel01.getProduct();
        for (Product product : productList){
            if (product.getLabel().equals(label01)){
                Assert.assertNotNull("Van id: " + product.getId(), product.getId());
                productDb01 = product;
                System.out.println(productDb01.getId()+" : "+productDb01.getUnitLabel());
            }
            if (product.getLabel().equals(label02)){
                Assert.assertNotNull("Van id: " + product.getId(), product.getId());
                productDb02 = product;
                System.out.println(productDb02.getId()+" : "+productDb02.getUnitLabel());
            }
            if (product.getLabel().equals(label03)){
                Assert.assertNotNull("Van id: " + product.getId(), product.getId());
                productDb03 = product;
                System.out.println(productDb03.getId()+" : "+productDb03.getUnitLabel());
            }


        }
    }

    @Test
    public void modProduct() {
//        productModel02 = new ProductModel();
//        String newUL = "10 darabos csomag";
//        productDb01.setUnitLabel(newUL);
//        productModel01.modProduct(productDb01);
//        List<Product> productList01 = productModel02.getProduct();
//        for (Product product01 : productList01){
//            if (product01.getLabel().equals(label01)){
//                Assert.assertEquals("Módisított mértékegység.", product01.getUnitLabel(), newUL);
//            }
//        }
    }

    @Test
    public void getProductName() {
    }

    @Test
    public void removeProduct() {
    }
    @After
    public void end() {
        productModel01.removeProduct(productDb01);
        productModel01.removeProduct(productDb02);
        productModel01.removeProduct(productDb03);
    }
}