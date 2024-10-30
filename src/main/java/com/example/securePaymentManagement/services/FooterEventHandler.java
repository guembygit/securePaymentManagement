package com.example.securePaymentManagement.services;

import java.io.IOException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
public class FooterEventHandler implements IEventHandler {

	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		// Obtenir l'événement PdfDocumentEvent
        PdfDocumentEvent pdfEvent = (PdfDocumentEvent) event;
        PdfDocument pdf = pdfEvent.getDocument();
        int pageNumber = pdf.getNumberOfPages();

        PdfCanvas canvas = new PdfCanvas(pdf.getPage(pageNumber));
        canvas.beginText();
        try {
			canvas.setFontAndSize(PdfFontFactory.createFont(StandardFonts.HELVETICA), 12);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        canvas.setTextMatrix(450, 30); // Ajustez les coordonnées selon vos besoins
        canvas.showText(String.format("Page %s", pageNumber));
        canvas.endText();
	}

}
