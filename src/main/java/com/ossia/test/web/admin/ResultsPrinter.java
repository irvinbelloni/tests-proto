package com.ossia.test.web.admin;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Niveau;
import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Response;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.TestSheetService;

@Controller
@RequestMapping(value="/admin/print")
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
	

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void printTest(@RequestParam(value = "id") String idRequestParam , HttpServletResponse response ) {
		
//		Integer identifier = Integer.parseInt(idRequestParam) ;  
//		TestSheet testSheetToPrint = testSheetService.getTestSheetById( identifier ) ; 
		
		// TODO TDS - Complete implementation 
		
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

	private void addPhraseAsTitle (Document document , String property , String param , String value) throws DocumentException {
		Paragraph paragraph = new Paragraph("");
		paragraph.setLeading(20f) ; 
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

		Phrase phrase = new Phrase();
		phrase.add(createChunk(FONT_TITLE1, property , param)) ;
		phrase.add(createSimpleChunk(FONT_TITLE2, value)) ;
		
		paragraph.add(phrase) ; 
		document.add(paragraph) ; 
	}
	
	private void addPhraseAsTitle (Document document , String property , String param) throws DocumentException {
		Paragraph paragraph = new Paragraph("");
		paragraph.setLeading(20f) ; 
		paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

		Phrase phrase = new Phrase();
		phrase.add(createChunk(FONT_TITLE1, property)) ;
		phrase.add(createSimpleChunk(FONT_TITLE2, param)) ;
		
		paragraph.add(phrase) ; 
		document.add(paragraph) ; 
	}
	
	private void addPhraseAsNormal (Document document , String property , String param) throws DocumentException {
		Paragraph paragraph = new Paragraph("");
		paragraph.setLeading(20f) ; 
		paragraph.setAlignment(Element.ALIGN_LEFT);
		
		Phrase phrase = new Phrase();
		phrase.add(createChunk(FONT_NORMAL_UNDERLINE, property)) ;
		phrase.add(createSimpleChunk(FONT_NORMAL, param)) ;
		
		paragraph.add(phrase) ; 
		document.add(paragraph) ;
	}
	
	private void addPhraseAsCode (Document document , String param) throws DocumentException {
		Paragraph paragraph = new Paragraph("");
		paragraph.setLeading(20f) ; 
		paragraph.setAlignment(Element.ALIGN_LEFT);
		
		Phrase phrase = new Phrase();
		phrase.add(createSimpleChunk(FONT_CODE, param)) ;
		
		paragraph.add(phrase) ; 
		document.add(paragraph) ;
	}
	
	/*
	 * FIXME 
	 * A - Le candidat termine et valide son test !! 
	 * on assume que l'évaluation passée en paramètre a un statut permettant d'effectuer cette action !!!
	 */
	private Document logiqueMetier (Evaluation evalParamEntree , Document document) 
		throws MalformedURLException, IOException, URISyntaxException, DocumentException {
//		Chapter chapter = new Chapter(contentProperties.getProperty(ITEXT_DOCUMENT_TITLE) , 1) ;
		
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = classLoader.getResource(contentProperties.getProperty(ITEXT_DOCUMENT_CHAPTER_LOGO_PATH));
		
		Image image = Image.getInstance(url.toURI().toString());
		document.add(image);
		document.add(new Paragraph("\n")) ;
		addPhraseAsTitle(document, ITEXT_DOCUMENT_CHAPTER_NOM , evalParamEntree.getProfil().getNom()) ; 
		addPhraseAsTitle(document, ITEXT_DOCUMENT_CHAPTER_PRENOM , evalParamEntree.getProfil().getPrenom()) ; 
		document.add(new Paragraph("\n")) ;

		/* Informations calculées */ 
		addPhraseAsTitle(document, ITEXT_DOCUMENT_CHAPTER_TESTNAME , evalParamEntree.getTest().getIntitule()) ; 

		// note globale
		addPhraseAsTitle(document , ITEXT_DOCUMENT_CHAPTER_EMPLACEMENT , "" ) ; 
		
		String noteGlobale = evaluationService.determinerNoteGlobale (evalParamEntree) ;
		addPhraseAsTitle(document , ITEXT_DOCUMENT_CHAPTER_NOTE_GLOBALE , noteGlobale ) ; 
		addPhraseAsTitle(document , ITEXT_DOCUMENT_CHAPTER_DUREE_TEST , evalParamEntree.getTest().getDuree()+"mn" ) ; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YY HH:mm");
		addPhraseAsTitle(document , ITEXT_DOCUMENT_CHAPTER_DEBUT_TEST , sdf.format(evalParamEntree.getStartTime())) ;  
		addPhraseAsTitle(document , ITEXT_DOCUMENT_CHAPTER_FIN_TEST , sdf.format(evalParamEntree.getEndTime())) ;
		document.add(new Paragraph("\n")) ; 

		for (Niveau object : Niveau.values()) {
			String noteParNiveau = evaluationService.determinerNoteParNiveau (evalParamEntree , object) ; 
			addPhraseAsTitle(document , ITEXT_DOCUMENT_CHAPTER_NOTE , object.getValue() , noteParNiveau) ; 
		}
		document.add(new Paragraph("\n")) ; 
		/* Affichage du tests détaillé plus correction */
		
		int i = 1;
		int nbrQuestion = evalParamEntree.getResponses().size() ; 
		
		URL urlCorrect = classLoader.getResource("correct-icon.png");
		URL urlUser = classLoader.getResource("user-icon.png");
		URL urlWrong = classLoader.getResource("uncorrect-icon.png");
		
		for (Response response : evalParamEntree.getResponses()) {
			Paragraph paragraph =  new Paragraph(contentProperties.getProperty(ITEXT_DOCUMENT_CHAPTER_QUESTION).concat(" "+i+"/"+nbrQuestion) , FONT_TITLE3 ) ; 
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph) ;
			document.add(new Paragraph("\n")) ; 
			
			addPhraseAsNormal( document , ITEXT_DOCUMENT_CHAPTER_TITRE , response.getQuestion().getIntitule() ) ;
			addPhraseAsNormal( document , ITEXT_DOCUMENT_CHAPTER_NIVEAU , response.getQuestion().getNiveau().getValue() ) ;
			
			addPhraseAsCode  ( document ,  formatQuestionOrProposition(response.getQuestion().getContenu()) ) ; 
			document.add(new Paragraph("\n")) ; 
			
//			addListLikeParagraphs (document, ITEXT_DOCUMENT_CHAPTER_STATEMENT , response.getQuestion().getPropositionsReponses()) ;
//			addListLikeParagraphs (document, ITEXT_DOCUMENT_CHAPTER_REPONSE , evaluationService.determinerPropositionsCorrectesByReponse(response) ) ;
//			addListLikeParagraphs (document, ITEXT_DOCUMENT_CHAPTER_CANDIDAT , response.getReponsesChoisies()) ;
			
			float[] colsWidth = {2f, 2f , 2f , 20f};
			PdfPTable table = new PdfPTable(colsWidth) ; 
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.setLockedWidth(false);
			int j = 1; 
			for (PropositionReponse proposition : response.getQuestion().getPropositionsReponses()) {
				PdfPCell cell = new PdfPCell(new Paragraph(""+j)) ; 
				table.addCell(cell);
				
				if (response.getReponsesChoisies().contains(proposition)) {
					cell = new PdfPCell(Image.getInstance(urlUser.toURI().toString())) ;
				} else {
					cell = new PdfPCell(new Paragraph("")) ;
				}
				cell.setPaddingLeft(0.3f) ; 
				table.addCell(cell);
				
				if (proposition.isPropositionCorrecte()) {
					cell = new PdfPCell(Image.getInstance(urlCorrect.toURI().toString()));
				} else {
					cell = new PdfPCell(Image.getInstance(urlWrong.toURI().toString()));
				}
				cell.setPaddingLeft(0.3f) ; 
				table.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(formatQuestionOrProposition(proposition.getValeur()))) ; 
				table.addCell(cell);
				
				j++ ; 
			}
			
			document.add(table) ; 
			
			if (response.getQuestion().getCorrectionHints() != null) {
				addListLikeParagraphs (document, ITEXT_DOCUMENT_CHAPTER_CORRECTION , response.getQuestion().getCorrectionHints()) ;
			}
			
			i++ ; 
		}
		document.add(new Paragraph("\n")) ;
//		document.add(chapter) ; 
		return document ; 
	}
	
	public void addListLikeParagraphs (Document document, String param , Collection<PropositionReponse> collection) throws DocumentException {
		document.add( new Paragraph(contentProperties.getProperty(param) , FONT_TITLE4 )) ;
		document.add(new Paragraph("\n")) ; 
		
		List liste = new List(true, collection.size());
		liste.setNumbered(true) ; 
		liste.setAlignindent(true) ; 
		liste.setAutoindent(true) ; 
		for (PropositionReponse propositionReponse : collection) {
			liste.add(new ListItem (new Phrase (formatQuestionOrProposition(propositionReponse.getValeur())))) ;
		}
		document.add(liste) ; 
		document.add(new Paragraph("\n")) ;
	}
	
	public void addListLikeParagraphs (Document document, String param , String correction) throws DocumentException {
		document.add( new Paragraph(contentProperties.getProperty(param) , FONT_TITLE4 )) ;
		document.add(new Paragraph("\n")) ; 
		
		document.add(new Chunk(correction)) ; 
		document.add(new Paragraph("\n")) ;
	}

	private void closeDocument(Document document) {
		document.close();
	}
	
	private String formatQuestionOrProposition (String input) {
		String output = input.replace("[DB]",  "\"");
		output = output.replace("[code style=java]", "");
		output = output.replace("[code style=csharp]", "");
		output = output.replace("[code style=xml]", "");
		output = output.replace("[code style=php]", "");
		output = output.replace("[code style=cpp]", "");
		output = output.replace("[code style=sql]", "");
		output = output.replace("[/code]", "");
		output = output.replace("[NL][NL]", "\r\n");
		output = output.replace("[NL]", "\r\n");
		output = output.replace("[TAB]", "\t\t\t");
		
		return output;  	
	}
}