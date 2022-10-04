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

import org.esupportail.esupnfccarteculture.entity.Salle;
import org.esupportail.esupnfccarteculture.repository.SalleRepository;
import org.esupportail.esupnfccarteculture.service.TagService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequestMapping("/manager/salles")
@Controller
public class SalleController {
	
	@ModelAttribute("active")
	public String getActiveMenu() {
		return "salles";
	}
	
	@Resource
	TagService tagService;

    @Resource
    private SalleRepository salleRepository;

    void populateEditForm(Model uiModel, Salle salle) {
    	uiModel.addAttribute("typeSalle", tagService.getTypeSallesBadgeables());
        uiModel.addAttribute("salle", salle);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
    	Salle salle = salleRepository.findSalle(id);
    	salle.setTarifString(salle.getTarifString());
        populateEditForm(uiModel, salle);
        return "jsp/manager/salles/update";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("salle", salleRepository.findSalle(id));
        uiModel.addAttribute("itemId", id);
        return "jsp/manager/salles/show";
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Salle salle, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, salle);
            return "jsp/manager/salles/create";
        }
        uiModel.asMap().clear();
        salleRepository.persist(salle);
        return "redirect:/manager/salles/" + salle.getId();
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Salle());
        return "jsp/manager/salles/create";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("salles", salleRepository.findSalleEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) salleRepository.countSalles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("salles", salleRepository.findAllSalles(sortFieldName, sortOrder));
        }
        return "jsp/manager/salles/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Salle salle, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, salle);
            return "jsp/manager/salles/update";
        }
        uiModel.asMap().clear();
        salleRepository.merge(salle);
        return "redirect:/manager/salles/" + salle.getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        salleRepository.remove(id);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/manager/salles";
    }

}
