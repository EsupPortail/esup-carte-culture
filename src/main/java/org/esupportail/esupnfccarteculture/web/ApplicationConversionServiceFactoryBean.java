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
package org.esupportail.esupnfccarteculture.web;

import org.esupportail.esupnfccarteculture.entity.*;
import org.esupportail.esupnfccarteculture.repository.*;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import javax.annotation.Resource;

@Configurable
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Resource
    private SalleRepository salleRepository;

    @Resource
    private TagLogRepository tagLogRepository;

    @Resource
    private TagLogGestRepository tagLogGestRepository;

    @Resource
    private EtudiantRepository etudiantRepository;

    @Resource
    private GestionnaireRepository gestionnaireRepository;

    public Converter<Gestionnaire, String> getGestionnaireToStringConverter() {
        return new Converter<Gestionnaire, String>() {
            public String convert(Gestionnaire gestionnaire) {
                return new StringBuilder().append(gestionnaire.getEppn()).append(' ').append(gestionnaire.getNom()).append(' ').append(gestionnaire.getPrenom()).toString();
            }
        };
    }

    public Converter<Etudiant, String> getEtudiantToStringConverter() {
        return new Converter<Etudiant, String>() {
            public String convert(Etudiant etudiant) {
                return new StringBuilder().append(etudiant.getEppn()).append(' ').append(etudiant.getCsn()).append(' ').append(etudiant.getCivilite()).append(' ').append(etudiant.getNom()).toString();
            }
        };
    }

    public Converter<TagLog, String> getTagLogToStringConverter() {
        return new Converter<TagLog, String>() {
            public String convert(TagLog tagLog) {
                return new StringBuilder().append(tagLog.getDate()).append(' ').append(tagLog.getTarif()).append(' ').append(tagLog.getEppnInit()).toString();
            }
        };
    }

    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }

    public Converter<TagLogGest, String> getTagLogGestToStringConverter() {
        return new Converter<TagLogGest, String>() {
            public String convert(TagLogGest tagLogGest) {
                return new StringBuilder().append(tagLogGest.getEppn()).append(' ').append(tagLogGest.getSalle()).append(' ').append(tagLogGest.getDate()).append(' ').append(tagLogGest.getEppnInit()).toString();
            }
        };
    }

    public Converter<Long, Salle> getIdToSalleConverter() {
        return new Converter<Long, Salle>() {
            public Salle convert(Long id) {
                return salleRepository.findSalle(id);
            }
        };
    }

    public Converter<Long, TagLog> getIdToTagLogConverter() {
        return new Converter<Long, TagLog>() {
            public TagLog convert(Long id) {
                return tagLogRepository.findTagLog(id);
            }
        };
    }

    public Converter<String, TagLog> getStringToTagLogConverter() {
        return new Converter<String, TagLog>() {
            public TagLog convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), TagLog.class);
            }
        };
    }

    public Converter<String, TagLogGest> getStringToTagLogGestConverter() {
        return new Converter<String, TagLogGest>() {
            public TagLogGest convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), TagLogGest.class);
            }
        };
    }

    public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getEtudiantToStringConverter());
        registry.addConverter(getIdToEtudiantConverter());
        registry.addConverter(getStringToEtudiantConverter());
        registry.addConverter(getGestionnaireToStringConverter());
        registry.addConverter(getIdToGestionnaireConverter());
        registry.addConverter(getStringToGestionnaireConverter());
        registry.addConverter(getSalleToStringConverter());
        registry.addConverter(getIdToSalleConverter());
        registry.addConverter(getStringToSalleConverter());
        registry.addConverter(getTagLogToStringConverter());
        registry.addConverter(getIdToTagLogConverter());
        registry.addConverter(getStringToTagLogConverter());
        registry.addConverter(getTagLogGestToStringConverter());
        registry.addConverter(getIdToTagLogGestConverter());
        registry.addConverter(getStringToTagLogGestConverter());
    }

    public Converter<String, Salle> getStringToSalleConverter() {
        return new Converter<String, Salle>() {
            public Salle convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Salle.class);
            }
        };
    }

    public Converter<Long, Etudiant> getIdToEtudiantConverter() {
        return new Converter<Long, Etudiant>() {
            public Etudiant convert(Long id) {
                return etudiantRepository.findEtudiant(id);
            }
        };
    }

    public Converter<String, Gestionnaire> getStringToGestionnaireConverter() {
        return new Converter<String, Gestionnaire>() {
            public Gestionnaire convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Gestionnaire.class);
            }
        };
    }

    public Converter<Long, Gestionnaire> getIdToGestionnaireConverter() {
        return new Converter<Long, Gestionnaire>() {
            public Gestionnaire convert(Long id) {
                return gestionnaireRepository.findGestionnaire(id);
            }
        };
    }

    public Converter<Salle, String> getSalleToStringConverter() {
        return new Converter<Salle, String>() {
            public String convert(Salle salle) {
                return new StringBuilder().append(salle.getNom()).append(' ').append(salle.getLieu()).append(' ').append(salle.getTypeSalle()).append(' ').append(salle.getTarif()).toString();
            }
        };
    }

    public Converter<Long, TagLogGest> getIdToTagLogGestConverter() {
        return new Converter<Long, TagLogGest>() {
            public TagLogGest convert(Long id) {
                return tagLogGestRepository.findTagLogGest(id);
            }
        };
    }

    public Converter<String, Etudiant> getStringToEtudiantConverter() {
        return new Converter<String, Etudiant>() {
            public Etudiant convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Etudiant.class);
            }
        };
    }
}
