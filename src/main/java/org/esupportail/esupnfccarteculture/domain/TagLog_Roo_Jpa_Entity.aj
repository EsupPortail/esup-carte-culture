// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.esupportail.esupnfccarteculture.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import org.esupportail.esupnfccarteculture.domain.TagLog;

privileged aspect TagLog_Roo_Jpa_Entity {
    
    declare @type: TagLog: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long TagLog.id;
    
    @Version
    @Column(name = "version")
    private Integer TagLog.version;
    
    public Long TagLog.getId() {
        return this.id;
    }
    
    public void TagLog.setId(Long id) {
        this.id = id;
    }
    
    public Integer TagLog.getVersion() {
        return this.version;
    }
    
    public void TagLog.setVersion(Integer version) {
        this.version = version;
    }
    
}
