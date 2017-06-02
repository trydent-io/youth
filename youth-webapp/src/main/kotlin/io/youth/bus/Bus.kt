package io.youth.bus

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.PublishSubject.create
import java.lang.System.currentTimeMillis

sealed class Bullet(private val serialID: Long)

interface Bus<B: Bullet> {
  fun emit(command: B)
  fun observed(): Observable<B>
}

open class Command: Bullet(currentTimeMillis())
open class Event: Bullet(currentTimeMillis())

data class SendSomething(val field: String): Command()

class CommandBus(private val commands: PublishSubject<Command> = create()): Bus<Command> {
  override fun emit(command: Command) {
    commands.onNext(command)
  }

  override fun observed(): Observable<Command> = commands
}

fun c() {
  val commands = CommandBus()
  commands.observed()
    .filter { it is SendSomething }
    .subscribe {

    }
}
