/**
 * Licensed to ESUP-Portail under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 *
 * ESUP-Portail licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.esupportail.esupnfccarteculture.web.manager;

import org.esupportail.esupnfccarteculture.entity.*;
import org.esupportail.esupnfccarteculture.repository.EtudiantRepository;
import org.esupportail.esupnfccarteculture.repository.GestionnaireRepository;
import org.esupportail.esupnfccarteculture.repository.SalleRepository;
import org.esupportail.esupnfccarteculture.repository.TagLogRepository;
import org.esupportail.esupnfccarteculture.service.EtudiantService;
import org.esupportail.esupnfccarteculture.service.ExportService;
import org.esupportail.esupnfccarteculture.service.TagService;
import org.esupportail.esupnfccarteculture.service.UtilsService;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/manager/etudiants")
@Controller
public class EtudiantController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@ModelAttribute("active")
	public String getActiveMenu() {
		return "etudiants";
	}

	@Resource
	ExportService exportService;

	@Resource
	EtudiantService etudiantService;

	@Resource
	TagService tagService;

	@Resource
	UtilsService utilsService;

	@Resource
	private SalleRepository salleRepository;

	@Resource
	private TagLogRepository tagLogRepository;

	@Resource
	private EtudiantRepository etudiantRepository;

	@Resource
	private GestionnaireRepository gestionnaireRepository;

	List<String> listSearchBy = Arrays.asList("nom", "login");

	void populateEditForm(Model uiModel, Etudiant etudiant) {
		uiModel.addAttribute("etudiant", etudiant);
		addDateTimeFormatPatterns(uiModel);
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("etudiant_datenaissance_date_format",
				DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("etudiant_daterecharge_date_format",
				DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("etudiant_dateinscription_date_format",
				DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
	}

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		Etudiant etudiant = etudiantRepository.findEtudiant(id);
		uiModel.addAttribute("tagLog_date_date_format",
				DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
		uiModel.addAttribute("etudiant", etudiant);
		uiModel.addAttribute("taglogs", tagLogRepository.findTagLogsByEtudiant(etudiant, "date", "desc").getResultList());
		uiModel.addAttribute("itemId", id);
		uiModel.addAttribute("salles", salleRepository.findAllSalles());
		return "jsp/manager/etudiants/show";
	}

	@Transactional
	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		Etudiant etudiant = etudiantRepository.findEtudiant(id);
		populateEditForm(uiModel, etudiant);
		try {
			etudiantService.updateEtudiant(etudiant);
		} catch (Exception e) {
			log.error("erreur lors de la mise à jour de " + etudiant.getEppn(), e);
		}
		List<Salle> salles = new ArrayList<Salle>();
		salles.addAll(salleRepository.findSalles().getResultList());
		uiModel.addAttribute("salles", salles);
		uiModel.addAttribute("recharge", tagService.checkRecharge(etudiant));
		return "jsp/manager/etudiants/update";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Etudiant etudiant, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, etudiant);
			return "jsp/manager/etudiants/update";
		}
		Etudiant etudiantBackup = etudiantRepository.findEtudiant(etudiant.getId());
		etudiant.setCoupons(etudiantBackup.getCoupons());
		uiModel.asMap().clear();
		etudiantRepository.merge(etudiant);
		return "redirect:/manager/etudiants/" + etudiant.getId();
	}

	@RequestMapping(produces = "text/html")
	public String list(@RequestParam(value = "annee", required = false) Integer annee,
			@RequestParam(value = "dateFilter", required = false) String dateFilter,
			@RequestParam(value = "etablissementFilter", required = false) String etablissementFilter,
			@RequestParam(value = "searchString", required = false) String searchString,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel,
			HttpServletRequest httpServletRequest) throws ParseException {

		if (annee == null) {
			annee = utilsService.getAnnee();
		}
		if (etablissementFilter == null) {
			etablissementFilter = "";
		}
		if (dateFilter == null) {
			dateFilter = "";
		}
		if (searchString == null) {
			searchString = "";
		}
		List<Etudiant> etudiants = etudiantRepository.findEtudiants(annee, dateFilter, etablissementFilter, searchString, page,
				size, sortFieldName, sortOrder).getResultList();

		int sizeNo = size == null ? 10 : size.intValue();

		float nrOfPages = (float) etudiantRepository.countFindEtudiants(annee, dateFilter, etablissementFilter, searchString,
				page, size) / sizeNo;

		uiModel.addAttribute("maxPages",
				(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		uiModel.addAttribute("etablissementFilter", etablissementFilter);
		uiModel.addAttribute("etablissements",
				etudiantRepository.findEtablissements(annee, dateFilter, searchString).getResultList());
		uiModel.addAttribute("dateFilter", dateFilter);
		uiModel.addAttribute("searchString", searchString);
		uiModel.addAttribute("etudiants", etudiants);
		uiModel.addAttribute("annee", annee);
		uiModel.addAttribute("annees", etudiantRepository.findAnnees());
		uiModel.addAttribute("page", page);
		uiModel.addAttribute("size", size);
		uiModel.addAttribute("size", size);
		uiModel.addAttribute("queryUrl", "?annee=" + annee + "&etablissementFilter=" + etablissementFilter
				+ "&dateFilter=" + dateFilter + "&searchString=" + searchString);
		return "jsp/manager/etudiants/list";
	}

	@RequestMapping(value = "/{id}/debitCoupon")
	public String debitCoupon(RedirectAttributes redirectAttrs, @PathVariable("id") Long id,
			@RequestParam(value = "location", required = true) String location, Model uiModel,
			HttpServletRequest httpServletRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String eppnInit = auth.getName();
		Etudiant etudiant = etudiantRepository.findEtudiant(id);
		if (tagService.checkEtudiant(etudiant, location) == null) {
			tagService.debitCoupon(etudiant, location, eppnInit);
			etudiantRepository.merge(etudiant);
			redirectAttrs.addFlashAttribute("messageInfo", "message_info_debit_coupon");
		} else {
			redirectAttrs.addFlashAttribute("messageError", "message_error_coupon");
		}
		return "redirect:/manager/etudiants/" + id + "?form";
	}

	@Transactional
	@RequestMapping(value = "/{id}/recharge", produces = "text/html")
	public String recharge(RedirectAttributes redirectAttrs, @PathVariable("id") Long id, Model uiModel,
			HttpServletRequest httpServletRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String eppnInit = auth.getName();
		Etudiant etudiant = etudiantRepository.findEtudiant(id);
		EsupNfcTagLog esupNfcTagLog = new EsupNfcTagLog();
		esupNfcTagLog.setEppnInit(eppnInit);
		esupNfcTagLog.setEppn(etudiant.getEppn());
		if (gestionnaireRepository.countFindGestionnairesByEppnEquals(eppnInit) > 0) {
			Gestionnaire gest = gestionnaireRepository.findGestionnairesByEppnEquals(eppnInit).getSingleResult();
			Salle salleInscription = null;
			for (Salle salle : gest.getSalles()) {
				if (salle.getTypeSalle()
						.equals(TypeSalleInscription.getTypeSalleInscriptionSingleton().toString())) {
					salleInscription = salle;
					break;
				}
			}
			if (salleInscription != null) {
				esupNfcTagLog.setLocation(salleInscription.getNom());
				if (tagService.recharge(etudiant)) {
					tagService.createNewTagLog(etudiant, salleInscription, esupNfcTagLog.getEppnInit());
					redirectAttrs.addFlashAttribute("messageInfo", "message_info_coupon");
				}
			} else {
				log.warn("Le gestionnaire " + eppnInit + " n'est associé à aucune salle");
				redirectAttrs.addFlashAttribute("messageError", "message_error_gest_salle");
			}
		} else {
			log.warn("Le gestionnaire " + eppnInit + " n'existe pas");
			redirectAttrs.addFlashAttribute("messageError", "message_error_gest_salle");
		}
		return "redirect:/manager/etudiants/" + id + "?form";
	}

	@RequestMapping(value = "/emails", method = RequestMethod.GET)
	public void exportEmails(@RequestParam(value = "annee", required = false) Integer annee, HttpServletResponse response, HttpServletRequest httpServletRequest, Model uiModel) throws IOException {
		if(annee==null) {
			annee = utilsService.getAnnee();
		}
		String reportName = "CSV_all_export.csv";
		response.setHeader("Content-disposition", "attachment;filename=" + reportName);
		response.setHeader("Set-Cookie", "fileDownload=true; path=/");
		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8");
		byte[] EXCEL_UTF8_HACK = new byte[] { (byte)0xEF, (byte)0xBB, (byte)0xBF};
		response.getOutputStream().write(EXCEL_UTF8_HACK);
		Writer writer = new OutputStreamWriter(response.getOutputStream(), "UTF8");
		Field[] attributes = ExportEmails.class.getDeclaredFields();
		final String[] header = new String[attributes.length];
		int i = 0;
		for (Field field : attributes) {
			header[i]=field.getName();
			i++;
		}
		ICsvBeanWriter beanWriter =  new CsvBeanWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);
		beanWriter.writeHeader(header);
		List<ExportEmails> exports=null;
		try{
			exports = exportService.getEmailsExport(annee);
			for (ExportEmails export : exports) {
				beanWriter.write(export, header);
			}
			beanWriter.flush();
			writer.close();
			beanWriter.close();
		}catch(Exception e){
			log.error("interuption de l'export email !", e);
		}
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        etudiantRepository.remove(id);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/manager/etudiants";
    }
}
