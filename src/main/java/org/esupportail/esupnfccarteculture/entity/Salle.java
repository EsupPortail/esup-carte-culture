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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    private String nom;

    @Column(columnDefinition = "TEXT")
    private String lieu;

    @NotNull
    private String typeSalle;

    private Integer tarif;

    transient String tarifString;

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLieu() {
        return this.lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getTypeSalle() {
        return this.typeSalle;
    }

    public void setTypeSalle(String typeSalle) {
        this.typeSalle = typeSalle;
    }

    public int getTarif() {
        return this.tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
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

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }


    public void setTarifString(String tarifString) {
        String tarifOld = "0";
        if (tarifString != null) {
            tarifOld = tarifString.replace(",", ".");
        }
        BigDecimal tarifBig = new BigDecimal(tarifOld);
        setTarif(tarifBig.multiply(BigDecimal.valueOf(100)).intValue());
    }

    public String getTarifString() {
        if (tarif != null && tarif > 0) {
            BigDecimal tarifBig = BigDecimal.valueOf(getTarif());
            DecimalFormat decimalFormat = new java.text.DecimalFormat("####.00");
            return decimalFormat.format(tarifBig.divide(BigDecimal.valueOf(100)));
        } else {
            return "0";
        }
    }
}
