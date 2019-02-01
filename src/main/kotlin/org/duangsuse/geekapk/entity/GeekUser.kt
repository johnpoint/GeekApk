package org.duangsuse.geekapk.entity

import org.duangsuse.geekapk.AppId
import org.duangsuse.geekapk.UserId
import org.duangsuse.geekapk.annotations.*
import org.duangsuse.geekapk.helpers.loopFor
import org.duangsuse.geekapk.helpers.times
import org.jetbrains.annotations.Nls
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Size
import kotlin.math.roundToInt

@StandaloneEntity("user")
@Table(name = "users")
@Entity
data class GeekUser (
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val uid: UserId = 0,
  @Version var version: Long = 0,

  @Size(message = "username must be greater than 1 char and smaller than 21 char count", min = 2, max = 20)
  @Nls var username: String = "(generated)",
  @Size(message = "nickname too long (~ ..90)", min = 0, max = 90) /* in case for unicode composition */
  @Nls var nickname: String = "Geek",

  @Size(message = "avatar url too large (~ ..600)", min = 0, max = 600)
  var avatarUrl: String = "",

  @LinkTo("app", Relation.HAS_ONE)
  var metaApp: AppId? = null,

  @Size(message = "bio too long (~ ..6k)", min = 0, max = 6000)
  @Markdown @Nls var bio: String = "_No bio provided QAQ_",

  var flags: Int = FLAG_NONE,

  @UserPrivate
  var sharedHash: String = makeSharedHash(20), /* static server-computed size */
  @UserPrivate /* should be set to another value in controller level */
  @Size(min = 256 / 4, max = 256 / 4) /* SHA-256 hex representation hash */
  var hash: String = "68e656b251e67e8358bef8483ab0d51c6619f3e7a1a9f0e75838d41ff368f728",

  val createdAt: Date = Date(),

  /* Weak field */
  @CounterFor("user")
  var followersCount: Int = 0
): Serializable {
  companion object {
    val KEY: String = System.getProperty("geekapk.key", "dolphins")
    val BOT_UID: UserId = System.getProperty("geekapk.bot", "0").toInt()
    val BOT_OVERRIDE: AppId? = System.getProperty("geekapk.botMessageAppOverride").toLongOrNull()

    const val FLAG_NONE = 0b0
    const val FLAG_READONLY = 0b1
    const val FLAG_BANNED = 0b10
    const val FLAG_ADMIN = 0b11
    @Suppress("SpellCheckingInspection")
    private val SHARED_HASH_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".toCharArray()

    /** Generate random [A-Za-z0-9] strings */
    @JvmStatic
    fun makeSharedHash(length: Int): String {
      val ret = StringBuilder()

      val appendRandomChar = fun (_: Int) {
        ret.append(SHARED_HASH_CHARS[(Math.random() * SHARED_HASH_CHARS.lastIndex).roundToInt()])
      }

      let { appendRandomChar loopFor length . times() }

      return ret.toString()
    }

    private val makeNewShared64CharKey = { makeSharedHash(64) }

    private fun makeUserProto(hash: () -> String = makeNewShared64CharKey) = fun (name: String): GeekUser {
      return GeekUser(username = name, hash = hash())
    }

    val makeUser = makeUserProto()
  }
}
