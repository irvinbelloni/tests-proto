package com.ossia.test.web.handler;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfWriter;
import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Niveau;
import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Response;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.TestSheetService;

@Controller
@RequestMapping(value="/print")
public class ResultsPrinter implements ResultsPrinterInterface {

	private final Log log = LogFactory.getLog(getClass());
	private Properties systemProperties = System.getProperties() ; 

	@Autowired
	private TestSheetService testSheetService;

	@Autowired
	private EvaluationService evaluationService;

//	private final Font FONT_TITLE1 = FontFactory.getFont(FontFactory.HELVETICA,
//			35, Font.BOLD, Color.RED);
//	private final Font FONT_TITLE2 = FontFactory.getFont(
//			FontFactory.TIMES_ROMAN, 26, Font.ITALIC, Color.ORANGE);
	
	private final Font FONT_NORMAL = FontFactory.getFont(FontFactory.COURIER,
			14, Font.NORMAL, Color.BLACK);
//	private final Font FONT_NORMAL_UNDERLINE = FontFactory.getFont(FontFactory.COURIER,
//			14, Font.UNDERLINE, Color.BLACK);
	private final Font FONT_CODE = FontFactory.getFont(
			FontFactory.TIMES_ROMAN, 12, Font.NORMAL, Color.GRAY);

	@ResponseBody
	@RequestMapping(value="/evaluation")
	public FileSystemResource printTestResultWithDetails(@RequestParam(value = "id") String idRequestParam , HttpServletResponse response) {
		log.debug("") ; 
		
		try {
			File file = File.createTempFile (systemProperties.getProperty(ITEXT_DOCUMENT_NAME) , 
					systemProperties.getProperty(ITEXT_DOCUMENT_EXTENSION));
			FileOutputStream fos = new FileOutputStream(file) ; 
			
			Document document = instanciateDocument(fos) ; 
			Evaluation evalParamEntree = evaluationService.getEvaluationById(Integer.parseInt(idRequestParam)) ; 
			
			document = logiqueMetier (evalParamEntree , document) ;
			closeDocument(document) ; 
			
//			IOUtils.copy(input, output)
//			response.setHeader("Content-Disposition", "attachment; filename=somefile.pdf"); 
//			response.setContentType("application/pdf");
//			response.flushBuffer();
			
			return new FileSystemResource(file) ; 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		
		return null ; 
	}

	private Document instanciateDocument(FileOutputStream fos) {
		Document document = new Document(PageSize.A4);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, fos );

			writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage
					| PdfWriter.PageModeUseNone | PdfWriter.HideMenubar
					| PdfWriter.HideToolbar | PdfWriter.HideWindowUI);

			document.addTitle(systemProperties.getProperty(ITEXT_DOCUMENT_TITLE));
			document.addAuthor(systemProperties.getProperty(ITEXT_DOCUMENT_AUTHOR));
			document.addSubject(systemProperties.getProperty(ITEXT_DOCUMENT_SUBJECT));
			document.addKeywords(systemProperties.getProperty(ITEXT_DOCUMENT_KEYWORDS));

			document.open();
			return document;
		} catch (DocumentException de) {
			de.printStackTrace();
		}
		return null;
	}
	
	private String concatStrings (String constante , String param ) {
		return constante.replace("#", param) ; 
	}
	
	private Chunk createChunkWithFont (Font font , String property , String param) {
		Chunk chunk = new Chunk(concatStrings(systemProperties.getProperty(property) , param )  , font) ; 
		return chunk ; 
	}
	
	private Chunk createChunkWithoutFont (String property , String param) {
		Chunk chunk = new Chunk( systemProperties.getProperty(property).concat(param)  , FONT_NORMAL) ; 
		return chunk ; 
	}
	
	private Chunk createSimpleChunk (String property , Font font) {
		Chunk chunk = new Chunk( property , font) ; 
		return chunk ; 
	}
	
	/*
	 * FIXME 
	 * A - Le candidat termine et valide son test !! 
	 * on assume que l'évaluation passée en paramètre a un statut permettant d'effectuer cette action !!!
	 * 
	 */
	private Document logiqueMetier (Evaluation evalParamEntree , Document document) 
		throws MalformedURLException, IOException, URISyntaxException, DocumentException {
		Chapter chapter = new Chapter("chapter 1" , 1) ;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(systemProperties.getProperty(ITEXT_DOCUMENT_CHAPTER_LOGO_PATH));
		
		Image image = Image.getInstance(url.toURI().toString());
		chapter.add(image);

		Phrase phrase = new Phrase();
		phrase.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_NOM , evalParamEntree.getProfil().getNom()) ) ; 
		phrase.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_PRENOM , evalParamEntree.getProfil().getPrenom()) ) ; 
		phrase.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_TESTNAME , evalParamEntree.getTest().getIntitule()) ) ; 
		chapter.add(phrase) ; 
		
		/* Informations calculées */ 

		// note globale
		Paragraph paragraph = new Paragraph("paragraph");
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
		paragraph.setIndentationLeft(10f);
		
		paragraph.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_EMPLACEMENT , "" )) ; 
		
		String noteGlobale = evaluationService.determinerNoteGlobale (evalParamEntree) ;
		paragraph.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_NOTE_GLOBALE , noteGlobale )) ; 
		paragraph.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_DUREE_TEST , evalParamEntree.getTest().getDuree()+"" )) ; 
		
		paragraph.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_DEBUT_TEST , evalParamEntree.getStartTime().toString())) ;  
		paragraph.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_DEBUT_TEST , evalParamEntree.getEndTime().toString())) ;
		
		for (Niveau object : Niveau.values()) {
			String noteParNiveau = evaluationService.determinerNoteParNiveau (evalParamEntree , object) ; 
			paragraph.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_NOTE , object.getValue() ).append(noteParNiveau)) ; 
		}
		chapter.add(paragraph);
		
		/* Affichage du tests détaillé plus correction */
		List liste = new List(true, evalParamEntree.getResponses().size());
		liste.setNumbered(true) ; 
		
		for (Response response : evalParamEntree.getResponses()) {
			phrase = new Phrase();
			Set<PropositionReponse> reponseChoisie = response.getReponsesChoisies() ; 
			
			phrase.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_QUESTION , response.getQuestion().getIntitule() )) ;
			phrase.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_NIVEAU , response.getQuestion().getNiveau().getValue() )) ;
			phrase.add( createSimpleChunk( response.getQuestion().getContenu(), FONT_CODE)) ; 
			
			for (PropositionReponse propositionReponse : response.getQuestion().getPropositionsReponses()) {
				phrase.add( createChunkWithFont( FONT_NORMAL ,ITEXT_DOCUMENT_CHAPTER_STATEMENT , propositionReponse.getValeur() )) ;
			}
			
			for (PropositionReponse propositionReponse : reponseChoisie) {
				phrase.add( createChunkWithoutFont(ITEXT_DOCUMENT_CHAPTER_CANDIDAT , propositionReponse.getValeur() )) ;
			}
			
			liste.add(new ListItem(phrase));
		}
		paragraph.add(liste);
		
		chapter.add(paragraph);
		document.add(chapter) ; 
		return document ; 
	}

//	private void addContentToDocument(Document document)
//			throws DocumentException, MalformedURLException, IOException {
//		Table tableau = new Table(2, 2);
//		tableau.setAutoFillEmptyCells(true);
//		tableau.setPadding(2);
//
//		Cell cell = new Cell("1.1");
//		cell.setHeader(true);
//		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//		cell.setBackgroundColor(Color.YELLOW);
//		tableau.addCell(cell, new Point(1, 0));
//		tableau.addCell("2.2");
//
//		document.add(tableau);
//	}

	private void closeDocument(Document document) {
		document.close();
	}
}
