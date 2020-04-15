package ru.kck.usb.zags.fataloiv.dao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author Ilya V. Kupriyanov
 * @version 08/02/2020
 */

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Death {
    private String regInfoId;
    private Date regInfoDate;
    private Date regDate;
    private Date recordDate;
    private String regNumber;
    private String zagsName;
    private String zagsCode;
    private String stateDesc;
    private String stateCode;
    private Date stateDate;
    private String deathCerts;
    private String surname;
    private String firstname;
    private String middlename;
    private String sex;
    private String citizenshipOKSM;
    private String citizenshipCountryName;
    private String birthDate;
    private String birthPlace;
    private String addressRF;
    private String addressRFText;
    private String addressNotRF;
    private String deathDate;
    private String deathTime;
    private String deathPlace;
    private String identityDocInfo;
    private String deathCause;
    private String deathDocument;
    private String deathDocIssuer;
    private String deathDocFIOIP;
    private String deathDocFIOFL;
    private String deathActChangesInfo;
    private String smevMessageId;

    private Date versionDate;
    private String versionNumber;
    private String deathCertsRepeat;
    private Boolean unindentPerson;
    private String aboutPerson;
    private String aboutDeath;
    private String childDeath;
    private String doctor;
    private String applicantInfo;

    public String getRegInfoId() {
        return regInfoId;
    }

    public void setRegInfoId(String regInfoId) {
        this.regInfoId = regInfoId;
    }

    public Date getRegInfoDate() {
        return regInfoDate;
    }

    public void setRegInfoDate(Date regInfoDate) {
        this.regInfoDate = regInfoDate;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Date getVersionDate() {
        return versionDate;
    }

    public void setVersionDate(Date versionDate) {
        this.versionDate = versionDate;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getZagsName() {
        return zagsName;
    }

    public void setZagsName(String zagsName) {
        this.zagsName = zagsName;
    }

    public String getZagsCode() {
        return zagsCode;
    }

    public void setZagsCode(String zagsCode) {
        this.zagsCode = zagsCode;
    }

    public String getStateDesc() {
        return stateDesc;
    }

    public void setStateDesc(String stateDesc) {
        this.stateDesc = stateDesc;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public Date getStateDate() {
        return stateDate;
    }

    public void setStateDate(Date stateDate) {
        this.stateDate = stateDate;
    }

    public String getDeathCerts() {
        return deathCerts;
    }

    public void setDeathCerts(String deathCerts) {
        this.deathCerts = deathCerts;
    }

    public String getDeathCertsRepeat() {
        return deathCertsRepeat;
    }

    public void setDeathCertsRepeat(String deathCertsRepeat) {
        this.deathCertsRepeat = deathCertsRepeat;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCitizenshipOKSM() {
        return citizenshipOKSM;
    }

    public void setCitizenshipOKSM(String citizenshipOKSM) {
        this.citizenshipOKSM = citizenshipOKSM;
    }

    public String getCitizenshipCountryName() {
        return citizenshipCountryName;
    }

    public void setCitizenshipCountryName(String citizenshipCountryName) {
        this.citizenshipCountryName = citizenshipCountryName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getAddressRF() {
        return addressRF;
    }

    public void setAddressRF(String addressRF) {
        this.addressRF = addressRF;
    }

    public String getAddressRFText() {
        return addressRFText;
    }

    public void setAddressRFText(String addressRFText) {
        this.addressRFText = addressRFText;
    }

    public String getAddressNotRF() {
        return addressNotRF;
    }

    public void setAddressNotRF(String addressNotRF) {
        this.addressNotRF = addressNotRF;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(String deathTime) {
        this.deathTime = deathTime;
    }

    public String getDeathPlace() {
        return deathPlace;
    }

    public void setDeathPlace(String deathPlace) {
        this.deathPlace = deathPlace;
    }

    public String getAboutPerson() {
        return aboutPerson;
    }

    public void setAboutPerson(String aboutPerson) {
        this.aboutPerson = aboutPerson;
    }

    public String getIdentityDocInfo() {
        return identityDocInfo;
    }

    public void setIdentityDocInfo(String identityDocInfo) {
        this.identityDocInfo = identityDocInfo;
    }

    public String getAboutDeath() {
        return aboutDeath;
    }

    public void setAboutDeath(String aboutDeath) {
        this.aboutDeath = aboutDeath;
    }

    public String getChildDeath() {
        return childDeath;
    }

    public void setChildDeath(String childDeath) {
        this.childDeath = childDeath;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getDeathCause() {
        return deathCause;
    }

    public void setDeathCause(String deathCause) {
        this.deathCause = deathCause;
    }

    public String getDeathDocument() {
        return deathDocument;
    }

    public void setDeathDocument(String deathDocument) {
        this.deathDocument = deathDocument;
    }

    public String getDeathDocIssuer() {
        return deathDocIssuer;
    }

    public void setDeathDocIssuer(String deathDocIssuer) {
        this.deathDocIssuer = deathDocIssuer;
    }

    public String getDeathDocFIOIP() {
        return deathDocFIOIP;
    }

    public void setDeathDocFIOIP(String deathDocFIOIP) {
        this.deathDocFIOIP = deathDocFIOIP;
    }

    public String getDeathDocFIOFL() {
        return deathDocFIOFL;
    }

    public void setDeathDocFIOFL(String deathDocFIOFL) {
        this.deathDocFIOFL = deathDocFIOFL;
    }

    public String getDeathActChangesInfo() {
        return deathActChangesInfo;
    }

    public void setDeathActChangesInfo(String deathActChangesInfo) {
        this.deathActChangesInfo = deathActChangesInfo;
    }

    public String getApplicantInfo() {
        return applicantInfo;
    }

    public void setApplicantInfo(String applicantInfo) {
        this.applicantInfo = applicantInfo;
    }

    public String getSmevMessageId() {
        return smevMessageId;
    }

    public void setSmevMessageId(String smevMessageId) {
        this.smevMessageId = smevMessageId;
    }

    public Boolean getUnindentPerson() {
        return unindentPerson;
    }

    public void setUnindentPerson(Boolean unindentPerson) {
        this.unindentPerson = unindentPerson;
    }
}
