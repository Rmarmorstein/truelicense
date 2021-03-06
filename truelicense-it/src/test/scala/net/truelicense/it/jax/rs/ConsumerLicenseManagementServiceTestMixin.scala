package net.truelicense.it.jax.rs

import net.truelicense.api.License
import net.truelicense.it.core.TestContext
import net.truelicense.jax.rs.ConsumerLicenseManagementService
import net.truelicense.spi.io.MemoryStore
import scala.language.existentials

trait ConsumerLicenseManagementServiceTestMixin {
  this: TestContext[_] =>

  lazy val managementService: ConsumerLicenseManagementService = new ConsumerLicenseManagementService(consumerManager())

  protected lazy val (cachedLicenseClass, cachedLicenseBean, cachedLicenseKey): (Class[_ <: License], License, Array[Byte]) = {
    val store = new MemoryStore
    val bean = license
    val generator = vendorManager generateKeyFrom bean saveTo store
    (bean.getClass, generator.license, store.data)
  }
}
