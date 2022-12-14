package com.github.encrypt

import com.intellij.codeInsight.editorActions.TextBlockTransferable
import com.intellij.notification.{Notification, NotificationType, Notifications}
import com.intellij.openapi.actionSystem.{AnAction, AnActionEvent}
import com.intellij.openapi.ide.CopyPasteManager

import java.util.Collections

object Constant {

  val groupId = "com.github.encrypt"

  val title = "Encrypt"

  def copyToClipBoard(format: String, res: (String, String)): Unit = {
    var notification: Notification = null
    if (res._2.length > 64) {
      notification = new Notification(
        Constant.groupId,
        Constant.title,
        format.format(res._1, res._2.substring(0, 65) + "..."),
        NotificationType.INFORMATION
      )
    } else {
      notification = new Notification(Constant.groupId, Constant.title, res._2, NotificationType.INFORMATION)
    }
    notification.addAction(new AnAction("Copy to ClipBoard") {
      override def actionPerformed(e: AnActionEvent): Unit = {
        CopyPasteManager.getInstance.setContents(new TextBlockTransferable(res._2, Collections.emptyList, null))
        Notifications.Bus.notify(
          new Notification(Constant.groupId, Constant.title, "Copied", NotificationType.INFORMATION)
        )
        notification.expire()
      }
    })
    Notifications.Bus.notify(notification)
  }
}
