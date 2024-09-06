package com.tutorial.apidemo.Springboot.tutorial.models;

import jakarta.persistence.*;

import javax.sound.midi.Sequence;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "tblProduct")
public class Product {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO) // auto-increment
    // you can also use "sequence"
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1 // increment by 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    // Validate = constraint
    @Column(unique = true, nullable = false, length = 300)
    private String productName;
    private int year;
    private Double price;
    private String url;
    public Product() {}
    // calculated field = transient, not exist in MySql
    @Transient
    private int age; // age is calculated from "year"
    private int getAge() {
        return Calendar.getInstance().get(Calendar.YEAR) - year;
    }
    public Product(String productName, int year, Double price, String url) {
        this.productName = productName;
        this.year = year;
        this.price = price;
        this.url = url;
    }
    // POJO - Plain Object Java Object

    @Override
    public String toString() {
        return "Product{" +
                ", productName='" + productName + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", url='" + url + '\'' +
                '}';
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return year == product.year
                && age == product.age
                && Objects.equals(id, product.id)
                && Objects.equals(productName, product.productName)
                && Objects.equals(price, product.price)
                && Objects.equals(url, product.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, year, price, url, age);
    }
}
