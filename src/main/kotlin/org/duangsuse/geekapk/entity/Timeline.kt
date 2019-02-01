package org.duangsuse.geekapk.entity

import org.duangsuse.geekapk.TimelineId
import org.duangsuse.geekapk.UserId
import org.duangsuse.geekapk.annotations.Appendage
import org.duangsuse.geekapk.annotations.LinkTo
import org.duangsuse.geekapk.annotations.Relation
import java.io.Serializable
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Appendage("user")
@Entity
data class Timeline (
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: TimelineId = 0,

  @LinkTo("user", rel = Relation.BELONGING)
  val owner: UserId,

  val type: Int = JUST_FORWARD_WIDEN_CID,
  val data: Int = 0,

  val createdAt: Date = Date()
): Serializable {
  companion object {
    const val NEW_FOLLOW = 0
    const val NEW_APP = 1
    const val NEW_APP_UPDATE = 2
    const val NEW_REPLY = 3
    const val DELETED_APP = 4
    const val DELETED_APP_UPDATE = 5
    const val DELETED_REPLY = 6
    const val UPDATED_BIO = 7
    const val UPDATED_NAME = 8
    const val UPDATED_USERNAME = 9
    const val COLLABORATE_APP = 10
    const val NEW_STAR = 11
    const val JUST_FORWARD_WIDEN_CID = 1000
  }
}
