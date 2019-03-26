package infrastructure

import slick.codegen.SourceCodeGenerator
import slick.model.Model

/**
  * Slick source code generator which uses joda types instead of sql.Timestamp
  */
class JodaAwareSourceCodeGenerator(model: Model) extends SourceCodeGenerator(model) {

  // This inserts the imports in any table, even if the table doesn't use DateTime, can be optimised.
  override def codePerTable: Map[String, String] = super.codePerTable.mapValues { v =>
    """import com.github.tototoshi.slick.MySQLJodaSupport._
      |import org.joda.time.DateTime
      |""".stripMargin + v
  }

  override def Table = new Table(_) {
    override def Column = new Column(_) {

      override def rawType = model.tpe match {
        case "java.sql.Timestamp" => "DateTime"
        case _ => super.rawType
      }
    }
  }
}
