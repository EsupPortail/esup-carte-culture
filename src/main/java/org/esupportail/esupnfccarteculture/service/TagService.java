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
package org.esupportail.esupnfccarteculture.service;

import org.esupportail.esupnfccarteculture.entity.*;
import org.esupportail.esupnfccarteculture.repository.EtudiantRepository;
import org.esupportail.esupnfccarteculture.repository.SalleRepository;
import org.esupportail.esupnfccarteculture.repository.TagLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import java.util.*;
import java.util.stream.Collectors;

public class TagService {

	@Resource
	private SalleRepository salleRepository;

	@Resource
	private TagLogRepository tagLogRepository;

	@Resource
	private EtudiantRepository etudiantRepository;

	private final Logger log = LoggerFactory.getLogger(TagService.class);

	private int nbRechargeMax = 3;

	private List<TypeSalle> typeSalles;
	
	private List<TypeSalle> typeSallesBases = Arrays.asList(new TypeSalle[]{TypeSalleInscription.getTypeSalleInscriptionSingleton()});

	public List<TypeSalle> getTypeSallesBadgeables() {
		return typeSalles.stream().filter(typeSalle -> typeSalle.isTypeSalleBadgeable()).collect(Collectors.toList());
	}
		
	public List<TypeSalle> getTypeSallesDebitables() {
		return typeSalles.stream().filter(typeSalle -> typeSalle.isTypeSalleDebitable()).collect(Collectors.toList());
	}
	
	public TypeSalle getTypeSalle(String nom) {
		return typeSalles.stream().filter(typeSalle -> typeSalle.getNom().equals(nom)).collect(Collectors.toList()).get(0);
	}

	public void setTypeSalles(List<TypeSalle> typeSalles) {		
		this.typeSalles = typeSalles;
		this.typeSalles.addAll(typeSallesBases);
	}

	public int getNbRechargeMax() {
		return nbRechargeMax;
	}

	public void setNbRechargeMax(int nbRechargeMax) {
		this.nbRechargeMax = nbRechargeMax;
	}
	
	public String checkEtudiant(String eppn, String nomSalle) {
		if(etudiantRepository.countFindEtudiantsByEppnEquals(eppn) > 0) {
			return checkEtudiant(etudiantRepository.findEtudiantsByEppnEquals(eppn).getSingleResult(), nomSalle);
		}
		return null;
	}
	
	public String checkEtudiant(Etudiant etudiant, String nomSalle) {
		try {
			Salle salle = salleRepository.findSallesByNomEquals(nomSalle).getSingleResult();
			if (!salle.getTypeSalle().equals(TypeSalleInscription.getTypeSalleInscriptionSingleton().toString())) {
				TypeSalle typeSalle = getTypeSalle(salle.getTypeSalle());
				if(etudiant.getCoupons() != null && (etudiant.getCoupons().get(typeSalle.getNom()) != null && (etudiant.getCoupons().get(typeSalle.getNom()) > 0) || etudiant.getCoupons().get(TypeSalleJoker.JOKER_NAME) > 0)) {
					log.info("Etudiant " + etudiant.getEppn() + " check OK for : " + salle.getNom());
				} else {
					log.info("L'etudiant " + etudiant.getEppn() + " n'a plus de coupon " + typeSalle.getNom() + " lors du check");
					return etudiant.getPrenom() + " " + etudiant.getNom() + ":\nPlus de coupon " + typeSalle.getNom();
				}
			}
		} catch (NoResultException e) {
			log.warn("Etudiant " + etudiant.getEppn() + " non trouvé lors du check", e);
			return "Pas de carte culture :\ncarte inactive";
		}
		return null;
	}

	public String checkEtudiantCoupon(String eppn, String nomSalle) {
		try {
			Etudiant etudiant = etudiantRepository.findEtudiantsByEppnEquals(eppn).getSingleResult();
			Salle salle = salleRepository.findSallesByNomEquals(nomSalle).getSingleResult();
			if (!salle.getTypeSalle().equals(TypeSalleInscription.getTypeSalleInscriptionSingleton().toString())) {
				TypeSalle typeSalle = getTypeSalle(salle.getTypeSalle());
				return etudiant.getCoupons().get(typeSalle.getNom()) + etudiant.getCoupons().get(TypeSalleJoker.JOKER_NAME) + "";
			}
		} catch (NoResultException e) {
			log.warn("Etudiant " + eppn + " non trouvé lors du check", e);
			return "Pas de carte culture :\ncarte inactive";
		}
		return null;
	}

	public boolean debitCoupon(Etudiant etudiant, String nomSalle, String eppnInit) {
		boolean debitOk = false;
		Salle salle = null;
		try {
			salle = salleRepository.findSallesByNomEquals(nomSalle).getSingleResult();
			TypeSalle typeSalle = getTypeSalle(salle.getTypeSalle());
			if(etudiant.getCoupons() != null && etudiant.getCoupons().get(typeSalle.getNom()) != null && etudiant.getCoupons().get(typeSalle.getNom()) > 0) {
				etudiant.getCoupons().replace(typeSalle.getNom(), etudiant.getCoupons().get(typeSalle.getNom()) - 1);
				log.info("Etudiant " + etudiant.getEppn() + " debit OK for : " + salle.getNom() + " du type " + typeSalle.getNom());
				debitOk = true;
			} else if(etudiant.getCoupons() != null && etudiant.getCoupons().get(TypeSalleJoker.JOKER_NAME) > 0) {
				etudiant.getCoupons().put(TypeSalleJoker.JOKER_NAME, etudiant.getCoupons().get(TypeSalleJoker.JOKER_NAME) - 1);
				log.info("Etudiant " + etudiant.getEppn() + " debit OK for : " + salle.getNom() + " with joker");
				debitOk = true;
			} else {
				log.info("L'etudiant " + etudiant.getEppn() + " n'a plus de coupon lors du debit");
			}
			if (debitOk) {
				createNewTagLog(etudiant, salle, eppnInit);
			} 
		} catch (NoResultException e) {
			log.warn("Salle " + salle + " non trouvé lors du debit", e);
		}
		
		return debitOk;
	}

	public boolean checkRecharge(Etudiant etudiant) {
		if(etudiant.getCouponsSum() == 0 && etudiant.getNbRecharge() < nbRechargeMax){
			return true;
		} else {
			log.info("Recharge impossible pour "+etudiant.getEppn());
			return false;
		}
	}

	public boolean recharge(Etudiant etudiant) {
		if(checkRecharge(etudiant)){
			log.info("Recharge de " + etudiant.getEppn());
	        Map<String, Integer> coupons = new HashMap<String, Integer>();
	        for(TypeSalle typeSalle : getTypeSallesDebitables()) {
	        	coupons.put(typeSalle.getNom(), typeSalle.getMaxCoupon().get(etudiant.getTypeEtudiant()));
	        }
	        etudiant.setCoupons(coupons);
	        etudiant.setNbRecharge(etudiant.getNbRecharge() + 1);
	        etudiant.setDateRecharge(new Date());
	        return true;
		} else {
			return false;
		}
	}

	public void createNewTagLog(Etudiant etudiant, Salle salle, String eppnInit) {
		TagLog tagLog = new TagLog();
		tagLog.setEtudiant(etudiant);
		tagLog.setDate(new Date());
		tagLog.setSalle(salle);
		tagLog.setTarif(salle.getTarif());
		tagLog.setEppnInit(eppnInit);
		tagLogRepository.persist(tagLog);
	}

	
}
