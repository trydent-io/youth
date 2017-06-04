<!--suppress UnterminatedStatementJS -->
<template>
  <div ref="dialog" class="ui modal">
    <i class="close icon"></i>
    <div class="header">
      Person
    </div>
    <div class="image content">
      <div class="ui medium image">
        <img :src="person.picture" alt="person">
      </div>
      <div class="description">
        <div class="ui form">
          <div class="field">
            <label>Name</label>
            <div class="three fields">
              <div class="field">
                <input type="text" ref="firstName" placeholder="First Name" v-model="person.firstName">
              </div>
              <div class="field">
                <input type="text" ref="secondName" placeholder="Second Name" v-model="person.secondName">
              </div>
              <div class="field">
                <input type="text" ref="lastName" placeholder="Last Name" v-model="person.lastName">
              </div>
            </div>
          </div>
          <div class="field">
            <div class="two fields">
              <div class="field">
                <label>Fiscal Code</label>
                <input type="text" ref="fiscalCode" placeholder="Fiscal Code" v-model="person.fiscalCode">
              </div>
              <div class="field">
                <label>Birth Date</label>
                <input type="date" ref="fiscalCode" placeholder="Fiscal Code" v-model="person.birth">
              </div>
            </div>
          </div>
          <div class="field">
            <div class="three fields">
              <div class="field">
                <label>Gender</label>
                <button class="ui circular icon button" :class="{ blue: person.gender === 'male' }" @click="isMale()">
                  <i class="male icon"></i>
                </button>
                <button class="ui circular icon button" :class="{ pink: person.gender === 'female' }" @click="isFemale()">
                  <i class="female icon"></i>
                </button>
                <button class="ui circular icon button" :class="{ orange: person.gender === 'beyond' }" @click="isBeyond()">
                  <i class="star icon"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="actions">
      <label v-if="message != null">{{message}}</label>
      <div class="ui basic primary cancel button">
        Cancel
      </div>
      <button ref="saveButton" class="ui labeled green icon button" :class="{ loading: saving }" @click="save()">
        <i class="check icon"></i>
        Save
      </button>
    </div>
  </div>
</template>

<script>
  import $ from 'jquery'
  import persons from './person-api'
  import * as commands from './person-command'
  import * as events from './person-event'
  import { Person } from './person-model'

  export default {
    mounted () {
      this.$bus.$on(commands.ADD_PERSON, () => this.open(new Person()))
      this.$bus.$on(commands.EDIT_PERSON, person => {
        persons.fetchOne(person.uuid, data => this.open(data), () => alert('shame on you'))
      })
    },
    data: () => ({
      person: new Person(),
      saving: false,
      message: null
    }),
    computed: {
      dialog () { return $(this.$refs.dialog) },
      firstName () { return this.$refs.firstName }
    },
    methods: {
      isMale () { this.person.gender = 'male' },
      isFemale () { this.person.gender = 'female' },
      isBeyond () { this.person.gender = 'beyond' },
      open (person) {
        this.person = person
        this.dialog.modal('show')
        this.firstName.focus()
      },
      save () {
        this.saving = true

        const apply = data => {
          this.person = data
          this.$bus.$emit(events.PERSON_ADDED)
          this.message = `${this.person.firstName} ${this.person.lastName} has been saved.`
          this.saving = false
          this.dialog.modal('hide')
        }

        const exceptionally = err => {
          this.message = 'Something wrong has happened'
          this.saving = false
          this.saved = false
          console.log(err)
        }

        if (this.person.uuid !== null) {
          persons.edit(this.person, apply, exceptionally)
        } else {
          persons.add(this.person, apply, exceptionally)
        }
      }
    }
  }
</script>

