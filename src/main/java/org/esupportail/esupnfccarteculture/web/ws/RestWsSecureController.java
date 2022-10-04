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

import org.esupportail.esupnfccarteculture.entity.Etudiant;
import org.esupportail.esupnfccarteculture.repository.EtudiantRepository;
import org.esupportail.esupnfccarteculture.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@RequestMapping("/ws-secure")
@Controller
public class RestWsSecureController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Resource
	TagService tagService;

	@Resource
	EtudiantRepository etudiantRepository;

	@RequestMapping(value="/nb-coupon", produces = "text/html")
	@ResponseBody
	public String getNbCoupon(@RequestParam String eppn, @RequestParam String location) {
		if(etudiantRepository.countFindEtudiantsByEppnEquals(eppn) > 0) {
			log.info("get nb coupon : " + eppn);
			return tagService.checkEtudiantCoupon(eppn, location);
		}
		return null;
	}

	@RequestMapping(value="/debit-coupon", produces = "text/html")
	@ResponseBody
	public String debitCoupon(@RequestParam String eppn, @RequestParam String location) {
		if(etudiantRepository.countFindEtudiantsByEppnEquals(eppn) > 0) {
			log.debug("debit coupon : " + eppn);
			Etudiant etudiant = etudiantRepository.findEtudiantsByEppnEquals(eppn).getSingleResult();
			if(tagService.debitCoupon(etudiant, location, "rest.api@univ-ville.fr")) {
				return tagService.checkEtudiantCoupon(eppn, location);
			} else {
				return "-1";
			}
		}
		return null;
	}

}
