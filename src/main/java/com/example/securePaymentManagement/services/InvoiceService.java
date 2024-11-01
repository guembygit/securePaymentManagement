package com.example.securePaymentManagement.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.securePaymentManagement.Models.Invoice;
import com.example.securePaymentManagement.Models.UserEvent;
import com.example.securePaymentManagement.Repositories.EventRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.io.exceptions.IOException;
//import java.io.IOException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DashedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

@Service
public class InvoiceService {
	
	@Autowired
	private EventRepository repo;
	/*public String generateInvoicePDF() throws Exception {
        String pdfPath = "invoice_.pdf";
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Ajouter le logo
        //String logoPath = "/public/logo/logo-fraternite.jpeg"; // Chemin vers votre logo
        //Image logo = new Image(ImageDataFactory.create(logoPath));
        //document.add(logo);

        // Titre de la facture
        document.add(new Paragraph("Facture").setFontSize(20).setBold());

        // Informations sur le client
        document.add(new Paragraph("Client: "));
        document.add(new Paragraph("TVA: %"));
        document.add(new Paragraph("Remise: €"));

        // Table des produits
        Table table = new Table(new float[]{1, 3, 2, 2});
        table.addHeaderCell("ID");
        table.addHeaderCell("Produit");
        table.addHeaderCell("Prix Unitaire (€)");
        table.addHeaderCell("Quantité");

        //for (Product product : invoice.getProducts()) {
            table.addCell("data");
            table.addCell("row");
            table.addCell("100");
            table.addCell("1");
       // }

        document.add(table);

        // Montant total
        document.add(new Paragraph("Montant Total: 50000 €"));

        // Générer QR code
        ByteArrayOutputStream qrCodeStream = generateQRCode("https://example.com/invoice/facture.pdf");
        Image qrCodeImage = new Image(ImageDataFactory.create(qrCodeStream.toByteArray()));
        document.add(qrCodeImage);

        // Ajouter pied de page
        document.add(new Paragraph("Merci pour votre achat!"));

        document.close();
        
        document.close();
        return pdfPath; // Retourne le chemin du fichier
    }*/
	
	/*public String generateInvoicePDF() throws Exception {
        String pdfPath = "invoice_.pdf";
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Créer l'en-tête de facture
        //createInvoiceHeader(document, invoice);
        createInvoiceHeader(document);

        // Informations sur le client
        document.add(new Paragraph("Client: AXA"));
        
        // Table des produits
        //createProductTable(document, invoice);
        createProductTable(document);

        // INFOS
        document.add(new Paragraph("Scanner le QRCode"));

        // Ajouter le QR code
        ByteArrayOutputStream qrCodeStream = generateQRCode("https://example.com/invoice/invoice");
        Image qrCodeImage = new Image(ImageDataFactory.create(qrCodeStream.toByteArray()));
        document.add(qrCodeImage);

        // Ajouter le pied de page
        document.add(new Paragraph("Merci pour votre achat!").setFixedPosition(10, 10, 580));
        
        int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {

            // Write aligned text to the specified by parameters point
        	document.showTextAligned(new Paragraph(String.format("page %s of %s", i, numberOfPages)),
                    559, 806, i, TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0);
        }
        
        document.close();
        return pdfPath;
    }

    private void createInvoiceHeader(Document document) throws IOException {
        Table headerTable = new Table(2);
        headerTable.setWidth(UnitValue.createPercentValue(100));
        
        // Ajouter le logo
        //String logoPath = Paths.get("public/logo/logo-fraternite.jpeg").toAbsolutePath().toString();

        String logoPath = "public/logo/logo-fraternite.jpeg"; // Chemin vers votre logo
        Image logo;
		try {
			logo = new Image(ImageDataFactory.create(logoPath));
			headerTable.addCell(logo.setAutoScale(false).setHeight(10).setWidth(10)).setBorder(Border.NO_BORDER);
			
			// Informations de l'entreprise
        headerTable.addCell(new Cell().setBorder(Border.NO_BORDER).add(new Paragraph("Nom de l'entreprise")
            .setFontSize(14).setBold()));
        headerTable.addCell(new Paragraph("Adresse de l'entreprise\nTéléphone: 0123456789\nEmail: info@entreprise.com")).setBorder(Border.NO_BORDER);
        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        document.add(headerTable);
        //document.add(new AreaBreak()); // Saut de page
    }
	
	private void createProductTable(Document document) {
        Table table = new Table(new float[]{1, 3, 2, 2});
        table.setWidth(UnitValue.createPercentValue(100));
        table.addHeaderCell("ID");
        table.addHeaderCell("Produit").setBorder(Border.NO_BORDER);
        table.addHeaderCell("Prix Unitaire (€)");
        table.addHeaderCell("Quantité");

        //for (Product product : invoice.getProducts()) {
            table.addCell(String.valueOf(1)).setBorder(Border.NO_BORDER);
            table.addCell("Jus jdjd jdjjd kdkkd kdkkd dlldl jdjd ldldl kdkdk");
            table.addCell(String.valueOf("100"));
            table.addCell(String.valueOf(2));
        //}
        table.setBorder(Border.NO_BORDER);
        document.add(table);
     // Montant total
        document.add(new Paragraph("Montant Total:  200 €"));
        document.add(new Paragraph("TVA: 0.5%"));
        document.add(new Paragraph("Remise: 0.6 € \n\n\n"));
        
        //document.add(new AreaBreak()); // Saut de page
    }*/
	
	public String generateInvoicePDF(int id) throws Exception {
		
		String pdfPath = "invoice_.pdf";
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdf);
        
        //WATERMAKE
        Table headerTable = new Table(2);
        //String logoPath = Paths.get("public/logo/logo-fraternite.jpeg").toAbsolutePath().toString();
        String imagePath = "public/logo/logo-fraternite.jpeg"; // Chemin vers votre logo
        Image image;
		image = new Image(ImageDataFactory.create(imagePath));		
		float x = pdf.getDefaultPageSize().getWidth()/2;
		float y = pdf.getDefaultPageSize().getHeight()/2;
        image.setFixedPosition(150, 300);
        image.setOpacity(0.1f);
        image.setHeight(200).setWidth(200);
		document.add(image);
		
		pdf.addEventHandler(PdfDocumentEvent.START_PAGE, (IEventHandler) new FooterEventHandler());
		
		//PAGINATE
		/*int numberOfPages = pdf.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {

            // Write aligned text to the specified by parameters point
        	document.showTextAligned(new Paragraph(String.format("page %s of %s", i, numberOfPages)),
                    559, 806, i, TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0);
        }*/
        
                
        float threecol = 190f;
        float twocol = 285f;
        float twocol150 = twocol+150f;
        float twocolumnWidth[]= {twocol150, twocol};
        float threeColumnWidth[]= {threecol, threecol, threecol};
        float fullwidth[]= {threecol*3};
        Paragraph ones = new Paragraph();
        
        List<Object[]> invoices = repo.getInvoiceData(id);
		Object[] invoiceData = invoices.get(0);
        		
        Table table = new Table(twocolumnWidth);
        table.addCell(new Cell().add(new Paragraph("Invoice")).setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedtable = new Table(new float[] {twocol/2, twocol/2});
        nestedtable.addCell(getHeaderTextCell("Invoice No.")).setBold();
        nestedtable.addCell(getHeaderTextCellValue(invoiceData[8].toString()));
        nestedtable.addCell(getHeaderTextCell("Invoice Date")).setBold();
        nestedtable.addCell(getHeaderTextCellValue(invoiceData[9].toString()));
        
        table.addCell(new Cell().add(nestedtable).setBorder(Border.NO_BORDER));
        
        Border gb = new SolidBorder(ColorConstants.GRAY, 2f);
        Table divider = new Table(fullwidth);
        divider.setBorder(gb);
        
        document.add(table);
        document.add(ones);
        document.add(divider);
        document.add(ones);
        
        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getBillingandShippingCell("Billing Information"));
        twoColTable.addCell(getBillingandShippingCell("Member Information"));
        document.add(twoColTable.setMarginBottom(12f));
        
        Table twoColTable2 = new Table(twocolumnWidth);
        twoColTable2.addCell(getCell10fLeft("Compagny", true));
        twoColTable2.addCell(getCell10fLeft("Name", true));
        twoColTable2.addCell(getCell10fLeft("ONG", false));
        twoColTable2.addCell(getCell10fLeft(invoiceData[1].toString(), false));
        document.add(twoColTable2);
        
        Table twoColTable3 = new Table(twocolumnWidth);
        twoColTable3.addCell(getCell10fLeft("City", true));
        twoColTable3.addCell(getCell10fLeft("Address", true));
        twoColTable3.addCell(getCell10fLeft("Paris", false));
        twoColTable3.addCell(getCell10fLeft(invoiceData[3].toString(), false));
        document.add(twoColTable3);
        
        float oneColumnwidth[]= {twocol150};
        
        Table oneColTable1 = new Table(oneColumnwidth);
        oneColTable1.addCell(getCell10fLeft("Address", true));
        oneColTable1.addCell(getCell10fLeft("75000 PARIS, \n île de France", false));
        oneColTable1.addCell(getCell10fLeft("Email", false));
        oneColTable1.addCell(getCell10fLeft("ONG@gmail.com", false));
        document.add(oneColTable1.setMarginBottom(10f));
        
        Table tableDivider2 = new Table(fullwidth);
        Border dgb = new DashedBorder(ColorConstants.GRAY, 0.5f);
        document.add(tableDivider2.setBorder(dgb));
        Paragraph producPara = new Paragraph("Products");
        
        document.add(producPara.setBold());
        Table threeColTable1 = new Table(threeColumnWidth);
        threeColTable1.setBackgroundColor(ColorConstants.BLACK, 0.7f);
        
        threeColTable1.addCell(new Cell().add(new Paragraph("Description")).setBold().setFontColor(ColorConstants.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add(new Paragraph("Quantity")).setBold().setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add(new Paragraph("Price")).setBold().setFontColor(ColorConstants.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        document.add(threeColTable1);
                
        Table threeColTable2 = new Table(threeColumnWidth);
        
        float total = 0;
        //int totalItems = 500;
        //int totalPages = (int) Math.ceil((double) totalItems / 5);
        
    	threeColTable2.addCell(new Cell().add(new Paragraph(invoiceData[4].toString())).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable2.addCell(new Cell().add(new Paragraph("1")).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable2.addCell(new Cell().add(new Paragraph(invoiceData[5].toString())).setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);            
    
        
     // Ajouter le gestionnaire d'événements de pied de page
        
        document.add(threeColTable2.setMarginBottom(20f));
        pdf.addEventHandler(PdfDocumentEvent.END_PAGE, (IEventHandler) new FooterEventHandler());
        
        float onetwo[]= {threecol+125f, threecol*2};
        Table threeColTable4 = new Table(onetwo);
        threeColTable4.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(tableDivider2)).setBorder(Border.NO_BORDER);
        document.add(threeColTable4);
        
        Table threeColTable3 = new Table(threeColumnWidth);        
        threeColTable3.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable3.addCell(new Cell().add(new Paragraph("Total")).setBold().setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable3.addCell(new Cell().add(new Paragraph(invoiceData[5].toString())).setBold().setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        
        document.add(threeColTable3);
        document.add(tableDivider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(ColorConstants.GRAY, 1)).setMarginBottom(15f));
        
        Table tb = new Table(fullwidth);
        tb.addCell(new Cell().add(new Paragraph("TERMS AND CONDITIONS\n")).setBold().setBorder(Border.NO_BORDER));
        
        List<String> TncList = new ArrayList();
        TncList.add("1. Acceptation des Termes En acceptant cette facture, le client reconnaît avoir lu et accepté les présentes conditions générales de vente.");
        TncList.add("2. Prix Tous les prix sont indiqués en [devise] et incluent/excluent [préciser si les taxes sont incluses ou non, par exemple, TVA].");
        TncList.add("3. Paiement Le paiement est dû dans les [nombre de jours] jours suivant la date de facturation. Les paiements peuvent être effectués par [modes de paiement acceptés, par exemple, virement bancaire, carte de crédit].");
        TncList.add("4. Réclamations Toute réclamation concernant la facture ou les produits/services fournis doit être effectuée dans un délai de [nombre de jours] jours suivant la réception de la facture.");
        TncList.add("5. Droit Applicable Ces conditions sont régies par le droit [pays/région]. Tout litige sera soumis aux tribunaux compétents de [localité].");
        TncList.add("6. Modifications [Votre entreprise] se réserve le droit de modifier ces conditions à tout moment. Les modifications seront effectives dès leur publication.");
        
        for(String tnc:TncList) {
        	tb.addCell(new Cell().add(new Paragraph(tnc)).setBorder(Border.NO_BORDER));
        }
        
        document.add(tb);
        
     // Ajouter le QR code
        ByteArrayOutputStream qrCodeStream = generateQRCode("http://192.168.1.120:8080/users_events/invoice?id="+id);
        Image qrCodeImage = new Image(ImageDataFactory.create(qrCodeStream.toByteArray()));
        document.add(qrCodeImage);

        // Ajouter le pied de page
        //document.add(new Paragraph("Merci pour votre achat!").setFixedPosition(10, 10, 580));
        
     
        
        document.close();
        
        return pdfPath;
	}
	
	static Cell getHeaderTextCell(String textValue) {
		return new Cell().add(new Paragraph(textValue)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
	}
	
	static Cell getHeaderTextCellValue(String textValue) {
		return new Cell().add(new Paragraph(textValue)).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
	}
	
	static Cell getBillingandShippingCell(String textValue) {
		return new Cell().add(new Paragraph(textValue)).setFontSize(12f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
	}
	
	static Cell getCell10fLeft(String textValue, Boolean isBold) {
		Cell myCell = new Cell().add(new Paragraph(textValue)).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
		return isBold ? myCell.setBold():myCell;
	}
	
	private Cell createCell(String content, boolean isHeader) {
	    Cell cell = new Cell();
	    cell.add(new Paragraph(content)); // Créer un Paragraph et l'ajouter à la cellule
	    cell.setBorder(null); // Supprime les bordures de la cellule
	    if (isHeader) {
	        cell.setBold(); // Met en gras si c'est un en-tête
	        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY); // Optionnel : couleur de fond pour les en-têtes
	    }
	    return cell;
	}
    
    private ByteArrayOutputStream generateQRCode(String text) throws Exception {
        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(matrix);
        ImageIO.write(qrCodeImage, "png", stream);
        return stream;
    }
}
