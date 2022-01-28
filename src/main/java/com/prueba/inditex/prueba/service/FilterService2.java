package com.prueba.inditex.prueba.service;

import com.prueba.inditex.prueba.model.Product;
import com.prueba.inditex.prueba.model.Size;
import com.prueba.inditex.prueba.model.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FilterService2 {

    public static void main(String[] args) throws IOException {
        System.out.println(filterProducts().stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    private static final String COMMA_DELIMITER = ",";

    public static List<Integer> filterProducts() throws IOException {

        List<Product> products = getProducts();
        List<Size> sizes = getSizes();
        List<Stock> stocks = getStocks();

        Set<Integer> sizesWithStock = stocks.stream()
                .filter(stock -> stock.getQuantity() > 0)
                .map(stock -> stock.getSizeId())
                .collect(Collectors.toSet());

        Set<Integer> productWithStockOrBackSoonNotSpecial = sizes.stream()
                .filter(size -> ((sizesWithStock.contains(size.getId()) || size.isBackSoon()) && !size.isSpecial()))
                .map(size -> size.getProductId()).collect(Collectors.toSet());

        Set<Integer> productWithSpecialSizeWithoutStock = sizes.stream()
                .filter(size -> size.isSpecial())
                .filter(size -> !sizesWithStock.contains(size.getId()))
                .map(size -> size.getProductId()).collect(Collectors.toSet());

        productWithStockOrBackSoonNotSpecial.removeAll(productWithSpecialSizeWithoutStock);

        List<Integer> productsOrdered = products.stream()
                .filter(product -> productWithStockOrBackSoonNotSpecial.contains(product.getId()))
                .sorted(Comparator.comparing(Product::getSequence))
                .map(product -> product.getId()).collect(Collectors.toList());

        return productsOrdered;
    }

    public static List<Product> getProducts() throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Jose/Projects/courses/prueba/target/classes/product.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }
        List<Product> products = new ArrayList<>();
        records.stream().forEach(strings -> products.add(
                new Product(Integer.parseInt(strings.get(0).trim()), Integer.parseInt(strings.get(1).trim()))));
        return products;
    }

    public static List<Size> getSizes() throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Jose/Projects/courses/prueba/target/classes/size.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }
        List<Size> sizes = new ArrayList<>();
        records.stream().forEach(strings -> sizes.add(
                new Size(Integer.parseInt(strings.get(0).trim()), Integer.parseInt(strings.get(1).trim()),
                        Boolean.parseBoolean(strings.get(2).trim()), Boolean.parseBoolean(strings.get(3).trim()))));
        return sizes;
    }

    public static List<Stock> getStocks() throws IOException {
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Jose/Projects/courses/prueba/target/classes/stock.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        }
        List<Stock> stocks = new ArrayList<>();
        records.stream().forEach(strings -> stocks.add(
                new Stock(Integer.parseInt(strings.get(0).trim()), Integer.parseInt(strings.get(1).trim()))));
        return stocks;
    }
}
