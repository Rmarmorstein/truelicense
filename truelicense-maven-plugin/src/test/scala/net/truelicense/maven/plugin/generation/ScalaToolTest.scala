/*
 * Copyright (C) 2005-2017 Schlichtherle IT Services.
 * All rights reserved. Use is subject to license terms.
 */

package net.truelicense.maven.plugin.generation

import org.junit.runner.RunWith
import org.scalatest.Matchers._
import org.scalatest.WordSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.PropertyChecks._

/**
 * @author Christian Schlichtherle
 */
@RunWith(classOf[JUnitRunner])
class ScalaToolTest extends WordSpec /*with Matchers*/ {

  "A ScalaTool " should {
    "generate an obfuscated string " which {
      "matches the pattern" in {
        forAll(Table(
          "string",
          "",
          "Hello world!"
        )) { string =>
          new ScalaTool obfuscatedString string should (fullyMatch regex "new net\\.truelicense\\.obfuscate\\.ObfuscatedString\\(Array\\[Long\\]\\([^)]*\\)\\) /\\*.*\\*/")
        }
      }
    }
  }
}
