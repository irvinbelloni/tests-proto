package com.ossia.test.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.ProfilService;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.sort.SortingInfo;

@Controller @RequestMapping("/admin/home")
public class HomeAdminController extends AbstractAdminController {
	
	@Autowired
	ProfilService profilService;
	
	@Autowired
	TestSheetService testSheetService;

	@RequestMapping(method = RequestMethod.GET)
	public String displayAdminHome(ModelMap model) {
		model.put("selectedTab", TAB_HOME);
		return "admin-home";
	}
	
	@ModelAttribute("candidates")
    public Collection<Profil> getAllUsers() {
		SortingInfo sortingInfo = new SortingInfo();
		sortingInfo.setSortingField(SortingInfo.SORT_CREATION_DATE);
		sortingInfo.setSortingDirection(SortingInfo.DESC);
		return profilService.getSortedProfilByRole(false, sortingInfo);
	}
	
	@ModelAttribute("administrators")
    public Collection<Profil> getAllAdministrators(HttpServletRequest request) {
		return profilService.getProfilByRole(true);
	}
	
	@ModelAttribute("tests")
    public Collection<TestSheet> getAllTests() {
		SortingInfo sortingInfo = new SortingInfo();
		sortingInfo.setSortingField(SortingInfo.SORT_CREATION_DATE);
		sortingInfo.setSortingDirection(SortingInfo.DESC);
		return testSheetService.getSortedTestSheets(sortingInfo);
	}	
}
