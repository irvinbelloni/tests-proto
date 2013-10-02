package com.ossia.test.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.EvaluationStatus;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.ProfilService;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.form.AssignTestForm;
import com.ossia.test.web.form.FilterResultsForm;
import com.ossia.test.web.sort.SortingInfo;

@Controller
@RequestMapping("/admin")
public class EvaluationController extends AbstractAdminController {
	
	private final static String SESSION_RESULT_LIST_SORT = "result.list.sort";
	private final static String SESSION_RESULT_LIST_FILTER = "result.list.filter";
	
	@Autowired 
	private ProfilService profilService ; 
	
	@Autowired
	private EvaluationService evaluationService ; 

	@Autowired
	private TestSheetService testSheetService ; 
		
	@RequestMapping(value = "/resultats", method = RequestMethod.GET)
	public String displayResultats (@RequestParam(value = "sort", required = false) String sortingField, @RequestParam(value = "direction", required = false) String sortingDirection, HttpServletRequest request, ModelMap model ) {
		
		// Sorting information
		SortingInfo sortingInfo = completeProfilSortingInfo((SortingInfo)request.getSession().getAttribute(SESSION_RESULT_LIST_SORT), sortingField, sortingDirection);	
		request.getSession().setAttribute(SESSION_RESULT_LIST_SORT, sortingInfo);
		
		// Filtering information
		FilterResultsForm filterForm = (FilterResultsForm)request.getSession().getAttribute(SESSION_RESULT_LIST_FILTER);
		if (filterForm == null) {
			filterForm = new FilterResultsForm();
		}
				
		model.put("assignForm", new AssignTestForm());
		model.put("sortingInfo", sortingInfo);
		model.put("resultFilteringForm", filterForm);
		model.put("selectedTab", TAB_RESULTAT);
		model.put("resultats", evaluationService.getSortedAndFilteredResults(sortingInfo, filterForm));
		return "resultat-home";
	}
	
	@RequestMapping(value = "/resultats/filter", method = RequestMethod.POST)
	public String filterResults (@Valid @ModelAttribute("resultFilteringForm") FilterResultsForm filterForm, BindingResult results, HttpServletRequest request, ModelMap model ) {
		request.getSession().setAttribute(SESSION_RESULT_LIST_FILTER, filterForm);		
		return "redirect:/admin/resultats";
	}
	
	@RequestMapping(value = "/resultat/detail", method = RequestMethod.GET)
	public String displayResultatDetail(@RequestParam("evaluation") Integer evaluationId, ModelMap model) {
		
		Evaluation evaluation = evaluationService.getEvaluationById(evaluationId);
		if (evaluation == null || evaluation.getStatus() != EvaluationStatus.DONE.getCode()) {
			return "redirect:/admin/resultats";
		}
		
		model.put("result",  evaluation);
		model.put("selectedTab", TAB_RESULTAT);		
		return "resultat-detail";
	}
	
	@ModelAttribute("testTypes")
    public Collection<String> getAllTestTypes() {
		return testSheetService.getAllTestTypes();
	}
	
	@ModelAttribute("tests")
    public Collection<TestSheet> getAllTests() {
		SortingInfo sortingInfo = new SortingInfo();
		sortingInfo.setSortingDirection(SortingInfo.ASC);
		sortingInfo.setSortingField(SortingInfo.SORT_INTITULE);
		return testSheetService.getSortedTestSheets(sortingInfo);
	}
	
	@ModelAttribute("candidates")
    public Collection<Profil> getAllCandidates() {
		SortingInfo sortingInfo = new SortingInfo();
		sortingInfo.setSortingField(SortingInfo.SORT_NAME);
		sortingInfo.setSortingDirection(SortingInfo.DESC);
		return profilService.getSortedProfilByRole(false, sortingInfo);
	}
		
	
	
	private SortingInfo completeProfilSortingInfo (SortingInfo sortingInfo, String sortingField, String sortingDirection) {
		if (sortingInfo == null) {
			sortingInfo = new SortingInfo();
			sortingInfo.setSortingDirection(SortingInfo.DESC);
			sortingInfo.setSortingField(SortingInfo.SORT_PASSAGE);
		}
		if (sortingField != null) {
			if (SortingInfo.SORT_TEST.equals(sortingField) || SortingInfo.SORT_CANDIDATE.equals(sortingField) || SortingInfo.SORT_TYPE.equals(sortingField) || SortingInfo.SORT_PASSAGE.equals(sortingField)) {
				sortingInfo.setSortingField(sortingField);
			}
		}
		if (sortingDirection != null) {
			if (SortingInfo.ASC.equals(sortingDirection) || SortingInfo.DESC.equals(sortingDirection)) {
				sortingInfo.setSortingDirection(sortingDirection);
			}
		}		
		return sortingInfo;
	}

}
