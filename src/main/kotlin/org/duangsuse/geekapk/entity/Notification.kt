package org.duangsuse.geekapk.entity

import org.duangsuse.geekapk.NotificationId
import org.duangsuse.geekapk.UserId
import org.duangsuse.geekapk.annotation.Appendage
import org.duangsuse.geekapk.annotation.LinkTo
import org.duangsuse.geekapk.annotation.Relation
import java.io.Serializable
import java.util.*
import javax.persistence.*

/**
 * GeekApk user private notification box
 */
@Appendage("user")
@Entity
@Table(name = "notifications")
data class Notification (
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: NotificationId = 0,

  /**
   * Notification owner
   */
  @LinkTo("user", rel = Relation.BELONGING)
  val owner: UserId,

  /**
   * Notification type
   */
  val type: Int = SYSTEM,
  /**
   * Paired data
   */
  val data: Int = 0,

  /**
   * Created date timestamp
   */
  @Temporal(TemporalType.TIMESTAMP)
  val createdAt: Date = Date(),

  /**
   * Mark as read
   */
  var pass: Boolean = false
): Serializable {
  companion object {
    const val NEW_REPLY = 0
    const val REPLY_DELETED = 1

    const val NEW_APP = 2
    const val APP_DELETED = 3
    const val APP_MOVED = 4

    const val SHASH_RENEWED = 5
    const val REHASH = 6

    const val READWRITE = 100
    const val READONLY = 101
    const val BANNED = 102
    const val UNBANNED = 103
    const val SET_USER = 104
    const val PROMOTED = 105

    const val SYSTEM = 1000
  }
}
