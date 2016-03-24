package controllers

import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, JacksonSwaggerBase, Swagger}

/**
 * @author VKoulakov
 * @since 24.03.2016 12:18
 */
class ApiSwagger extends Swagger(Swagger.SpecVersion, "1.0.0", ShopApiInfo) {

}

object ShopApiInfo extends ApiInfo(
  "The EShop API",
  "Docs for the EShop API",
  "http://localhost",
  "apiteam@wivern.com",
  "Apache",
  "http://"
)

class ResourcesApp(implicit val swagger: Swagger) extends ScalatraServlet with JacksonSwaggerBase
