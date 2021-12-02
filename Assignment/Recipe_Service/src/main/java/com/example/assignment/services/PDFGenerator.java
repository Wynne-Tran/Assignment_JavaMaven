package com.example.assignment.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import com.example.assignment.model.ReportSale;
import com.example.assignment.model.Shopping_Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class PDFGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PDFGenerator.class);

    public static ByteArrayInputStream cartPDFReport(List<Shopping_Cart> shopping_carts, List<ReportSale> report, String email) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            // Add Text to PDF file ->
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
                    PdfPCell imageCell = new PdfPCell(new Phrase(cart.getRecipes().getImage()));
                    imageCell.setPaddingLeft(4);
                    imageCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(imageCell);

                    PdfPCell ingredientCell = new PdfPCell(new Phrase(cart.getRecipes().getIngredient()));
                    ingredientCell .setPaddingLeft(4);
                    ingredientCell .setVerticalAlignment(Element.ALIGN_MIDDLE);
                    ingredientCell .setHorizontalAlignment(Element.ALIGN_LEFT);
                    table.addCell(ingredientCell );

                    PdfPCell priceCell = new PdfPCell(new Phrase(String.valueOf(cart.getRecipes().getPrice())));
                    priceCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    priceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    priceCell.setPaddingRight(4);
                    table.addCell(priceCell);

                    PdfPCell quantityCell = new PdfPCell(new Phrase(String.valueOf(cart.getQuantity())));
                    quantityCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    quantityCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    quantityCell.setPaddingRight(4);
                    table.addCell(quantityCell );

                    PdfPCell totalCell = new PdfPCell(new Phrase(String.valueOf(cart.getTotalPrice())));
                    totalCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
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
            Paragraph sentence = new Paragraph("Payment: $"+  paymentCell, footer);
            sentence.setAlignment(Element.ALIGN_RIGHT);
            document.add(sentence);
            document.add(Chunk.NEWLINE);


            document.close();
        } catch (DocumentException e) {
            logger.error(e.toString());
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}