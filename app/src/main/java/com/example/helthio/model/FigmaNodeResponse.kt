package com.example.helthio.model

import com.google.gson.annotations.SerializedName

data class FigmaFileResponse(
    @SerializedName("name") val name: String,
    @SerializedName("lastModified") val lastModified: String,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String,
    @SerializedName("version") val version: String,
    @SerializedName("role") val role: String,
    @SerializedName("editorType") val editorType: String,
    @SerializedName("linkAccess") val linkAccess: String,
    @SerializedName("nodes") val nodes: Map<String, FigmaNode>
)

data class FigmaNode(
    @SerializedName("document") val document: Document,
    @SerializedName("components") val components: Map<String, Any>?,
    @SerializedName("componentSets") val componentSets: Map<String, Any>?,
    @SerializedName("schemaVersion") val schemaVersion: Int,
    @SerializedName("styles") val styles: Map<String, Style>
)

data class Document(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("isFixed") val isFixed: Boolean,
    @SerializedName("scrollBehavior") val scrollBehavior: String,
    @SerializedName("blendMode") val blendMode: String,
    @SerializedName("fills") val fills: List<Fill>,
    @SerializedName("strokes") val strokes: List<Any>,
    @SerializedName("strokeWeight") val strokeWeight: Float,
    @SerializedName("strokeAlign") val strokeAlign: String,
    @SerializedName("styles") val styles: Map<String, String>,
    @SerializedName("absoluteBoundingBox") val absoluteBoundingBox: BoundingBox,
    @SerializedName("absoluteRenderBounds") val absoluteRenderBounds: BoundingBox,
    @SerializedName("constraints") val constraints: Constraints,
    @SerializedName("characters") val characters: String,
    @SerializedName("style") val style: TextStyle,
    @SerializedName("layoutVersion") val layoutVersion: Int,
    @SerializedName("characterStyleOverrides") val characterStyleOverrides: List<Any>,
    @SerializedName("styleOverrideTable") val styleOverrideTable: Map<String, Any>,
    @SerializedName("lineTypes") val lineTypes: List<String>,
    @SerializedName("lineIndentations") val lineIndentations: List<Int>,
    @SerializedName("effects") val effects: List<Any>,
    @SerializedName("interactions") val interactions: List<Any>
)

data class Fill(
    @SerializedName("blendMode") val blendMode: String,
    @SerializedName("type") val type: String,
    @SerializedName("color") val color: Color
)

data class Color(
    @SerializedName("r") val r: Float,
    @SerializedName("g") val g: Float,
    @SerializedName("b") val b: Float,
    @SerializedName("a") val a: Float
)

data class BoundingBox(
    @SerializedName("x") val x: Float,
    @SerializedName("y") val y: Float,
    @SerializedName("width") val width: Float,
    @SerializedName("height") val height: Float
)

data class Constraints(
    @SerializedName("vertical") val vertical: String,
    @SerializedName("horizontal") val horizontal: String
)

data class TextStyle(
    @SerializedName("fontFamily") val fontFamily: String,
    @SerializedName("fontPostScriptName") val fontPostScriptName: String,
    @SerializedName("fontWeight") val fontWeight: Int,
    @SerializedName("textAutoResize") val textAutoResize: String,
    @SerializedName("fontSize") val fontSize: Float,
    @SerializedName("textAlignHorizontal") val textAlignHorizontal: String,
    @SerializedName("textAlignVertical") val textAlignVertical: String,
    @SerializedName("letterSpacing") val letterSpacing: Float,
    @SerializedName("lineHeightPx") val lineHeightPx: Float,
    @SerializedName("lineHeightPercent") val lineHeightPercent: Float,
    @SerializedName("lineHeightUnit") val lineHeightUnit: String
)

data class Style(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("styleType") val styleType: String,
    @SerializedName("remote") val remote: Boolean,
    @SerializedName("description") val description: String
)


