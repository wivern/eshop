import javax.servlet.ServletContext

import com.mchange.v2.c3p0.ComboPooledDataSource
import controllers._
import org.scalatra.LifeCycle
import org.slf4j.LoggerFactory
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter
import util.DatabaseInititalizer

/**
 * @author VKoulakov
 * @since 14.03.2016 17:28
 */
class ScalatraBootstrap extends LifeCycle with DatabaseInititalizer {
  val logger = LoggerFactory.getLogger(getClass)

  val cpds = new ComboPooledDataSource

  implicit val swagger = new ApiSwagger

  override def init(context: ServletContext) {
    context mount(new AdminController, "/admin")
    context mount(new ApiController, "/api/v1")
    context mount(new ProductsController, "/products")
    context mount(new ResourcesApp, "/api-docs")
    context mount(new IndexController, "/")
    SessionFactory.concreteFactory = Some(() => connection)

    def connection = {
      logger.debug("Creating connection with c3po connection pool")
      Session.create(cpds.getConnection, new H2Adapter)
    }
    initDb()
  }

  private def closeDbConnection() {
    logger.info("Closing c3po connection pool")
  }

  override def destroy(context: ServletContext) {
    super.destroy(context)
    closeDbConnection()
  }
}
