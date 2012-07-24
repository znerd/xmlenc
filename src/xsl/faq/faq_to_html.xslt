<?xml version="1.0" encoding="UTF-8" ?>

<!--
 -*- mode: Fundamental; tab-width: 4; -*-
 ex:ts=4

 XSLT stylesheet that converts a FAQ category XML file to HTML.

 $Id: faq_to_html.xslt,v 1.1 2003/02/06 14:47:17 znerd Exp $
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
	method="xml"
	indent="no"
	doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

	<xsl:template match="faq">
		<html>
			<head>
				<title>xmlenc -- Frequently asked questions</title>
				<link rel="stylesheet" type="text/css" href="stylesheet.css" />
			</head>
			<body>
				<h1>Frequently asked questions</h1>
				<xsl:apply-templates select="qna" />
			</body>
		</html>
	</xsl:template>

	<xsl:template match="qna">
		<p>
			<xsl:apply-templates select="question" />
			<xsl:apply-templates select="answer" />
		</p>
	</xsl:template>

	<xsl:template match="question">
		<strong>
			<xsl:apply-templates />
		</strong>
	</xsl:template>

	<xsl:template match="answer">
		<br />
		<xsl:apply-templates />
	</xsl:template>
</xsl:stylesheet>
