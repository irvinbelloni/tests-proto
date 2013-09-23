package com.ossia.test.web.admin;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	private Properties contentProperties = new Properties() ; 
	
	@Autowired
	private TestSheetService testSheetService;
	
	@Autowired
	private EvaluationService evaluationService;
	
	private final Font FONT_TITLE1 = FontFactory.getFont(FontFactory.HELVETICA,
			14, Font.BOLD, Color.RED);
	private final Font FONT_TITLE2 = FontFactory.getFont(
			FontFactory.HELVETICA, 14, Font.NORMAL, Color.BLACK);
	private static final Font FONT_TITLE3 = FontFactory.getFont(FontFactory.TIMES_ITALIC,
			16, Font.UNDERLINE, Color.BLUE);
	private static final Font FONT_TITLE4 = FontFactory.getFont(FontFactory.COURIER,
			16, Font.BOLD, Color.GREEN);
	
	private final Font FONT_NORMAL_UNDERLINE = FontFactory.getFont(FontFactory.TIMES_ROMAN,
			14, Font.UNDERLINE, Color.ORANGE);
	private final Font FONT_NORMAL = FontFactory.getFont(FontFactory.TIMES_ROMAN,
			14, Font.NORMAL, Color.BLACK);
	
	private final Font FONT_CODE = FontFactory.getFont(
			FontFactory.COURIER, 12, Font.ITALIC, Color.GRAY);
	

	{
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		java.io.InputStream is = (java.io.InputStream) cl.getResourceAsStream("export_itext_content.properties") ; 
		try {
			contentProperties.load(is) ;
		} catch (IOException e) {
			log.warn(e) ; 
		}
	}
	@RequestMapping(value="/evaluation")
	public void printTestResultWithDetails(@RequestParam(value = "id") String idRequestParam , HttpServletResponse response) {
		log.debug("") ; 
		
		try {
			File file = File.createTempFile (contentProperties.getProperty(ITEXT_DOCUMENT_NAME) , 
					contentProperties.getProperty(ITEXT_DOCUMENT_EXTENSION));
			FileOutputStream fos = new FileOutputStream(file) ; 
			
			Document document = instanciateDocument(fos) ; 
			Evaluation evalParamEntree = evaluationService.getEvaluationById(Integer.parseInt(idRequestParam)) ; 
			
			document = logiqueMetier (evalParamEntree , document) ;
			closeDocument(document) ;
			fos.close() ; 
			
			FileInputStream fis = new FileInputStream(file) ;
			
			IOUtils.copy(fis, response.getOutputStream()) ; 
			
			response.setHeader("Content-Disposition", "attachment; filename=somefile.pdf"); 
			response.setContentType("application/pdf");
			response.flushBuffer();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
	}

	private Document instanciateDocument(FileOutputStream fos) {
		Document document = new Document(PageSize.A4);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, fos );

			writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage
					| PdfWriter.PageModeUseNone | PdfWriter.HideMenubar
					| PdfWriter.HideToolbar | PdfWriter.HideWindowUI);

			document.addTitle(contentProperties.getProperty(ITEXT_DOCUMENT_TITLE));
			document.addAuthor(contentProperties.getProperty(ITEXT_DOCUMENT_AUTHOR));
			document.addSubject(contentProperties.getProperty(ITEXT_DOCUMENT_SUBJECT));
			document.addKeywords(contentProperties.getProperty(ITEXT_DOCUMENT_KEYWORDS));

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
	
	private Chunk createChunk (Font font , String property , String param) {
		Chunk chunk = new Chunk( concatStrings( contentProperties.getProperty(property) , param) , font) ; 
		return chunk ; 
	}
	
	private Chunk createChunk (Font font , String property) {
		Chunk chunk = new Chunk( contentProperties.getProperty(property) , font) ; 
		return chunk ; 
	}
	
	private Chunk createSimpleChunk (Font font , String property) {
		Chunk chunk = new Chunk( property != null ? property : "" , font) ; 
		return chunk ; 
	}

	private void addPhraseAsTitle (Chapter chapter , String property , String param , String value) {
		Paragraph paragraph = new Paragraph("");
		paragraph.setLeading(20f) ; 
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

		Phrase phrase = new Phrase();
		phrase.add(createChunk(FONT_TITLE1, property , param)) ;
		phrase.add(createSimpleChunk(FONT_TITLE2, value)) ;
		
		paragraph.add(phrase) ; 
		chapter.add(paragraph) ; 
	}
	
	private void addPhraseAsTitle (Chapter chapter , String property , String param) {
		Paragraph paragraph = new Paragraph("");
		paragraph.setLeading(20f) ; 
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

		Phrase phrase = new Phrase();
		phrase.add(createChunk(FONT_TITLE1, property)) ;
		phrase.add(createSimpleChunk(FONT_TITLE2, param)) ;
		
		paragraph.add(phrase) ; 
		chapter.add(paragraph) ; 
	}
	
	private void addPhraseAsNormal (Chapter chapter , String property , String param) {
		Paragraph paragraph = new Paragraph("");
		paragraph.setLeading(20f) ; 
		paragraph.setAlignment(Element.ALIGN_LEFT);
		
		Phrase phrase = new Phrase();
		phrase.add(createChunk(FONT_NORMAL_UNDERLINE, property)) ;
		phrase.add(createSimpleChunk(FONT_NORMAL, param)) ;
		
		paragraph.add(phrase) ; 
		chapter.add(paragraph) ;
	}
	
	private void addPhraseAsCode (Chapter chapter , String param) {
		Paragraph paragraph = new Paragraph("");
		paragraph.setLeading(20f) ; 
		paragraph.setAlignment(Element.ALIGN_LEFT);
		
		Phrase phrase = new Phrase();
		phrase.add(createSimpleChunk(FONT_CODE, param)) ;
		
		paragraph.add(phrase) ; 
		chapter.add(paragraph) ;
	}
	
	/*
	 * FIXME 
	 * A - Le candidat termine et valide son test !! 
	 * on assume que l'évaluation passée en paramètre a un statut permettant d'effectuer cette action !!!
	 */
	private Document logiqueMetier (Evaluation evalParamEntree , Document document) 
		throws MalformedURLException, IOException, URISyntaxException, DocumentException {
		Chapter chapter = new Chapter(contentProperties.getProperty(ITEXT_DOCUMENT_TITLE) , 1) ;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(contentProperties.getProperty(ITEXT_DOCUMENT_CHAPTER_LOGO_PATH));
		
		Image image = Image.getInstance(url.toURI().toString());
		chapter.add(image);
		chapter.add(new Paragraph("\n")) ;
		addPhraseAsTitle(chapter, ITEXT_DOCUMENT_CHAPTER_NOM , evalParamEntree.getProfil().getNom()) ; 
		addPhraseAsTitle(chapter, ITEXT_DOCUMENT_CHAPTER_PRENOM , evalParamEntree.getProfil().getPrenom()) ; 
		chapter.add(new Paragraph("\n")) ;

		/* Informations calculées */ 
		addPhraseAsTitle(chapter, ITEXT_DOCUMENT_CHAPTER_TESTNAME , evalParamEntree.getTest().getIntitule()) ; 

		// note globale
		addPhraseAsTitle(chapter , ITEXT_DOCUMENT_CHAPTER_EMPLACEMENT , "" ) ; 
		
		String noteGlobale = evaluationService.determinerNoteGlobale (evalParamEntree) ;
		addPhraseAsTitle(chapter , ITEXT_DOCUMENT_CHAPTER_NOTE_GLOBALE , noteGlobale ) ; 
		addPhraseAsTitle(chapter , ITEXT_DOCUMENT_CHAPTER_DUREE_TEST , evalParamEntree.getTest().getDuree()+"" ) ; 
		addPhraseAsTitle(chapter , ITEXT_DOCUMENT_CHAPTER_DEBUT_TEST , evalParamEntree.getStartTime().toString() ) ;  
		addPhraseAsTitle(chapter , ITEXT_DOCUMENT_CHAPTER_FIN_TEST , evalParamEntree.getEndTime().toString()) ;
		chapter.add(new Paragraph("\n")) ; 

		for (Niveau object : Niveau.values()) {
			String noteParNiveau = evaluationService.determinerNoteParNiveau (evalParamEntree , object) ; 
			addPhraseAsTitle(chapter , ITEXT_DOCUMENT_CHAPTER_NOTE , object.getValue() , noteParNiveau) ; 
		}
		chapter.add(new Paragraph("\n")) ; 
		/* Affichage du tests détaillé plus correction */
		
		int i = 1;
		int nbrQuestion = evalParamEntree.getResponses().size() ; 
		
		for (Response response : evalParamEntree.getResponses()) {
			Paragraph paragraph =  new Paragraph(contentProperties.getProperty(ITEXT_DOCUMENT_CHAPTER_QUESTION).concat(" "+i+"/"+nbrQuestion) , FONT_TITLE3 ) ; 
			paragraph.setAlignment(Element.ALIGN_CENTER);
			chapter.add(paragraph) ;
			chapter.add(new Paragraph("\n")) ; 
			
			addPhraseAsNormal( chapter , ITEXT_DOCUMENT_CHAPTER_TITRE , response.getQuestion().getIntitule() ) ;
			addPhraseAsNormal( chapter , ITEXT_DOCUMENT_CHAPTER_NIVEAU , response.getQuestion().getNiveau().getValue() ) ;
			
			addPhraseAsCode  ( chapter ,  response.getQuestion().getContenu() ) ; 
			chapter.add(new Paragraph("\n")) ; 
			
			addListLikeParagraphs (chapter, ITEXT_DOCUMENT_CHAPTER_STATEMENT , response.getQuestion().getPropositionsReponses()) ;
			addListLikeParagraphs (chapter, ITEXT_DOCUMENT_CHAPTER_CORRECTION , evaluationService.determinerPropositionsCorrectesByReponse(response) ) ;
			addListLikeParagraphs (chapter, ITEXT_DOCUMENT_CHAPTER_CANDIDAT , response.getReponsesChoisies()) ;
			
			i++ ; 
		}
		chapter.add(new Paragraph("\n")) ;
		document.add(chapter) ; 
		return document ; 
	}
	
	public void addListLikeParagraphs (Chapter chapter, String param , Collection<PropositionReponse> collection) {
		chapter.add( new Paragraph(contentProperties.getProperty(param) , FONT_TITLE4 )) ;
		chapter.add(new Paragraph("\n")) ; 
		
		List liste = new List(true, collection.size());
		liste.setNumbered(true) ; 
		liste.setAlignindent(true) ; 
		liste.setAutoindent(true) ; 
		for (PropositionReponse propositionReponse : collection) {
			liste.add(new ListItem (new Phrase (propositionReponse.getValeur()))) ;
		}
		chapter.add(liste) ; 
		chapter.add(new Paragraph("\n")) ;
	}

	private void closeDocument(Document document) {
		document.close();
	}
}