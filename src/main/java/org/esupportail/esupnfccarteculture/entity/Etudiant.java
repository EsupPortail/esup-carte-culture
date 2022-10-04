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
package org.esupportail.esupnfccarteculture.entity;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;
import java.util.Map.Entry;

@Configurable
@Entity
public class Etudiant {

		@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Version
	@Column(name = "version")
	private Integer version;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getCivilite() {
		return this.civilite;
	}

	public String getEppn() {
		return this.eppn;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}

	public void setNbRecharge(int nbRecharge) {
		this.nbRecharge = nbRecharge;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public int getNbRecharge() {
		return this.nbRecharge;
	}

	public void setEppn(String eppn) {
		this.eppn = eppn;
	}

	public String getCsn() {
		return this.csn;
	}

	public Date getDateRecharge() {
		return this.dateRecharge;
	}

	public Date getDateInscription() {
		return this.dateInscription;
	}

	public String getNiveauEtudes() {
		return this.niveauEtudes;
	}

	public String getAffiliation() {
		return this.affiliation;
	}

	public void setCsn(String csn) {
		this.csn = csn;
	}

	public void setCivilite(String civilite) {
		this.civilite = civilite;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public void setDateInscription(Date dateInscription) {
		this.dateInscription = dateInscription;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return this.email;
	}

	public void setDateRecharge(Date dateRecharge) {
		this.dateRecharge = dateRecharge;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public Map<String, Integer> getCoupons() {
		return this.coupons;
	}

	public String getEtablissement() {
		return this.etablissement;
	}

	public String getFiliere() {
		return this.filiere;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setCoupons(Map<String, Integer> coupons) {
		this.coupons = coupons;
	}

	public Date getDateNaissance() {
		return this.dateNaissance;
	}

	public void setNiveauEtudes(String niveauEtudes) {
		this.niveauEtudes = niveauEtudes;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public enum Type {ETUDIANT, PERSONNEL, INVITE};
	
    @Column(unique = true)
    private String eppn;

    private String csn;
    
    private String civilite;

    private String nom;

    private String prenom;

    private String email;
    
    // map coupons : key = nom du type de salle, value = nombre de coupons
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Integer> coupons = new HashMap<String, Integer>();

    private Integer nbRecharge = 0;

    private String filiere;    
    
    private String niveauEtudes;

    private String affiliation;

    private String etablissement;

    @DateTimeFormat(style = "MM")
    private Date dateNaissance;
    
    @DateTimeFormat(style = "MM")
    private Date dateRecharge;

    @DateTimeFormat(style = "MM")
    private Date dateInscription;
    
    @Enumerated(EnumType.STRING)
    private Type typeEtudiant;
    
    public Etudiant() {
		super();
	}
    
    public Etudiant(String eppn, Date dateInscription, List<String> eduPersonAffiliation) {
		super();
		this.eppn = eppn;
		this.dateInscription = dateInscription;
		if(eduPersonAffiliation.contains("student")) {
			this.typeEtudiant = Type.ETUDIANT;
		} else if(eduPersonAffiliation.contains("affiliate")) {
			this.typeEtudiant = Type.INVITE;
		} else {
			this.typeEtudiant = Type.PERSONNEL;
		}
	}

	public Type getTypeEtudiant() {
		return typeEtudiant;
	}

	public int getCouponsSum() {
    	if(coupons != null) {
    		return coupons.values().stream().mapToInt(i -> i).sum();
    	} else {
    		return 0;
    	}
    }

    public String getCouponsLibelleLn() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Integer>> iter = coupons.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Integer> entry = iter.next();
			sb.append("coupon " + entry.getKey());
			sb.append(" : ");
			sb.append(entry.getValue());
			if (iter.hasNext()) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public String getCouponsLibelle() {
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, Integer>> iter = coupons.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Integer> entry = iter.next();
			sb.append("coupon " + entry.getKey());
			sb.append(" : ");
			sb.append(entry.getValue());
			if (iter.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
    
}
