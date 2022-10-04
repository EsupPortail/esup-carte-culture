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
package org.esupportail.esupnfccarteculture.web.ws;

import org.esupportail.esupnfccarteculture.entity.*;
import org.esupportail.esupnfccarteculture.ldap.PersonLdap;
import org.esupportail.esupnfccarteculture.repository.EtudiantRepository;
import org.esupportail.esupnfccarteculture.repository.GestionnaireRepository;
import org.esupportail.esupnfccarteculture.repository.SalleRepository;
import org.esupportail.esupnfccarteculture.repository.TagLogGestRepository;
import org.esupportail.esupnfccarteculture.service.EtudiantService;
import org.esupportail.esupnfccarteculture.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/nfc-ws")
@Controller
public class NfcWsController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	EtudiantService etudiantService;

	@Resource
	TagService tagService;

	@Resource
	private SalleRepository salleRepository;

	@Resource
	private EtudiantRepository etudiantRepository;

	@Resource
	private GestionnaireRepository gestionnaireRepository;

	@Resource
	private TagLogGestRepository tagLogGestRepository;

	@RequestMapping(value = "/tagIdCheck", method = RequestMethod.GET)
	@ResponseBody
	public EsupNfcTagLog tagIdCheck(@RequestParam(required = false) String desfireId,
			@RequestParam(required = false) String csn) {
		EsupNfcTagLog esupNfcTagLog = null;
		if (etudiantRepository.countFindEtudiantsByCsnEquals(csn.toUpperCase()) > 0) {
			Etudiant etudiant = etudiantRepository.findEtudiantsByCsnEquals(csn.toUpperCase()).getSingleResult();
			esupNfcTagLog = new EsupNfcTagLog();
			esupNfcTagLog.setCsn(etudiant.getCsn());
			esupNfcTagLog.setEppn(etudiant.getEppn());
			esupNfcTagLog.setFirstname(etudiant.getPrenom());
			esupNfcTagLog.setLastname(etudiant.getNom());
		} else {
			log.warn("Etudiant non trouvé dans la base Carte Culture. CSN : "+ csn);
			PersonLdap person = etudiantService.getPersonFromCsn(csn);
			if(person != null) {
				esupNfcTagLog = new EsupNfcTagLog();
				esupNfcTagLog.setCsn(csn.toUpperCase());
				esupNfcTagLog.setEppn(person.getEduPersonPrincipalName());
				esupNfcTagLog.setFirstname(person.getGivenName());
				esupNfcTagLog.setLastname(person.getSn());
			}
		}
		return esupNfcTagLog;
	}

	@RequestMapping(value = "/getLocations", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<String> getLocations(@RequestParam String eppn) {
		List<String> listSalles = new ArrayList<>();
		try {
			List<Gestionnaire> gestionnaires = gestionnaireRepository.findGestionnairesByEppnEquals(eppn).getResultList();
			if (gestionnaires.size() > 0) {
				listSalles = new ArrayList<>();
				List<Salle> salles = gestionnaires.get(0).getSalles();
				for (Salle salle : salles) {
					listSalles.add(salle.getNom());
				}
			} else {
				log.warn("L'utilisateur " + eppn + " n'est gestionnaire d'aucune salle");
			}
		} catch (NoResultException e) {
			log.error("L'utilisateur " + eppn + " n'est gestionnaire d'aucune salle");
		}
		return listSalles;
	}

	/* curl -H "Content-Type: application/json" -X POST -d '{"eppn": "test@univ-ville.fr", "location": "Olympia"}' https://carte-culture.univ-ville.fr/nfc-ws/isTagable */
	@RequestMapping(value = "/isTagable", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> isTagable(@RequestBody EsupNfcTagLog esupNfcTagLog) {
		HttpHeaders responseHeaders = new HttpHeaders();
		Salle salle = salleRepository.findSallesByNomEquals(esupNfcTagLog.getLocation()).getSingleResult();
		if(gestionnaireRepository.countFindGestionnairesByEppnEquals(esupNfcTagLog.getEppn()) == 0) {
			if (salle.getTypeSalle().equals(TypeSalleInscription.getTypeSalleInscriptionSingleton().getNom())) {
				if(etudiantRepository.countFindEtudiantsByEppnEquals(esupNfcTagLog.getEppn()) > 0) {
					Etudiant etudiant = etudiantRepository.findEtudiantsByEppnEquals(esupNfcTagLog.getEppn()).getSingleResult();
					if (tagService.checkRecharge(etudiant)) {
						return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.OK);
					} else {
						return new ResponseEntity<String>("\nRecharge impossible", responseHeaders, HttpStatus.EXPECTATION_FAILED);
					}
				} else {
					PersonLdap person = etudiantService.getPersonFromEppn(esupNfcTagLog.getEppn());
					if(person != null) {
						return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.OK);
					} else {
						return new ResponseEntity<String>("Non autorisé", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
			} else {
				String result = tagService.checkEtudiant(esupNfcTagLog.getEppn(), esupNfcTagLog.getLocation());
				if (result != null) {
					return new ResponseEntity<String>(result, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				} else {
					return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.OK);
				}
			}
		}else{
			return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.OK);
		}
	}
	
	/* curl -H "Content-Type: application/json" -X POST -d '{"eppn": "test@univ-ville.fr", "location": "Olympia", "eppnInit": "gest@univ-ville.fr"}' http://carte-culture.univ-ville.fr/nfc-ws/validateTag */
	@Transactional
	@RequestMapping(value = "/validateTag", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> validateTag(@RequestBody EsupNfcTagLog esupNfcTagLog, HttpServletRequest httpServletRequest) {
		HttpHeaders responseHeaders = new HttpHeaders();
		Salle salle = salleRepository.findSallesByNomEquals(esupNfcTagLog.getLocation()).getSingleResult();
		if(gestionnaireRepository.countFindGestionnairesByEppnEquals(esupNfcTagLog.getEppn()) == 0) {
			if (salle.getTypeSalle().equals(TypeSalleInscription.getTypeSalleInscriptionSingleton().getNom())) {
				if(etudiantRepository.countFindEtudiantsByEppnEquals(esupNfcTagLog.getEppn()) > 0) {
					Etudiant etudiant = etudiantRepository.findEtudiantsByEppnEquals(esupNfcTagLog.getEppn()).getSingleResult();
					if(tagService.recharge(etudiant)) {
						tagService.createNewTagLog(etudiant, salle, esupNfcTagLog.getEppnInit());
					}
					try {
						etudiantService.updateEtudiant(etudiant);
					} catch (Exception e) {
						log.error("erreur lors de la mise à jour de " + esupNfcTagLog.getEppn(), e);
					}
					return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.OK);
				}else{
					PersonLdap person = etudiantService.getPersonFromEppn(esupNfcTagLog.getEppn());
					if(person != null) {
						try {
							Etudiant etudiant = new Etudiant(esupNfcTagLog.getEppn(), new Date(), person.getEduPersonAffiliation());
							etudiantRepository.persist(etudiant);
							tagService.recharge(etudiant);
							etudiantService.updateEtudiant(etudiant);
							tagService.createNewTagLog(etudiant, salle, esupNfcTagLog.getEppnInit());
						} catch (Exception e) {
							log.error("erreur lors de l'inscription de l'etudiant", e);
							return new ResponseEntity<String>("KO", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
						}
						return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.OK);
					} else {
						log.warn("étudiant non autorisé à s'inscrire");
						return new ResponseEntity<String>("KO", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
			} else {
				if(etudiantRepository.countFindEtudiantsByEppnEquals(esupNfcTagLog.getEppn()) > 0) {
					Etudiant etudiant = etudiantRepository.findEtudiantsByEppnEquals(esupNfcTagLog.getEppn()).getSingleResult();
					if (tagService.debitCoupon(etudiant, esupNfcTagLog.getLocation(), esupNfcTagLog.getEppnInit())) {
						try {
							etudiantService.updateEtudiant(etudiant);
						} catch (Exception e) {
							log.error("erreur lors de la mise à jour de " + esupNfcTagLog.getEppn());
						}
						return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.OK);
					} else {
						return new ResponseEntity<String>("KO", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					log.warn("Etudiant " + esupNfcTagLog.getEppn() + " non trouvé lors du debit");
					return new ResponseEntity<String>("KO", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
		} else {
			TagLogGest tagLogGest = new TagLogGest();
			tagLogGest.setDate(new Date());
			tagLogGest.setEppn(esupNfcTagLog.getEppn());
			tagLogGest.setEppnInit(esupNfcTagLog.getEppnInit());
			tagLogGest.setSalle(esupNfcTagLog.getLocation());
			tagLogGestRepository.persist(tagLogGest);
			return new ResponseEntity<String>("OK", responseHeaders, HttpStatus.OK);
		}
	}

	@RequestMapping(value="/display",  method=RequestMethod.POST)
	@ResponseBody
	public String verso(@RequestBody EsupNfcTagLog taglog) {
		log.info("get verso from : " + taglog);
		if(gestionnaireRepository.countFindGestionnairesByEppnEquals(taglog.getEppn())==0){
			Etudiant etudiant = etudiantRepository.findEtudiantsByEppnEquals(taglog.getEppn()).getSingleResult();
			return "Coupons restants : " + etudiantService.affichageCoupons(etudiant);
		} else {
			return "Le badgeage fonctionne !";
		}
	}
	
}