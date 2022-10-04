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
package org.esupportail.esupnfccarteculture.web.partenaire;

import org.esupportail.esupnfccarteculture.entity.*;
import org.esupportail.esupnfccarteculture.repository.EtudiantRepository;
import org.esupportail.esupnfccarteculture.repository.GestionnaireRepository;
import org.esupportail.esupnfccarteculture.repository.SalleRepository;
import org.esupportail.esupnfccarteculture.repository.TagLogRepository;
import org.esupportail.esupnfccarteculture.service.FacturePDFBuilder;
import org.esupportail.esupnfccarteculture.service.TagService;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RequestMapping("/partenaire")
@Controller
public class IndexPartenaireController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	private FacturePDFBuilder facturePDFBuilder;

	@Resource
	private SalleRepository salleRepository;

	@Resource
	private TagLogRepository tagLogRepository;

	@Resource
	private EtudiantRepository etudiantRepository;

	@Resource
	private GestionnaireRepository gestionnaireRepository;

	@Resource
	TagService tagService;

	@ModelAttribute("active")
	public String getActiveMenu() {
		return "partenaire";
	}

	@RequestMapping
	public String index(HttpServletRequest request, Model uiModel) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String eppn = auth.getName();
		List<Salle> salles = null;
		if (request.isUserInRole("ROLE_ADMIN")) {
			salles = salleRepository.findAllSalles();
		} else {
			if (gestionnaireRepository.countFindGestionnairesByEppnEquals(eppn) > 0) {
				Gestionnaire gestionnaire = gestionnaireRepository.findGestionnairesByEppnEquals(eppn).getSingleResult();
				salles = gestionnaire.getSalles();
				if (salles.size() == 1) {
					return "redirect:/partenaire/salle/" + salles.get(0).getId() + "/";
				}
			} else {
				log.warn(eppn + " n'est pas un partenaire");
			}
		}
		uiModel.addAttribute("salles", salles);
		return "jsp/partenaire/index";
	}

	@RequestMapping(value = "/salle/{id}")
	public String salle(@PathVariable("id") long id,
			@RequestParam(value = "dateDebut", required = false) String dateDebut,
			@RequestParam(value = "dateFin", required = false) String dateFin, HttpServletRequest request,
			Model uiModel) throws ParseException {
		addDateTimeFormatPatterns(uiModel);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String eppn = auth.getName();
		Salle salleOK = null;
		if (request.isUserInRole("ROLE_ADMIN")) {
			salleOK = salleRepository.findSalle(id);
		} else {
			Gestionnaire gestionnaire = gestionnaireRepository.findGestionnairesByEppnEquals(eppn).getSingleResult();
			List<Salle> salles = gestionnaire.getSalles();
			for (Salle salle : salles) {
				if (salle.getId() == id) {
					salleOK = salle;
					break;
				}
			}
		}
		if(salleOK == null) {
			throw new AccessDeniedException("Ne gère pas cette salle");				
		}
		boolean pdf = false;
		Date dateDebutOK = null;
		Date dateFinOK = null;
		List<TagLog> tagLogs = null;
		if (dateDebut == null && dateFin == null) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			dateDebutOK = calendar.getTime();
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			dateFinOK = calendar.getTime();
		} else {
			pdf = true;
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
			Calendar calendarDebut = Calendar.getInstance();
			calendarDebut.setTime(format.parse(dateDebut));
			calendarDebut.set(Calendar.MINUTE, 0);
			calendarDebut.set(Calendar.SECOND, 0);
			calendarDebut.set(Calendar.HOUR_OF_DAY, 0);
			dateDebutOK = calendarDebut.getTime();
			Calendar calendarFin = Calendar.getInstance();
			calendarFin.setTime(format.parse(dateFin));
			calendarFin.set(Calendar.MINUTE, 59);
			calendarFin.set(Calendar.SECOND, 59);
			calendarFin.set(Calendar.HOUR_OF_DAY, 23);
			dateFinOK = calendarFin.getTime();
		}

		tagLogs = tagLogRepository.findTagLogsBySalleAndDateBetween(salleOK, dateDebutOK, dateFinOK).getResultList();

		uiModel.addAttribute("dateDebut", dateDebut);
		uiModel.addAttribute("dateFin", dateFin);
		uiModel.addAttribute("taglogs", tagLogs);
		uiModel.addAttribute("salle", salleOK);
		uiModel.addAttribute("pdf", pdf);
		return "jsp/partenaire/salle";
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
	public String update(@Valid Salle salle, BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		Salle salleOK = salleRepository.findSalle(salle.getId());
		salleOK.setTarifString(salle.getTarifString());
		salleRepository.merge(salleOK);
		return "redirect:/partenaire/salle/" + salle.getId() + "/";
	}

	@RequestMapping(value = "/salle/{id}/debit", method = RequestMethod.POST)
	public String debit(@PathVariable("id") long id,
			@RequestParam(value = "nom", required = true) String nom,
			@RequestParam(value = "prenom", required = true) String prenom, 
			@RequestParam(value = "dateNaissance", required = true) String dateNaissance, HttpServletRequest request,
			Model uiModel, RedirectAttributes redirectAttrs) throws ParseException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String eppn = auth.getName();
		Salle salleOK = null;
		Gestionnaire gestionnaire = gestionnaireRepository.findGestionnairesByEppnEquals(eppn).getSingleResult();
		List<Salle> salles = gestionnaire.getSalles();
		for (Salle salle : salles) {
			if (salle.getId() == id) {
				salleOK = salle;
				break;
			}
		}
		if (request.isUserInRole("ROLE_ADMIN")) {
			salleOK = salleRepository.findSalle(id);
		}
		if(salleOK != null) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
			if(etudiantRepository.countFindEtudiantsByNomEqualsAndPrenomEqualsAndDateNaissanceEquals(nom, prenom, format.parse(dateNaissance)) > 0) {
				Etudiant etudiant = etudiantRepository.findEtudiantsByNomEqualsAndPrenomEqualsAndDateNaissanceEquals(nom, prenom, format.parse(dateNaissance)).getSingleResult();
				if (tagService.checkEtudiant(etudiant, salleOK.getNom()) == null) {
					tagService.debitCoupon(etudiant, salleOK.getNom(), eppn);
					etudiantRepository.merge(etudiant);
					redirectAttrs.addFlashAttribute("messageInfo", "message_info_debit_coupon");
				} else {
					redirectAttrs.addFlashAttribute("messageError", "message_error_coupon");
				}
				
			} else {
				redirectAttrs.addFlashAttribute("messageError", "message_error_find_etudiant");
			}
		} else {
			throw new AccessDeniedException("Ne gère pas cette salle");	
		}
				
		return "redirect:/partenaire/salle/" + id + "/";
	}
	
	@RequestMapping(value = "/salle/{id}/pdf")
	@ResponseBody
	public void pdf(@PathVariable("id") long id,
			@RequestParam(value = "dateDebut", required = false) String dateDebut,
			@RequestParam(value = "dateFin", required = false) String dateFin, HttpServletRequest request, HttpServletResponse response,
			Model uiModel) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String eppn = auth.getName();
		Salle salleOK = null;
		if (!request.isUserInRole("ROLE_ADMIN")) {
			Gestionnaire gestionnaire = gestionnaireRepository.findGestionnairesByEppnEquals(eppn).getSingleResult();
			List<Salle> salles = gestionnaire.getSalles();
			for (Salle salle : salles) {
				if (salle.getId() == id) {
					salleOK = salle;
				}
			}
		} else {
			salleOK = salleRepository.findSalle(id);
		}
		if (salleOK == null && !request.isUserInRole("ROLE_ADMIN")) {
			throw new AccessDeniedException("Ne gère pas cette salle");
		}
		Date dateDebutOK = null;
		Date dateFinOK = null;
		List<TagLog> tagLogs = null;
		if (dateDebut == null && dateFin == null) {

			tagLogs = tagLogRepository.findTagLogsBySalle(salleOK).getResultList();
		} else {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
			Calendar calendarDebut = Calendar.getInstance();
			calendarDebut.setTime(format.parse(dateDebut));
			calendarDebut.set(Calendar.MINUTE, 0);
			calendarDebut.set(Calendar.SECOND, 0);
			calendarDebut.set(Calendar.HOUR_OF_DAY, 0);
			dateDebutOK = calendarDebut.getTime();
			Calendar calendarFin = Calendar.getInstance();
			calendarFin.setTime(format.parse(dateFin));
			calendarFin.set(Calendar.MINUTE, 59);
			calendarFin.set(Calendar.SECOND, 59);
			calendarFin.set(Calendar.HOUR_OF_DAY, 23);
			dateFinOK = calendarFin.getTime();
			tagLogs = tagLogRepository.findTagLogsBySalleAndDateBetween(salleOK, dateDebutOK, dateFinOK).getResultList();
		}

		FactureModel factureModel = new FactureModel();
		factureModel.setTagLogs(tagLogs);
		factureModel.setDateDebut(dateDebutOK);
		factureModel.setDateFin(dateFinOK);
		factureModel.setSalle(salleOK.getNom() + " " + salleOK.getLieu());

		uiModel.addAttribute("factureModel", factureModel);
		facturePDFBuilder.render(uiModel.asMap(), request, response);
	}

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("tagLog_date_date_format",
				DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
	}

}
