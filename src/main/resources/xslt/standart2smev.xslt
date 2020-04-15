<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet xmlns:ns1="urn://x-artefacts-zags-fataloiv/root/112-41/4.0.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!-- IMPORTANT: add all namespace you use in xml to the <xsl:stylesheet>
         like xmlns:prefix="namespace_uri" instead of xmlns:ns1 and xmlns:ns2-->
    <xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">

        <xsl:if test="//*[local-name()='FATALOIVResponse']">
            <ns1:FATALOIVResponse>
                <xsl:attribute name="ИдСвед">
                    <xsl:value-of select="//*[local-name()='IdSved']"/>
                </xsl:attribute>
                <xsl:attribute name="КодОбр">
                    <xsl:value-of select="//*[local-name()='CodeObr']"/>
                </xsl:attribute>
            </ns1:FATALOIVResponse>
        </xsl:if>

        <xsl:if test="//*[local-name()='emptyData']">
            <NoData>
                <xsl:apply-templates/>
            </NoData>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>