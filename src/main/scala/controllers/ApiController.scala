package controllers

/**
 * @author VKoulakov
 * @since 22.03.2016 18:09
 */

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport

class ApiController extends ControllerBase with JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  before() {
    contentType = formats("json")
  }
}
