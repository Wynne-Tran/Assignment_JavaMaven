
/* ********************************************************************************
 * Project: Create a Recipe Project Using Spring/Spring Boot
 * Assignment: 1 & 2
 * Author(s): Wynne Tran
 * Student Number: 101161665
 * Date: Nov 4 2021
 * Description:  this page use to create PDF page, how my PDF look when export file
 ******************************************************************************** */


package com.example.assignment.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.example.assignment.model.ReportSale;
import com.example.assignment.model.Shopping_Cart;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.itextpdf.text.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import javax.swing.border.Border;

public class PDFGenerator {
    private static final Logger logger = LoggerFactory.getLogger(PDFGenerator.class);
    public static ByteArrayInputStream cartPDFReport(List<Shopping_Cart> shopping_carts, List<ReportSale> report, String email, String username) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
            PdfPTable nameCompany = new PdfPTable(4);
            nameCompany.setWidths(new float[] { 5, 45, 30, 20 });
            String filelogo = "Assignment/Recipe_Web/src/main/resources/static/image/logo.png";
            Image logo= Image.getInstance(filelogo);
            logo.scaleAbsolute(20f, 20f);
            PdfPCell image1Cell = new PdfPCell(logo);
            image1Cell.setVerticalAlignment(Element.ALIGN_LEFT);
            PdfPCell company = new PdfPCell(new Phrase("The Achievers Group"));
            PdfPCell date= new PdfPCell(new Phrase(String.valueOf(LocalDate.now())));
            PdfPCell nuCell= new PdfPCell(new Phrase(""));

            company.setVerticalAlignment(Element.ALIGN_LEFT);
            date.setVerticalAlignment(Element.ALIGN_RIGHT);
            nameCompany.addCell(image1Cell).setBorder(Rectangle.NO_BORDER);
            nameCompany.addCell(company).setBorder(Rectangle.NO_BORDER);
            nameCompany.addCell(nuCell).setBorder(Rectangle.NO_BORDER);
            nameCompany.addCell(date).setBorder(Rectangle.NO_BORDER);
            document.add(nameCompany);
            document.add(Chunk.NEWLINE);

            // Add Text to PDF file ->
            Font font2 = FontFactory.getFont(FontFactory.COURIER, 18, BaseColor.BLACK);
            Paragraph customer = new Paragraph(username, font2);
            customer.setAlignment(Element.ALIGN_CENTER);
            document.add(customer);
            customer.add(Chunk.NEWLINE);

            Font font3 = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
            Paragraph emailAddress= new Paragraph(email, font3);
            emailAddress.setAlignment(Element.ALIGN_CENTER);
            document.add(emailAddress);
            customer.add(Chunk.NEWLINE);

            Font font = FontFactory.getFont(FontFactory.COURIER, 24, BaseColor.BLACK);
            Paragraph para = new Paragraph("Shopping Cart", font);
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(5);
            // Add PDF Table Header ->
            Stream.of("Image", "Ingredient", "Price", "Quantity", "Total").forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setBorderWidth(2);
                header.setPhrase(new Phrase(headerTitle, headFont));
                table.addCell(header);
            });

            for (Shopping_Cart cart : shopping_carts) {
                if(Objects.equals(cart.getUser_email(), email)){

                    String filename = "Assignment/Recipe_Web/src/main/resources/static/image/" + cart.getRecipes().getImage();
                    Image image = Image.getInstance(filename);
                    image.scaleAbsolute(50f, 50f);
                    PdfPCell imageCell = new PdfPCell(image);
                    imageCell.setPadding(4);
                    imageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(imageCell);

                    PdfPCell ingredientCell = new PdfPCell(new Phrase(cart.getRecipes().getIngredient()));
                    ingredientCell .setPaddingLeft(4);
                    ingredientCell .setVerticalAlignment(Element.ALIGN_MIDDLE);
                    ingredientCell .setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(ingredientCell );

                    PdfPCell priceCell = new PdfPCell(new Phrase(String.valueOf(cart.getRecipes().getPrice())));
                    priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    priceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    priceCell.setPaddingRight(4);
                    table.addCell(priceCell);

                    PdfPCell quantityCell = new PdfPCell(new Phrase(String.valueOf(cart.getQuantity())));
                    quantityCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    quantityCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    quantityCell.setPaddingRight(4);
                    table.addCell(quantityCell );

                    PdfPCell totalCell = new PdfPCell(new Phrase(String.valueOf(cart.getTotalPrice())));
                    totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    totalCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    totalCell.setPaddingRight(4);
                    table.addCell(totalCell);
                }

            }
            document.add(table);

            // Add Text to PDF file ->
            Font footer = FontFactory.getFont(FontFactory.COURIER, 24, BaseColor.BLACK);
            String paymentCell = "";
            for (ReportSale printReport : report) {
                if(Objects.equals(printReport.getDatePayment(), LocalDate.now()) && Objects.equals(printReport.getUser().getEmail(), email)){
                    paymentCell = String.valueOf(printReport.getPayment());
                }
            }

            Paragraph sentence = new Paragraph("Total Payment: $"+  paymentCell, footer);
            sentence.setAlignment(Element.ALIGN_CENTER);
            document.add(sentence);
            document.add(Chunk.NEWLINE);

            document.close();
        } catch (DocumentException e) {
            logger.error(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }


}