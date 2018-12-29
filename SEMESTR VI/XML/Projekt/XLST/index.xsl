<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:template match="/">
        <html>
            <body>
                <h2>Cennik samochodów</h2>

                <table border="1">

                    <tr bgcolor="#e1e1e1">
                        <th>Marka</th>
                        <th>Model</th>
                        <th>Rocznik</th>
                        <th>Nr. Katalogowy</th>
                        <th>Cena</th>
                    </tr>

                    <xsl:for-each select="cennik/samochrod">
                        <xsl:sort select="rocznik" order="descending"/>
                        <tr>
                            <td><xsl:value-of select="marka"/></td>
                            <td><xsl:value-of select="model"/></td>
                            <td><xsl:value-of select="rocznik"/></td>
                            <td><xsl:value-of select="numer_katalogowy"/></td>
                            <xsl:choose>
                                <xsl:when test="cena > 50000">
                                    <td bgcolor="#fc8383">
                                        <xsl:value-of select="cena"/>
                                    </td>
                                </xsl:when>
                                <xsl:when test="cena > 20000">
                                    <td bgcolor="#83eefc">
                                        <xsl:value-of select="cena"/>
                                    </td>
                                </xsl:when>
                                <xsl:otherwise>
                                    <td bgcolor="#85fc83">
                                        <xsl:value-of select="cena"/>
                                    </td>
                                </xsl:otherwise>
                            </xsl:choose>
                        </tr>
                    </xsl:for-each>

                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>