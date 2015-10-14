package bad.robot

import bad.robot.http.configuration.{Username => SimpleHttpUsername}
import bad.robot.http.configuration.{Password => SimpleHttpPassword}
import bad.robot.radiate.config.{Password, Username}

package object http {

  implicit def toUsername(username: Username): SimpleHttpUsername = {
    SimpleHttpUsername.username(username.value)
  }

  implicit def toPassword(password: Password): SimpleHttpPassword = {
    SimpleHttpPassword.password(password.value)
  }
}
