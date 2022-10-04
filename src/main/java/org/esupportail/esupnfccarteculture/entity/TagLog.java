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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

@Configurable
@Entity
public class TagLog {

	@ManyToOne
    private Etudiant etudiant;

    @ManyToOne
    private Salle salle;

    @DateTimeFormat(style = "MM")
    private Date date;

    private int tarif;

    private String eppnInit;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	@Version
	@Column(name = "version")
	private Integer version;

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Etudiant getEtudiant() {
		return this.etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Salle getSalle() {
		return this.salle;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getTarif() {
		return this.tarif;
	}

	public void setTarif(int tarif) {
		this.tarif = tarif;
	}

	public String getEppnInit() {
		return this.eppnInit;
	}

	public void setEppnInit(String eppnInit) {
		this.eppnInit = eppnInit;
	}

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

	public String getEtudiantNomPrenom() {
		return getEtudiant().getNom() + " " + getEtudiant().getPrenom();
	}

	public long getEtudiantId() {
		return getEtudiant().getId();
	}

	public String getSalleNom() {
		return getSalle().getNom();
	}

	public String getTarifEuro() {
		BigDecimal tarifBig = BigDecimal.valueOf(getTarif());
		DecimalFormat decimalFormat = new java.text.DecimalFormat("####.00");
		return decimalFormat.format(tarifBig.divide(BigDecimal.valueOf(100)));
	}

}
