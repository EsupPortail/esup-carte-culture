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

import org.esupportail.esupnfccarteculture.entity.Gestionnaire;
import org.esupportail.esupnfccarteculture.entity.Salle;
import org.esupportail.esupnfccarteculture.repository.GestionnaireRepository;
import org.esupportail.esupnfccarteculture.repository.SalleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/manager/gestionnaires")
@Controller
public class GestionnaireController {

    @Resource
    private SalleRepository salleRepository;

    @Resource
    private GestionnaireRepository gestionnaireRepository;

	@ModelAttribute("active")
	public String getActiveMenu() {
		return "gestionnaires";
	}

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@RequestParam String eppn, @RequestParam String nom, @RequestParam String prenom, @RequestParam List<Long> salles) {
        Gestionnaire gestionnaire = new Gestionnaire();
        createGest(eppn, nom, prenom, salles, gestionnaire);
        gestionnaireRepository.persist(gestionnaire);
        return "redirect:/manager/gestionnaires/" + gestionnaire.getId() + "?form";
    }

    private void createGest(@RequestParam String eppn, @RequestParam String nom, @RequestParam String prenom, @RequestParam List<Long> salles, Gestionnaire gestionnaire) {
        gestionnaire.setNom(nom);
        gestionnaire.setPrenom(prenom);
        gestionnaire.setEppn(eppn);
        gestionnaire.getSalles().clear();
        for(Long salleId : salles) {
            Salle salle = salleRepository.findSalle(salleId);
            gestionnaire.getSalles().add(salle);
        }
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Gestionnaire());
        return "jsp/manager/gestionnaires/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("gestionnaire", gestionnaireRepository.findGestionnaire(id));
        uiModel.addAttribute("itemId", id);
        return "jsp/manager/gestionnaires/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("gestionnaires", gestionnaireRepository.findGestionnaireEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) gestionnaireRepository.countGestionnaires() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("gestionnaires", gestionnaireRepository.findAllGestionnaires(sortFieldName, sortOrder));
        }
        return "jsp/manager/gestionnaires/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@RequestParam Long id, @RequestParam String eppn, @RequestParam String nom, @RequestParam String prenom, @RequestParam List<Long> salles) {
        Gestionnaire gestionnaire = gestionnaireRepository.findGestionnaire(id);
        createGest(eppn, nom, prenom, salles, gestionnaire);
        gestionnaireRepository.merge(gestionnaire);
        return "redirect:/manager/gestionnaires/" + gestionnaire.getId() + "?form";
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, gestionnaireRepository.findGestionnaire(id));
        return "jsp/manager/gestionnaires/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        gestionnaireRepository.remove(id);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/manager/gestionnaires";
    }

    void populateEditForm(Model uiModel, Gestionnaire gestionnaire) {
        uiModel.addAttribute("gestionnaire", gestionnaire);
        uiModel.addAttribute("salles", salleRepository.findAllSalles());
    }
}
