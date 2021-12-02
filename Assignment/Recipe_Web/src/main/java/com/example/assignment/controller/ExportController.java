package com.example.assignment.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.example.assignment.model.ReportSale;
import com.example.assignment.model.Shopping_Cart;
import com.example.assignment.repositories.ReportSaleRespository;
import com.example.assignment.repositories.ShoppingRepository;
import com.example.assignment.services.PDFGenerator;
import com.example.assignment.services.ReportSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/pdf")
public class ExportController {

    @Autowired
    ShoppingRepository shoppingRepository;
    @Autowired
    ReportSaleRespository reportSaleRespository;


    @GetMapping(value = "/export", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> cartReport(HttpSession session) throws IOException {
        List<Shopping_Cart> shopping_carts = (List<Shopping_Cart>) shoppingRepository.findAll();
        List<ReportSale> report= (List<ReportSale>) reportSaleRespository.findAll();
        String email = (String)session.getAttribute("email");
        String username = (String) session.getAttribute("username");
        ByteArrayInputStream bis = PDFGenerator.cartPDFReport(shopping_carts, report, email, username);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=employees.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}