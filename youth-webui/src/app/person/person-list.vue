<!--suppress HtmlUnknownTag, UnterminatedStatementJS -->
<template>
  <div style="width: 100%">
    <transition name="fader">
      <div class="ui active centered inline text huge loader" v-if="loading">Loading...</div>
    </transition>
    <div class="ui icon floating olive message" v-if="loaded && empty">
      <i class="user icon"></i>
      <div class="content">
        <div class="header">
          No people have been found.
        </div>
        <p>Please consider to add some of them by using the <strong><span class="ui teal label"><i
          class="plus icon"></i> Add</span></strong> button on the toolbar.</p>
      </div>
    </div>
    <transition name="fade">
      <board-message :title="errorTitle" :message="errorMessage" type="error" v-if="error"></board-message>
    </transition>
    <transition name="fade">
      <table class="ui selectable striped padded large table" v-if="loaded && !empty && !error">
        <thead>
          <tr>
            <th></th>
            <th class="center aligned"><i class="icon user"></i> Person</th>
            <th>Details</th>
            <!--<th class="center aligned"><i class="share alternate square icon"></i> Social profiles</th>-->
            <th class="center aligned"><i class="cogs icon"></i></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(person, index) in persons" :key="person.uuid">
            <td class="collapsing">
              <div class="ui ribbon teal label">{{ member() }}</div>
            </td>
            <td class="collapsing">
              <h4 class="ui image header">
                <img src="static/female/female_avatar3.svg" class="ui huge rounded image">
                <div class="content">
                  {{person.fullName}}
                  <div class="sub header">{{person.email}}</div>
                  <div class="sub header">+2-555-12345</div>
                </div>
              </h4>
            </td>
            <td>
              <a class="ui label">New</a>
              <a class="ui label">Upcoming</a>
              <a class="ui label">Featured</a>
            </td>
            <!--<td class="center aligned collapsing">
              <button class="ui circular facebook icon button">
                <i class="facebook icon"></i>
              </button>
              <button class="ui circular twitter icon button">
                <i class="twitter icon"></i>
              </button>
              <button class="ui circular linkedin icon button">
                <i class="linkedin icon"></i>
              </button>
            </td>-->
            <td class="right aligned collapsing">
              <button class="mini ui labeled icon green button" @click="edit(person)">
                <i class="write icon"></i>
                Edit
              </button>
            </td>
          </tr>
        </tbody>
        <tfoot class="full-width">
          <tr>
            <th colspan="4">
              <div class="right floated ui pagination menu">
                <a class="active item">1</a>
                <a class="item">2</a>
                <div class="disabled item"><i class="ellipsis horizontal icon"></i></div>
                <a class="item">11</a>
                <a class="item">12</a>
              </div>
            </th>
          </tr>
        </tfoot>
      </table>
    </transition>
  </div>
</template>

<script>
  import persons from './person-api'
  import * as events from './person-event'
  import * as commands from './person-command'
  import {Person} from './person-model'
  import PersonDialog from './person-dialog.vue'
  import BoardMessage from '../../shared/board-message.vue'

  export default {
    components: {PersonDialog, BoardMessage},
    created () {
      this.fetchAll()

      this.$bus.$on(events.PERSON_ADDED, () => this.fetchAll())
    },
    data: () => ({
      persons: [],
      loading: true,
      error: false,
      empty: false,
      errorTitle: '',
      errorMessage: ''
    }),
    computed: {
      loaded () {
        return this.loading === false
      }
    },
    methods: {
      add () {
        this.$refs.dialog.open(new Person())
      },
      edit (person) {
        this.$bus.$emit(commands.EDIT_PERSON, person)
      },
      fetchAll () {
        this.loading = true
        const apply = data => {
          this.persons = data
          this.loading = false
          this.empty = data.length === 0
        }
        const exceptionally = err => {
          this.errorTitle = err.message
          if (err.message.indexOf('Error') > 0) {
            this.errorMessage = 'API not reachable: Youth Server is maybe down for some reasons.'
          } else {
            this.errorMessage = 'Something else has happened.'
          }
          this.loading = false
          this.error = true
          this.empty = false
        }

        persons.fetchAll(apply, exceptionally)
      },
      member () {
        switch (Math.floor(Math.random() * 5)) {
          case 0: return 'member'
          case 1: return 'partner'
          case 2: return 'customer'
          case 3: return 'tester'
          case 4: return 'associate'
        }
      }
    }
  }
</script>

<style>
  .ui.table td > .ui.ribbon.label {
    left: calc(-.78571429em - 1.6em);
  }

  .fade-enter-active, .fade-leave-active {
    transition: opacity .5s
  }

  .fade-leave-active {
    position: absolute;
    z-index: -4;
  }

  .fade-enter, .fade-leave-to /* .fade-leave-active in <2.1.8 */
  {
    opacity: 0
  }
</style>
