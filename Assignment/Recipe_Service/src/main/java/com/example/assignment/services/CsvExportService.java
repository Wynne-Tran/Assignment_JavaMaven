package com.example.assignment.services;

import com.example.assignment.model.Shopping_Cart;
import com.example.assignment.repositories.ShoppingRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class CsvExportService {

    private static final Logger log = getLogger(CsvExportService.class);

    private final ShoppingRepository shoppingRepository;

    public CsvExportService(ShoppingRepository shoppingRepository) {
        this.shoppingRepository = shoppingRepository;
    }

    public void writeToCsv(Writer writer, String email) {
        List<Shopping_Cart> shopping_carts = shoppingRepository.findAll( );
        try (CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
            csvPrinter.printRecord(
                   "****** YOUR SHOPPING CART ****** \n\n");
            for (Shopping_Cart cart: shopping_carts) {
                if (Objects.equals(cart.getUser().getEmail(), email)) {
                    csvPrinter.printRecord(
                            "---Cart ID: " + cart.getId() + "---\n" +
                           "\tCreater: " + cart.getRecipes().getCreater()+ "\n" +
                           "\tTitle Recipe: " + cart.getRecipes().getTitle() + "\n" +
                            "\tDescription: " + cart.getRecipes().getDescription() + "\n" +
                            "\tIngredient: " + cart.getRecipes().getIngredient() + "\n" +
                            "\tPrice: " + cart.getRecipes().getPrice() + "\n");
                }
            }
        } catch (IOException e) {
            log.error("Error While writing CSV ", e);
        }
    }



}

