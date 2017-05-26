package io.youth.service

data class AddCompletePerson(
  val firstName: String,
  val secondName: String,
  val lastName: String
)

data class EditCompletePerson(
  val uuid: String,
  val firstName: String,
  val secondName: String,
  val lastName: String
)

data class AddPartialPerson(
  val firstName: String,
  val lastName: String
)

data class EditPartialPerson(
  val uuid: String,
  val firstName: String,
  val lastName: String
)

data class EditLikedPerson(
  val uuid: String,
  val liked: Int
)

data class RemovePerson(val uuid: String)
