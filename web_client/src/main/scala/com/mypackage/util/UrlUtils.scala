package com.mypackage.util

import scala.scalajs.js

object UrlUtils {

  /**
    * Extracts the base url form the address bar.
    * Returns only the part with the protocol, the domain and the port
    *
    */
  def getBaseUrl: String = {
    val currentPageUrl: String = js.Dynamic.global.window.location.href.asInstanceOf[String]
    val protocolSlashIndex = currentPageUrl.indexOf('/')
    val baseUrlSlashIndex = currentPageUrl.indexOf('/', protocolSlashIndex + 2)


    currentPageUrl.substring(0, baseUrlSlashIndex)
  }

  def navigate(queryString: String) = {
    js.Dynamic.global.window.location.href = getBaseUrl + queryString
  }
}
