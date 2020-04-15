<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

    <xsl:param name="smevMessageId"/>

    <xsl:template match="/">
        <DeathList>
        <xsl:apply-templates select="//*[local-name()='FATALOIVRequest']"/>
        </DeathList>
    </xsl:template>

    <xsl:template match="*[local-name()='FATALOIVRequest']">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="*[local-name()='СведРегСмертОИВ']">
        <xsl:element name="Death">
            <regInfoId> <!-- old column-->
                <xsl:value-of select="../@ИдСвед"/>
            </regInfoId>
            <regInfoDate> <!--old column-->
                <xsl:value-of select="../@ДатаСвед"/>
            </regInfoDate>
            <regDate> <!--old column-->
                <xsl:value-of select="@ДатаЗапис"/>
            </regDate>
            <regNumber> <!--old column-->
                <xsl:value-of select="@НомерЗапис"/>
            </regNumber>
            <!--ДатаВерс, НомерВерс-->
            <versionDate>  <!--new column-->
                <xsl:value-of select="@ДатаВерс"/>
            </versionDate>
            <versionNumber> <!--new column-->
                <xsl:value-of select="@НомерВерс"/>
            </versionNumber>
            <zagsName> <!--old column-->
                <xsl:value-of select="normalize-space(*[local-name()='ОрганЗАГС']/@НаимЗАГС)"/>
            </zagsName>
            <zagsCode> <!--old column-->
                <xsl:value-of select="*[local-name()='ОрганЗАГС']/@КодЗАГС"/>
            </zagsCode>
            <stateDesc> <!--old column-->
                <xsl:value-of select="*[local-name()='СтатусЗаписи']/@НаимСтатус"/>
            </stateDesc>
            <stateCode> <!--old column-->
                <xsl:value-of select="*[local-name()='СтатусЗаписи']/@КодСтатус"/>
            </stateCode>
            <stateDate>  <!--old column-->
                <xsl:value-of select="*[local-name()='СтатусЗаписи']/@ДатаНачСтатус"/>
            </stateDate>
            <xsl:if test="*[local-name()='СведСвидет']">
                <deathCerts><xsl:apply-templates select="*[local-name()='СведСвидет']"/></deathCerts>  <!--old column-->
            </xsl:if>
            <xsl:if test="*[local-name()='ПовтСвидет']">
                <deathCertsRepeat>{"deathCertsRepeat":[<xsl:apply-templates select="*[local-name()='ПовтСвидет']"/>]}</deathCertsRepeat>  <!--new column-->
            </xsl:if>
            <xsl:apply-templates select="*[local-name()='ПрдСведРег']"/>

            <xsl:if test="*[local-name()='СведИспрАГС'] | *[local-name()='СведВосстанАГС'] | *[local-name()='СведАннулирАГС']">
                <deathActChangesInfo>{"CORRECTION_ACT":[<xsl:apply-templates
                        select=".//*[local-name()='СведИспрАГС']"/>],"RECOVERY_ACT":[<xsl:apply-templates
                        select=".//*[local-name()='СведВосстанАГС']"/>],"CANCELLATION_ACT":[<xsl:apply-templates
                        select=".//*[local-name()='СведАннулирАГС']"/>]}</deathActChangesInfo><!--old column-->
            </xsl:if>

            <xsl:element name="applicantInfo">{"FL":{<xsl:if test="*[local-name()='СведЗаявительФЛ']">"FIO":{<xsl:call-template name="fio"> <!--new column-->
                <xsl:with-param name="current" select="*[local-name()='СведЗаявительФЛ']/*[local-name()='ФИОЗаявитель']"/>
            </xsl:call-template>},"POST":"<xsl:value-of select="*[local-name()='СведЗаявительФЛ']/@СведДолжность"/>","ADDRESS_RF":{<xsl:if test="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']">"ADDRESS_RF_TEXT":{"ADDRESS":"<xsl:value-of
                    select="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@АдрРФТекст"/>","OKTMO":"<xsl:value-of
                    select="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@ОКТМО"/>","SIGN_RF":"<xsl:value-of
                    select="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@ПрТипАдрРФ"/>"},"KLADR":{<xsl:apply-templates
                    select="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрКЛАДР']"/>},"FIAS":{<xsl:apply-templates
                    select="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрФИАС']"/>}</xsl:if>},"ADDRESS_NOT_RF":{<xsl:if
                    test="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']">"OKSM":"<xsl:value-of
                    select="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@ОКСМ"/>","COUNTRY":"<xsl:value-of
                    select="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@НаимСтраны"/>","ADDRESS":"<xsl:value-of
                    select="*[local-name()='СведЗаявительФЛ']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@АдрТекст"/>"</xsl:if>},"IDENT_DOC":{<xsl:if
                        test="*[local-name()='СведЗаявительФЛ']/*[local-name()='УдЛичнФЛ']">"DOCUMENT_CODE":"<xsl:value-of
                            select="*[local-name()='СведЗаявительФЛ']/*[local-name()='УдЛичнФЛ']/@КодВидДок"/>","DOCUMENT_NAME":"<xsl:value-of
                            select="*[local-name()='СведЗаявительФЛ']/*[local-name()='УдЛичнФЛ']/@НаимДок"/>","SERIES_NUMBER":"<xsl:value-of
                            select="*[local-name()='СведЗаявительФЛ']/*[local-name()='УдЛичнФЛ']/@СерНомДок"/>","ISSUER_DATE":"<xsl:value-of
                            select="*[local-name()='СведЗаявительФЛ']/*[local-name()='УдЛичнФЛ']/@ДатаДок"/>","ISSUER":"<xsl:value-of
                            select="*[local-name()='СведЗаявительФЛ']/*[local-name()='УдЛичнФЛ']/@ВыдДок"/>"</xsl:if>}</xsl:if>},"UL":{<xsl:if test="*[local-name()='СведЗаявительЮЛ']">"ORG_INFO":{"ORG_NAME":"<xsl:value-of select="*[local-name()='СведЗаявительЮЛ']/@НаимОрг"/>","ORG_ADDRESS":"<xsl:value-of
                    select="*[local-name()='СведЗаявительЮЛ']/@АдрОрг"/>"},"PERSON_INFO":{"FIO":{<xsl:call-template name="fio"> <!--new column-->
                    <xsl:with-param name="current" select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='ФИОЗаявитель']"/>
                </xsl:call-template>},"POST":"<xsl:value-of select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/@СведДолжность"/>","ADDRESS_RF":{<xsl:if
                    test="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']">"ADDRESS_RF_TEXT":{"ADDRESS":"<xsl:value-of
                        select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@АдрРФТекст"/>","OKTMO":"<xsl:value-of
                        select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@ОКТМО"/>","SIGN_RF":"<xsl:value-of
                        select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@ПрТипАдрРФ"/>"},"KLADR":{<xsl:apply-templates
                    select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрКЛАДР']"/>},"FIAS":{<xsl:apply-templates
                    select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрФИАС']"/>}</xsl:if>},"ADDRESS_NOT_RF":{<xsl:if
                    test="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']">"OKSM":"<xsl:value-of
                            select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@ОКСМ"/>","COUNTRY":"<xsl:value-of
                            select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@НаимСтраны"/>","ADDRESS":"<xsl:value-of
                            select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@АдрТекст"/>"</xsl:if>},"IDENT_DOC":{<xsl:if
                            test="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='УдЛичнФЛ']">"DOCUMENT_CODE":"<xsl:value-of
                            select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='УдЛичнФЛ']/@КодВидДок"/>","DOCUMENT_NAME":"<xsl:value-of
                            select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='УдЛичнФЛ']/@НаимДок"/>","SERIES_NUMBER":"<xsl:value-of
                            select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='УдЛичнФЛ']/@СерНомДок"/>","ISSUER_DATE":"<xsl:value-of
                            select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='УдЛичнФЛ']/@ДатаДок"/>","ISSUER":"<xsl:value-of
                            select="*[local-name()='СведЗаявительЮЛ']/*[local-name()='СведПредстОрг']/*[local-name()='УдЛичнФЛ']/@ВыдДок"/>"</xsl:if>}}</xsl:if>}}</xsl:element>
            <smevMessageId><xsl:value-of select="$smevMessageId"/></smevMessageId>
        </xsl:element>
    </xsl:template>

    <xsl:template match="*[local-name()='СведИспрАГС']">{"DATE":"<xsl:value-of
            select="@ДатаВнесИспр"/>","CONTENT":"<xsl:value-of
            select="@СодержИспр"/>","DOC_CODE":"<xsl:value-of
            select="@КодВидСвед"/>","DOC_NAME":"<xsl:value-of
            select="@НаимВидСвед"/>","BASE_DOC_INFO":{<xsl:apply-templates select="*[local-name()='СведДокИспрАГС']"/>}}<xsl:if test="position() != last()">,</xsl:if></xsl:template>

    <xsl:template match="*[local-name()='СведВосстанАГС']">{"DATE":"<xsl:value-of
            select="@ДатаВнесИспр"/>","CONTENT":"<xsl:value-of
            select="@СодержИспр"/>","DOC_CODE":"<xsl:value-of
            select="@КодВидСвед"/>","DOC_NAME":"<xsl:value-of
            select="@НаимВидСвед"/>","BASE_DOC_INFO":{<xsl:apply-templates select="*[local-name()='СведДокИспрАГС']"/>}}<xsl:if test="position() != last()">,</xsl:if></xsl:template>

    <xsl:template match="*[local-name()='СведАннулирАГС']">{"DATE":"<xsl:value-of
            select="@ДатаВнесИспр"/>","CONTENT":"<xsl:value-of
            select="@СодержИспр"/>","DOC_CODE":"<xsl:value-of
            select="@КодВидСвед"/>","DOC_NAME":"<xsl:value-of
            select="@НаимВидСвед"/>","BASE_DOC_INFO":{<xsl:apply-templates select="*[local-name()='СведДокИспрАГС']"/>}}<xsl:if test="position() != last()">,</xsl:if></xsl:template>

    <xsl:template match="*[local-name()='СведДокИспрАГС']">"MAIN_ACT":{<xsl:apply-templates
            select="*[local-name()='ЗапАктОсн']"/>},"ANOTHER_DOC_MARK":{<xsl:apply-templates
            select="*[local-name()='ДокОсн']"/>}</xsl:template>

    <!--<xsl:value-of select="normalize-space(*[local-name()='ДокОсн']/*[local-name()='СведПометка']/*[local-name()='Пометка'])"/>-->

    <xsl:template match="*[local-name()='ЗапАктОсн']">"CIVIL_STATUS_TYPE":"<xsl:value-of select="@ТипАГС"/>","TYPE_NAME":"<xsl:value-of select="@НаимТипЗапис"/>","RECORD_NUMBER":"<xsl:value-of select="@НомерЗапис"/>","RECORD_DATE":"<xsl:value-of select="@ДатаЗапис"/>","ZAGS":{<xsl:apply-templates select="*[local-name()='ОрганЗАГС']"/>}</xsl:template>
    <xsl:template match="*[local-name()='ОрганЗАГС']">"CRO_NAME":"<xsl:value-of select="@НаимЗАГС"/>","CRO_CODE":"<xsl:value-of select="@КодЗАГС"/>"</xsl:template>

    <xsl:template match="*[local-name()='ДокОсн']">"DOC_NAME":"<xsl:value-of select="@НаимДок"/>","DOC_DATE":"<xsl:value-of select="@ДатаДок"/>","DOC_CODE":"<xsl:value-of select="@КодДок"/>","SERIES_NUMBER":"<xsl:value-of select="@СерНомДок"/>","MARK":"<xsl:value-of
            select="*[local-name()='СведПометка']/*[local-name()='Пометка']"/>","ORG_NAME":"<xsl:value-of select="translate(*[local-name()='НаимОрг'],'\&quot;', '')"/>","FIOIP":{<xsl:if
            test="*[local-name()='ФИОИП']"><xsl:call-template name="fio"> <!--new column-->
        <xsl:with-param name="current" select="*[local-name()='ФИОИП']"/>
    </xsl:call-template></xsl:if>},"FIOFL":{<xsl:if
            test="*[local-name()='ФИОФЛ']"><xsl:call-template name="fio">
        <xsl:with-param name="current" select="*[local-name()='ФИОФЛ']"/></xsl:call-template></xsl:if>}</xsl:template>

    <xsl:template match="*[local-name()='СведСвидет']">{"SERIES":"<xsl:value-of select="@СерияСвидет"/>","NUMBER":"<xsl:value-of select="@НомерСвидет"/>","DATE":"<xsl:value-of select="@ДатаВыдСвидет"/>"}</xsl:template>
    <xsl:template match="*[local-name()='ПовтСвидет']">{"SERIES":"<xsl:value-of select="@СерияСвидет"/>","NUMBER":"<xsl:value-of select="@НомерСвидет"/>","DATE":"<xsl:value-of select="@ДатаВыдСвидет"/>"}<xsl:if test="position() != last()">,</xsl:if></xsl:template>

    <xsl:template match="*[local-name()='ПрдСведРег']">
        <xsl:apply-templates select="*[local-name()='СведУмер']"/>
        <xsl:apply-templates select="*[local-name()='СведДокСмерт']"/>
    </xsl:template>

    <xsl:template match="*[local-name()='СведУмер']">

        <xsl:choose>
            <xsl:when test="@ПрНеустанЛицо">
                <unindentPerson>true</unindentPerson> <!--new column-->
            </xsl:when>
            <xsl:otherwise>
                <unindentPerson>false</unindentPerson>
            </xsl:otherwise>
        </xsl:choose>

        <xsl:if test="*[local-name()='ФИОУмер']/*[local-name()='Фамилия']/text()">
            <surname>  <!--old column-->
                <xsl:value-of select="*[local-name()='ФИОУмер']/*[local-name()='Фамилия']"/>
            </surname>
        </xsl:if>
        <xsl:if test="*[local-name()='ФИОУмер']/*[local-name()='Имя']/text()">
            <firstname> <!--old column-->
                <xsl:value-of select="*[local-name()='ФИОУмер']/*[local-name()='Имя']"/>
            </firstname>
        </xsl:if>
        <xsl:if test="*[local-name()='ФИОУмер']/*[local-name()='Отчество']/text()">
            <middlename> <!--old column-->
                <xsl:value-of select="*[local-name()='ФИОУмер']/*[local-name()='Отчество']"/>
            </middlename>
        </xsl:if>
        <xsl:if test="@Пол">
            <sex> <!--old column-->
                <xsl:value-of select="@Пол"/>
            </sex>
        </xsl:if>
        <xsl:if test="*[local-name()='ДатаРождДок'] | *[local-name()='ДатаРождКаленд']">
            <xsl:element name="birthDate">{"CALEND":"<xsl:value-of select="*[local-name()='ДатаРождКаленд']"/>","DOC":{<xsl:if
                    test="*[local-name()='ДатаРождДок']">"Y":"<xsl:value-of
                    select="*[local-name()='ДатаРождДок']/*[local-name()='Год']"/>","M":"<xsl:value-of
                            select="*[local-name()='ДатаРождДок']/*[local-name()='Месяц']"/>","D":"<xsl:value-of
                            select="*[local-name()='ДатаРождДок']/*[local-name()='День']"/>"</xsl:if>}}</xsl:element><!--old column-->
        </xsl:if>

        <xsl:call-template name="place"> <!--old column-->
            <xsl:with-param name="use" select="'birth'"/>
            <xsl:with-param name="current" select="*[local-name()='МестоРожден']"/>
        </xsl:call-template>

        <xsl:if test="*[local-name()='Гражданство']">
            <xsl:element name="citizenshipCountryName"><xsl:value-of select="*[local-name()='Гражданство']/@НаимСтраны"/></xsl:element> <!--old column-->
            <xsl:element name="citizenshipOKSM"><xsl:value-of select="*[local-name()='Гражданство']/@ОКСМ"/></xsl:element> <!--old column-->
        </xsl:if>

        <xsl:if test="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']">
            <xsl:element name="addressRF">{"KLADR":{<xsl:if test="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрКЛАДР']">
                <xsl:apply-templates select="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрКЛАДР']"/> <!--checked!-->
                </xsl:if>},"FIAS":{<xsl:if test="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрФИАС']">
                    <xsl:apply-templates select="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрФИАС']"/> <!--checked!-->
                </xsl:if>}}</xsl:element> <!--old column-->
            <xsl:if test="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@АдрРФТекст">
                <xsl:element name="addressRFText">{"ADDRESS":"<xsl:value-of
                        select="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@АдрРФТекст"/>","OKTMO":"<xsl:value-of
                        select="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@ОКТМО"/>","SIGN_RF":"<xsl:value-of
                        select="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/@ПрТипАдрРФ"/>"}</xsl:element> <!--old column-->
            </xsl:if>
        </xsl:if>

        <xsl:if test="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']">
            <xsl:element name="addressNotRF">{"OKSM":"<xsl:value-of
                    select="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@ОКСМ"/>","COUNTRY":"<xsl:value-of
                    select="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@НаимСтраны"/>","ADDRESS":"<xsl:value-of
                    select="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖИн']/@АдрТекст"/>"}</xsl:element> <!--old column-->
        </xsl:if>

        <xsl:if test="*[local-name()='ДатаСмертДок'] | *[local-name()='ДатаСмертКаленд']">
            <xsl:element name="deathDate">{"CALEND":"<xsl:value-of
                    select="*[local-name()='ДатаСмертКаленд']"/>","DOC":{<xsl:if
                    test="*[local-name()='ДатаСмертДок']">"Y":"<xsl:value-of
                        select="*[local-name()='ДатаСмертДок']/*[local-name()='Год']"/>","M":"<xsl:value-of
                        select="*[local-name()='ДатаСмертДок']/*[local-name()='Месяц']"/>","D":"<xsl:value-of
                    select="*[local-name()='ДатаСмертДок']/*[local-name()='День']"/>"</xsl:if>}}</xsl:element> <!--old column-->
        </xsl:if>

        <xsl:if test="*[local-name()='ВремяСмерт']">
            <xsl:element name="deathTime"> <!--old column-->
                <xsl:value-of select="*[local-name()='ВремяСмерт']"/>
            </xsl:element>
        </xsl:if>

        <xsl:call-template name="place"> <!--old column-->
            <xsl:with-param name="use" select="'death'"/>
            <xsl:with-param name="current" select="*[local-name()='МестоСмерт']"/>
        </xsl:call-template>

        <xsl:element name="aboutPerson">{"NATIONALITY":"<xsl:value-of
                select="*[local-name()='Национальность']/text()"/>","MARITAL_STATUS":"<xsl:value-of
                select="*[local-name()='СемПолож']/text()"/>","EDUCATION":"<xsl:value-of
                select="*[local-name()='Образование']/text()"/>","EMPLOYMENT":"<xsl:value-of
                select="*[local-name()='Занятость']/text()"/>"}</xsl:element> <!--new column-->

        <xsl:apply-templates select="*[local-name()='УдЛичнФЛ']"/>

        <xsl:element name="aboutDeath">{"DEATH_PLACE":"<xsl:value-of
                select="*[local-name()='МестоНастСмерт']/text()"/>","DEATH_CIRCUMSTANCES":"<xsl:value-of
                select="*[local-name()='ОбстоятСмерт']/text()"/>","TRAFFIC_ACCIDENT":"<xsl:value-of
                select="*[local-name()='СрокСмертДТП']/text()"/>","PERSON_DETERM":"<xsl:value-of
                select="*[local-name()='ЛицоПричСмерт']/text()"/>","DEATH_CAUSE_BASIS":"<xsl:value-of
                select="*[local-name()='ОснПричСмерт']/text()"/>"}</xsl:element> <!--new column-->

        <xsl:apply-templates select="*[local-name()='СведУмерРеб']"/>
        <xsl:apply-templates select="*[local-name()='СведВрач']"/>

        <xsl:if test="*[local-name()='СведПричСмерт']">
            <deathCause>{"deathCause":[<xsl:apply-templates select="*[local-name()='СведПричСмерт']"/>]}</deathCause>  <!--old column-->
        </xsl:if>

    </xsl:template>

    <xsl:template match="*[local-name()='СведДокСмерт']">
        <deathDocument>{"DOCUMENT_CODE":"<xsl:value-of select="@КодДок"/>","DOCUMENT_NAME":"<xsl:value-of
                select="@НаимДок"/>","SERIES_NUMBER":"<xsl:value-of
            select="@СерНомДок"/>","DOCUMENT_DATE":"<xsl:value-of
                select="@ДатаДок"/>"}</deathDocument>  <!--old column-->
        <xsl:if test="*[local-name()='НаимОрг']">
            <deathDocIssuer>{"ORG_NAME":"<xsl:value-of select="translate(*[local-name()='НаимОрг'],'\&quot;', '')"/>","MARK":"<xsl:value-of select="normalize-space(*[local-name()='СведПометка']/*[local-name()='Пометка'])"/>"}</deathDocIssuer>  <!--old column-->
        </xsl:if>
        <xsl:if test="*[local-name()='ФИОИП']">
            <deathDocFIOIP>{"FIO":{<xsl:call-template name="fio">
                    <xsl:with-param name="current" select="*[local-name()='ФИОИП']"/>
                </xsl:call-template>},"MARK":"<xsl:value-of select="normalize-space(*[local-name()='СведПометка']/*[local-name()='Пометка'])"/>"}</deathDocFIOIP></xsl:if>  <!--old column-->
        <xsl:if test="*[local-name()='ФИОФЛ']">
            <deathDocFIOFL>{"FIO":{<xsl:call-template name="fio">
                    <xsl:with-param name="current" select="*[local-name()='ФИОФЛ']"/>
                </xsl:call-template>},"MARK":"<xsl:value-of select="normalize-space(*[local-name()='СведПометка']/*[local-name()='Пометка'])"/>"}</deathDocFIOFL></xsl:if>  <!--old column-->
    </xsl:template>

    <xsl:template name="fio"> <!--check later todo -->
        <xsl:param name="current"/>"SURNAME":"<xsl:value-of select="$current/*[local-name()='Фамилия']"/>","FIRSTNAME":"<xsl:value-of
            select="$current/*[local-name()='Имя']"/>","MIDDLENAME":"<xsl:value-of select="$current/*[local-name()='Отчество']"/>"</xsl:template>

    <xsl:template match="*[local-name()='УдЛичнФЛ']">
        <xsl:element name="identityDocInfo">{"DOCUMENT_CODE":"<xsl:value-of
                select="@КодВидДок"/>","DOCUMENT_NAME":"<xsl:value-of
            select="@НаимДок"/>","SERIES_NUMBER":"<xsl:value-of
                select="@СерНомДок"/>","ISSUER_DATE":"<xsl:value-of
            select="@ДатаДок"/>","ISSUER":"<xsl:value-of
                select="@ВыдДок"/>"}</xsl:element>  <!--old column-->
    </xsl:template>

    <xsl:template match="*[local-name()='СведУмерРеб']">
        <xsl:element name="childDeath">{"MASS":"<xsl:value-of
                select="*[local-name()='Масса']"/>","NUMBER":"<xsl:value-of
                select="*[local-name()='Счет']"/>"}</xsl:element>
    </xsl:template>  <!--new column-->

    <xsl:template match="*[local-name()='СведВрач']">
        <xsl:element name="doctor">{"SURNAME":"<xsl:value-of
                select="*[local-name()='ФИОВрач']/*[local-name()='Фамилия']"/>","FIRSTNAME":"<xsl:value-of
                select="*[local-name()='ФИОВрач']/*[local-name()='Имя']"/>","MIDDLENAME":"<xsl:value-of
                select="*[local-name()='ФИОВрач']/*[local-name()='Отчество']"/>","POST":"<xsl:value-of
                select="@СведДолжн"/>"}</xsl:element>
    </xsl:template>  <!--new column-->

    <xsl:template name="addressKLADR" match="*[local-name()='АдрКЛАДР']">"INDEX":"<xsl:value-of
            select="@Индекс"/>","REGION_CODE":"<xsl:value-of
            select="@КодРегион"/>","REGION_NAME":"<xsl:value-of
            select="@НаимРегион"/>","DISTRICT":"<xsl:value-of
            select="@Район"/>","CITY":"<xsl:value-of
            select="@Город"/>","LOCALITY":"<xsl:value-of
            select="@НаселПункт"/>","STREET":"<xsl:value-of
            select="@Улица"/>","HOUSE":"<xsl:value-of
            select="@Дом"/>","HOUSING":"<xsl:value-of
            select="@Корпус"/>","FLAT":"<xsl:value-of
            select="@Кварт"/>"</xsl:template>

    <xsl:template name="addressFIAS" match="*[local-name()='АдрМЖ']/*[local-name()='АдрМЖРФ']/*[local-name()='АдрФИАС']">"ID":"<xsl:value-of
            select="@ИдНом"/>","INDEX":"<xsl:value-of
            select="@Индекс"/>","REGION":"<xsl:value-of
            select="*[local-name()='Регион']"/>","MUNIC_DISTRICT":{<xsl:apply-templates
            select="*[local-name()='МуниципРайон']"/>},"SETTLEMENT":{<xsl:apply-templates
            select="*[local-name()='ГородСелПоселен']"/>},"LOCALITY":{<xsl:apply-templates
            select="*[local-name()='НаселенПункт']"/>},"PLAN_STRUCT":{<xsl:apply-templates
            select="*[local-name()='ЭлПланСтруктур']"/>},"ROAD_NET":{<xsl:apply-templates
            select="*[local-name()='ЭлУлДорСети']"/>},"LAND_PLOT":"<xsl:value-of
            select="*[local-name()='ЗемелУчасток']"/>","BUILDING":[<xsl:apply-templates
            select=".//*[local-name()='Здание']"/>],"BUILDING_ROOM":<xsl:apply-templates
            select="*[local-name()='ПомещЗдания']"/>,"FLAT_ROOM":<xsl:apply-templates
            select="*[local-name()='ПомещКвартиры']"/></xsl:template>

    <xsl:template match="*[local-name()='МуниципРайон']|*[local-name()='ГородСелПоселен']">"VIEW_CODE":"<xsl:value-of
            select="@ВидКод"/>","NAME":"<xsl:value-of
            select="@Наим"/>"</xsl:template>

    <xsl:template match="*[local-name()='НаселенПункт']">"VIEW":"<xsl:value-of
            select="@Вид"/>","NAME":"<xsl:value-of
            select="@Наим"/>"</xsl:template>

    <xsl:template match="*[local-name()='ЭлПланСтруктур'] | *[local-name()='ЭлУлДорСети']">"TYPE":"<xsl:value-of
            select="@Тип"/>","NAME":"<xsl:value-of
            select="@Наим"/>"</xsl:template>

    <xsl:template match="*[local-name()='Здание'] | *[local-name()='ПомещЗдания'] | *[local-name()='ПомещКвартиры']">{"TYPE":"<xsl:value-of select="@Тип"/>","NUMBER":"<xsl:value-of select="@Номер"/>"}<xsl:if test="position() != last()">,</xsl:if></xsl:template>

    <xsl:template name="place">
        <xsl:param name="use"/>
        <xsl:param name="current"/>
        <xsl:variable name="elementName" select="concat($use, 'Place')"/>
        <xsl:if test="$current"><xsl:element name="{$elementName}">{"TEXT":"<xsl:value-of
                select="$current/@МестоТекст"/>","CODE":"<xsl:value-of
                select="$current/@КодСтраны"/>","NAME":"<xsl:value-of
                select="$current/@НаимСтраны"/>","REGION":"<xsl:value-of
                select="$current/@Регион"/>","SUBJECT":"<xsl:value-of
                select="$current/@НаимСубъект"/>","DISTRICT":"<xsl:value-of
                select="$current/@Район"/>","CITY":"<xsl:value-of
                select="$current/@Город"/>","LOCALITY":"<xsl:value-of
                select="$current/@НаселПункт"/>","OKTMO":"<xsl:value-of
                select="$current/@ОКТМО"/>","RF_BELONG":"<xsl:value-of
                select="$current/@ПризнМесто"/>"}</xsl:element></xsl:if>
    </xsl:template>

    <xsl:template match="*[local-name()='СведПричСмерт']">{"ILLNESS":"<xsl:value-of
            select="@Заболевание"/>","MKB":{<xsl:if test="*[local-name()='СвПричСмертМКБ']">
        <xsl:apply-templates select="*[local-name()='СвПричСмертМКБ']"/>
    </xsl:if>},"NOT_DICT":{<xsl:if test="*[local-name()='СвПричСмертПроизв']">
        <xsl:apply-templates select="*[local-name()='СвПричСмертПроизв']"/>
    </xsl:if>}}<xsl:if test="position() != last()">,</xsl:if></xsl:template>

    <xsl:template match="*[local-name()='СвПричСмертМКБ']">"CODE":"<xsl:value-of select="@КодПричСмерт"/>","CAUSE":"<xsl:value-of select="@ПричСмерт"/>"</xsl:template>
    <xsl:template match="*[local-name()='СвПричСмертПроизв']">"CODE":"<xsl:value-of select="@КодПричСмертПроизв"/>","CAUSE":"<xsl:value-of select="@ПричСмертПроизв"/>"</xsl:template>

</xsl:stylesheet>