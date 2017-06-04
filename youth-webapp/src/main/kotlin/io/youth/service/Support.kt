package io.youth.service

import com.google.inject.Binder

internal fun newUuid() = java.util.UUID.randomUUID().toString()

typealias Injection = (b:Binder) -> Unit
