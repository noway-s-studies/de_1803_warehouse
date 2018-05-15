package hu.unideb.inf.warehouse.model;

import hu.unideb.inf.warehouse.pojo.Product;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProductModelTest {

    ProductModel productModel01 = new ProductModel();
    Product product01 = new Product();
    Product product02 = new Product();
    Product product03 = new Product();
    Product productDb01 = new Product();
    Product productDb02 = new Product();
    Product productDb03 = new Product();
    String label01 = "";
    String label02 = "";
    String label03 = "";
    String text = "\n";


    @Test
    public void initProductModelTest() {
        label01 = "TóróRudi_Test";
        label02 = "CocaCola_Test";
        label03 = "Tej_Test";

        /**
         * Product()
         */
        product01 = new Product(label01, "1 darab");
        product02 = new Product(label02, "2,5 liter");
        product03 = new Product(label03, "1 liter");

        /**
         * addProduct()
         */
        productModel01.addProduct(product01);
        productModel01.addProduct(product02);
        productModel01.addProduct(product03);

        /**
         * getProduct()
         */
        List<Product> productList = productModel01.getProduct();
        for (Product product : productList){
            if (product.getLabel().equals(label01)){
                Assert.assertNotNull("Van id: " + product.getId(), product.getId());
                productDb01 = product;
                text += "Id: "+product.getId()+", Label: "+product.getLabel() +", Mértékegység: "+product.getUnitLabel() +"\n";
            }
            if (product.getLabel().equals(label02)){
                Assert.assertNotNull("Van id: " + product.getId(), product.getId());
                productDb02 = product;
                text += "Id: "+product.getId()+", Label: "+product.getLabel() +", Mértékegység: "+product.getUnitLabel()+"\n";
            }
            if (product.getLabel().equals(label03)){
                Assert.assertNotNull("Van id: " + product.getId(), product.getId());
                productDb03 = product;
                text += "Id: "+product.getId()+", Label: "+product.getLabel() +", Mértékegység: "+product.getUnitLabel()+"\n";
            }
        }

        /**
         * setStatus()
         * getStatus()
         * modProduct()
         */
        Assert.assertTrue("Van beállított státusz érték ami igaz.", productDb01.getStatus());
        productDb01.setStatus(false);
        productModel01.modProduct(productDb01);
        List<Product> productList2 = productModel01.getProduct();
        for (Product product : productList2){
            if (product.getLabel().equals(label01)){
                assertFalse("Beállított státusz érték hamis.", product.getStatus());
                text += "Státusz mod: " + product.getStatus() + "\n";
            }
        }

        /**
         * getProductName()
         */
        List<String> productList3 = productModel01.getProductName();
        Assert.assertNotNull("Üress áru név lista", productList);
        for (String label : productList3)
            text += label + "\n";

        /**
         * removeProduct()
         */
        productModel01.removeProduct(productDb01);
        productModel01.removeProduct(productDb02);
        productModel01.removeProduct(productDb03);
        List<String> productListEND = productModel01.getProductName();
        Assert.assertTrue("Sikeres törlés", productListEND.size() == 0);

        System.out.println(text);
    }
}