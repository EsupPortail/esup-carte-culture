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
package org.esupportail.esupnfccarteculture.ldap;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Collection;
import java.util.List;

public class PersonLdap {
	
	private String uid;
	private String cn ;
	private String sn; 
	private String givenName ;
	private String displayName ;
	private String schacDateOfBirth;
	private String mail ;
	private String md5UserPassword ;
	private String cryptUserPassword ;
	private String shaUserPassword ;
	private List<String> eduPersonAffiliation ;
	private String eduPersonPrimaryAffiliation ;
	private String eduPersonPrincipalName ;
	private String mailDrop ;
	private String mailHost ;
	private String sambaSID ;
	private String sambaPrimaryGroupSID ;
	private String sambaPwdLastSet ;
	private String sambaLMPassword ;
	private String sambaNTPassword ;
	private String sambaAcctFlags ;
	private String homeDirectory ;
	private String uidNumber ;
	private String gidNumber ;
	private String postalAddress ;
	private String facsimileTelephoneNumber ;
	private String telephoneNumber ;
	private String supannCivilite ;
	private String supannListeRouge ;
	private String supannEtablissement ;
	private String supannEntiteAffectation ;
	private String supannEntiteAffectationPrincipale ;
	private String supannEmpId ;
	private String supannEmpCorps ;
	private String supannActivite ;
	private String supannAutreTelephone ;
	private String supannCodeINE ;
	private String supannEtuId ;
	private String supannEtuEtape ;
	private String supannEtuAnneeInscription ;
	private String supannEtuSecteurDisciplinaire ;
	private String supannEtuDiplome ;
	private String supannEtuTypeDiplome ;
	private List<String> supannEtuCursusAnnee ;
	private String supannParrainDN ;
	private String supannMailPerso ;
	private String supannAliasLogin ;
	private List<String> supannRefId;
	private String supannRoleGenerique ;
	private String supannAutreMail ;
	private Long mailuserquota ;

    public static PersonLdap fromJsonToPersonLdap(String json) {
        return new JSONDeserializer<PersonLdap>()
        .use(null, PersonLdap.class).deserialize(json);
    }

    public static String toJsonArray(Collection<PersonLdap> collection) {
        return new JSONSerializer()
        .exclude("*.class").serialize(collection);
    }

    public static String toJsonArray(Collection<PersonLdap> collection, String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(collection);
    }

    public static Collection<PersonLdap> fromJsonArrayToPersonLdaps(String json) {
        return new JSONDeserializer<List<PersonLdap>>()
        .use("values", PersonLdap.class).deserialize(json);
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCn() {
        return this.cn;
    }

    public String getMailDrop() {
        return this.mailDrop;
    }

    public String getSambaAcctFlags() {
        return this.sambaAcctFlags;
    }

    public void setSupannEtuEtape(String supannEtuEtape) {
        this.supannEtuEtape = supannEtuEtape;
    }

    public void setSupannCivilite(String supannCivilite) {
        this.supannCivilite = supannCivilite;
    }

    public String getSupannEtuId() {
        return this.supannEtuId;
    }

    public String getSupannAutreMail() {
        return this.supannAutreMail;
    }

    public String getSambaPwdLastSet() {
        return this.sambaPwdLastSet;
    }

    public void setSupannActivite(String supannActivite) {
        this.supannActivite = supannActivite;
    }

    public void setGidNumber(String gidNumber) {
        this.gidNumber = gidNumber;
    }

    public String getSupannActivite() {
        return this.supannActivite;
    }

    public String getMailHost() {
        return this.mailHost;
    }

    public void setCryptUserPassword(String cryptUserPassword) {
        this.cryptUserPassword = cryptUserPassword;
    }

    public void setSambaLMPassword(String sambaLMPassword) {
        this.sambaLMPassword = sambaLMPassword;
    }

    public String getSupannEtuDiplome() {
        return this.supannEtuDiplome;
    }

    public void setMailuserquota(Long mailuserquota) {
        this.mailuserquota = mailuserquota;
    }

    public String getSambaPrimaryGroupSID() {
        return this.sambaPrimaryGroupSID;
    }

    public void setSupannRoleGenerique(String supannRoleGenerique) {
        this.supannRoleGenerique = supannRoleGenerique;
    }

    public Long getMailuserquota() {
        return this.mailuserquota;
    }

    public void setShaUserPassword(String shaUserPassword) {
        this.shaUserPassword = shaUserPassword;
    }

    public String getSchacDateOfBirth() {
        return this.schacDateOfBirth;
    }

    public String getMd5UserPassword() {
        return this.md5UserPassword;
    }

    public String getSupannEntiteAffectationPrincipale() {
        return this.supannEntiteAffectationPrincipale;
    }

    public void setSupannAutreTelephone(String supannAutreTelephone) {
        this.supannAutreTelephone = supannAutreTelephone;
    }

    public void setSupannAutreMail(String supannAutreMail) {
        this.supannAutreMail = supannAutreMail;
    }

    public String getSambaNTPassword() {
        return this.sambaNTPassword;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }

    public void setSupannEntiteAffectation(String supannEntiteAffectation) {
        this.supannEntiteAffectation = supannEntiteAffectation;
    }

    public void setSupannMailPerso(String supannMailPerso) {
        this.supannMailPerso = supannMailPerso;
    }

    public String getSupannEtuAnneeInscription() {
        return this.supannEtuAnneeInscription;
    }

    public String getSn() {
        return this.sn;
    }

    public void setSupannEmpCorps(String supannEmpCorps) {
        this.supannEmpCorps = supannEmpCorps;
    }

    public void setSupannEtuDiplome(String supannEtuDiplome) {
        this.supannEtuDiplome = supannEtuDiplome;
    }

    public void setMailDrop(String mailDrop) {
        this.mailDrop = mailDrop;
    }

    public void setSupannEtuCursusAnnee(List<String> supannEtuCursusAnnee) {
        this.supannEtuCursusAnnee = supannEtuCursusAnnee;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setMd5UserPassword(String md5UserPassword) {
        this.md5UserPassword = md5UserPassword;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public void setEduPersonAffiliation(List<String> eduPersonAffiliation) {
        this.eduPersonAffiliation = eduPersonAffiliation;
    }

    public String getSupannListeRouge() {
        return this.supannListeRouge;
    }

    public void setSupannEtuSecteurDisciplinaire(String supannEtuSecteurDisciplinaire) {
        this.supannEtuSecteurDisciplinaire = supannEtuSecteurDisciplinaire;
    }

    public void setSupannListeRouge(String supannListeRouge) {
        this.supannListeRouge = supannListeRouge;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getSupannEtablissement() {
        return this.supannEtablissement;
    }

    public String getSupannMailPerso() {
        return this.supannMailPerso;
    }

    public String getGivenName() {
        return this.givenName;
    }

    public void setSambaPwdLastSet(String sambaPwdLastSet) {
        this.sambaPwdLastSet = sambaPwdLastSet;
    }

    public String getSupannRoleGenerique() {
        return this.supannRoleGenerique;
    }

    public void setSupannEntiteAffectationPrincipale(String supannEntiteAffectationPrincipale) {
        this.supannEntiteAffectationPrincipale = supannEntiteAffectationPrincipale;
    }

    public String getSupannEtuEtape() {
        return this.supannEtuEtape;
    }

    public String getHomeDirectory() {
        return this.homeDirectory;
    }

    public String getUidNumber() {
        return this.uidNumber;
    }

    public String getSupannEntiteAffectation() {
        return this.supannEntiteAffectation;
    }

    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }

    public void setSupannRefId(List<String> supannRefId) {
        this.supannRefId = supannRefId;
    }

    public void setSambaSID(String sambaSID) {
        this.sambaSID = sambaSID;
    }

    public String getSambaLMPassword() {
        return this.sambaLMPassword;
    }

    public String getEduPersonPrimaryAffiliation() {
        return this.eduPersonPrimaryAffiliation;
    }

    public void setEduPersonPrincipalName(String eduPersonPrincipalName) {
        this.eduPersonPrincipalName = eduPersonPrincipalName;
    }

    public void setSupannEmpId(String supannEmpId) {
        this.supannEmpId = supannEmpId;
    }

    public String getSupannCodeINE() {
        return this.supannCodeINE;
    }

    public void setSupannCodeINE(String supannCodeINE) {
        this.supannCodeINE = supannCodeINE;
    }

    public void setSupannAliasLogin(String supannAliasLogin) {
        this.supannAliasLogin = supannAliasLogin;
    }

    public void setSupannEtuTypeDiplome(String supannEtuTypeDiplome) {
        this.supannEtuTypeDiplome = supannEtuTypeDiplome;
    }

    public void setSambaAcctFlags(String sambaAcctFlags) {
        this.sambaAcctFlags = sambaAcctFlags;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getFacsimileTelephoneNumber() {
        return this.facsimileTelephoneNumber;
    }

    public String getShaUserPassword() {
        return this.shaUserPassword;
    }

    public String getSupannEtuSecteurDisciplinaire() {
        return this.supannEtuSecteurDisciplinaire;
    }

    public String getSupannAliasLogin() {
        return this.supannAliasLogin;
    }

    public String getSupannEtuTypeDiplome() {
        return this.supannEtuTypeDiplome;
    }

    public List<String> getEduPersonAffiliation() {
        return this.eduPersonAffiliation;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMail() {
        return this.mail;
    }

    public String getSupannParrainDN() {
        return this.supannParrainDN;
    }

    public String getSambaSID() {
        return this.sambaSID;
    }

    public void setSupannEtuAnneeInscription(String supannEtuAnneeInscription) {
        this.supannEtuAnneeInscription = supannEtuAnneeInscription;
    }

    public String getSupannEmpCorps() {
        return this.supannEmpCorps;
    }

    public List<String> getSupannRefId() {
        return this.supannRefId;
    }

    public String getSupannAutreTelephone() {
        return this.supannAutreTelephone;
    }

    public String getSupannCivilite() {
        return this.supannCivilite;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setSupannEtuId(String supannEtuId) {
        this.supannEtuId = supannEtuId;
    }

    public void setSchacDateOfBirth(String schacDateOfBirth) {
        this.schacDateOfBirth = schacDateOfBirth;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCryptUserPassword() {
        return this.cryptUserPassword;
    }

    public void setSambaPrimaryGroupSID(String sambaPrimaryGroupSID) {
        this.sambaPrimaryGroupSID = sambaPrimaryGroupSID;
    }

    public void setSambaNTPassword(String sambaNTPassword) {
        this.sambaNTPassword = sambaNTPassword;
    }

    public String getGidNumber() {
        return this.gidNumber;
    }

    public void setFacsimileTelephoneNumber(String facsimileTelephoneNumber) {
        this.facsimileTelephoneNumber = facsimileTelephoneNumber;
    }

    public void setHomeDirectory(String homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    public String getPostalAddress() {
        return this.postalAddress;
    }

    public void setSupannParrainDN(String supannParrainDN) {
        this.supannParrainDN = supannParrainDN;
    }

    public void setEduPersonPrimaryAffiliation(String eduPersonPrimaryAffiliation) {
        this.eduPersonPrimaryAffiliation = eduPersonPrimaryAffiliation;
    }

    public String getSupannEmpId() {
        return this.supannEmpId;
    }

    public List<String> getSupannEtuCursusAnnee() {
        return this.supannEtuCursusAnnee;
    }

    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }

    public void setSupannEtablissement(String supannEtablissement) {
        this.supannEtablissement = supannEtablissement;
    }

    public String getEduPersonPrincipalName() {
        return this.eduPersonPrincipalName;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String toJson() {
        return new JSONSerializer()
        .exclude("*.class").serialize(this);
    }

    public String toJson(String[] fields) {
        return new JSONSerializer()
        .include(fields).exclude("*.class").serialize(this);
    }
}
	
	
