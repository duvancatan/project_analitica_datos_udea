#!/usr/bin/env amm
// pipeline.sc - ejemplo de pipeline simple de análisis de datos (CSV)
// Ejecutar con Ammonite: `amm pipeline.sc` o con Scala-CLI si lo prefieres.

import scala.io.Source
import java.io.PrintWriter

// Lectura CSV simple: la primera línea son cabeceras
def readCSV(path: String): Seq[Map[String,String]] = {
  val src = Source.fromFile(path)
  try {
    val lines = src.getLines().toList
    if (lines.isEmpty) return Seq.empty
    val headers = lines.head.split(",").map(_.trim)
    lines.tail.map { l =>
      val cols = l.split(",", -1).map(_.trim)
      headers.zipAll(cols, "", "").toMap
    }
  } finally src.close()
}

// Limpieza básica: quitar filas con valores vacíos en columnas clave
def cleanData(rows: Seq[Map[String,String]], requiredCols: Seq[String]): Seq[Map[String,String]] = {
  rows.filter { row =>
    requiredCols.forall(c => row.get(c).exists(_.nonEmpty))
  }
}

// Transformación: convertir columnas numéricas
def toDouble(value: String): Option[Double] = try { Some(value.toDouble) } catch { case _: Throwable => None }

// Agregación: media por categoría
def aggregateMeanByCategory(rows: Seq[Map[String,String]], categoryCol: String, valueCol: String): Seq[(String, Double, Int)] = {
  val grouped = rows.flatMap { row =>
    for {
      cat <- row.get(categoryCol)
      vstr <- row.get(valueCol)
      v <- toDouble(vstr)
    } yield (cat, v)
  }.groupBy(_._1).mapValues(_.map(_._2))

  grouped.toSeq.map { case (cat, vals) => (cat, vals.sum / vals.size, vals.size) }
}

// Guardar resultado CSV
def saveAggregated(path: String, data: Seq[(String, Double, Int)]): Unit = {
  val pw = new PrintWriter(path)
  try {
    pw.println("category,mean,count")
    data.foreach { case (cat, mean, count) => pw.println(s"$cat,$mean,$count") }
  } finally pw.close()
}

// --- Ejemplo de flujo completo ---
// Si no existe `data.csv`, generamos un CSV de ejemplo
val samplePath = "data.csv"
val sampleData = Seq(
  "category,value",
  "A,10",
  "B,20",
  "A,15",
  "B,25",
  "C,5",
  "A,"
)
import java.nio.file.{Files, Paths}
if (!Files.exists(Paths.get(samplePath))) {
  val pw = new PrintWriter(samplePath)
  try sampleData.foreach(pw.println) finally pw.close()
  println(s"Archivo de ejemplo creado: $samplePath")
}

// Ejecutar pipeline
val rows = readCSV(samplePath)
println(s"Leídas ${rows.length} filas")

val cleaned = cleanData(rows, Seq("category", "value"))
println(s"Tras limpieza: ${cleaned.length} filas válidas")

val aggregated = aggregateMeanByCategory(cleaned, "category", "value")
println("Resultados (category, mean, count):")
aggregated.foreach(println)

val outPath = "aggregated.csv"
saveAggregated(outPath, aggregated)
println(s"Resultado guardado en: $outPath")

// Fin del script
println("Pipeline completado.")
