<?xml version="1.0" encoding="UTF-8" ?>

<!--
 -*- mode: Fundamental; tab-width: 4; -*-
 ex:ts=4

 XSLT stylesheet that converts a Checkstyle XML report to HTML.

 $Id: index.xslt,v 1.1 2003/04/26 17:40:13 znerd Exp $
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<xsl:output
	method="xml"
	indent="no"
	doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN"
	doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" />

	<xsl:template match="checkstyle">
		<html>
			<head>
				<title>Checkstyle report</title>
				<link rel="stylesheet" type="text/css" href="stylesheet.css" />
			</head>
			<body>
				<h1>Checkstyle report</h1>
				<h2>Summary</h2>
				<table class="summary">
					<tr>
						<th>Files checked:</th>
						<td>
							<xsl:value-of select="count(file)" />
							<xsl:text> (</xsl:text>
							<xsl:value-of select="count(file[error])" />
							<xsl:text> with errors)</xsl:text>
						</td>
					</tr>
					<tr>
						<th>Errors:</th>
						<td>
							<xsl:value-of select="count(file/error)" />
						</td>
					</tr>
				</table>

				<xsl:choose>
					<xsl:when test="file[error]">
						<h2>Checkstyle details</h2>
						<xsl:apply-templates select="file[error]" />
					</xsl:when>
					<xsl:otherwise>
						<p />
						<em>No errors!</em>
					</xsl:otherwise>
				</xsl:choose>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="file">
		<h3>
			<xsl:value-of select="@name" />
		</h3>

		<table class="file">
			<tr>
				<th>Line</th>
				<th>Column</th>
				<th>Message</th>
			</tr>
			<xsl:apply-templates select="error" />
		</table>
	</xsl:template>

	<xsl:template match="error">
		<tr>
			<td>
				<xsl:value-of select="@line" />
			</td>
			<td>
				<xsl:value-of select="@column" />
			</td>
			<td>
				<xsl:value-of select="@message" />
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
