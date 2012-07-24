<?xml version="1.0" encoding="UTF-8" ?>

<!--
 -*- mode: Fundamental; tab-width: 4; -*-
 ex:ts=4

 XSLT stylesheet that converts a FAQ index to HTML.

 $Id: faqindex_to_html.xslt,v 1.2 2003/02/07 09:38:43 znerd Exp $
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
	method="xml"
	indent="no"
	doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

	<xsl:template match="faqindex">
		<html>
			<head>
				<title>xmlenc -- Frequently asked questions</title>
				<link rel="stylesheet" type="text/css" href="stylesheet.css" />
			</head>
			<body>
				<h1>Frequently asked questions</h1>
				<p>The questions are divided over the following categories:</p>
				<ul>
					<xsl:apply-templates select="faq" />
				</ul>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="faq">
		<li>
			<a href="{@name}.html">
				<xsl:value-of select="@name" />
			</a>
		</li>
	</xsl:template>
</xsl:stylesheet>
