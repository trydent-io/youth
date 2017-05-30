package io.youth.bus

import rx.Observable
import rx.subjects.PublishSubject
import rx.subjects.PublishSubject.create
import java.util.UUID

sealed class Bullet(private val id: UUID)

interface Bus<B: Bullet> {
  fun emit(command: B)
  fun observed(): Observable<B>
}

open class Command: Bullet(UUID.randomUUID())
open class Event: Bullet(UUID.randomUUID())

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
