<!--suppress ALL -->
<template>
  <div style="width: 100%; height: 100%;">
    <md-layout md-align="center">
      <md-layout md-flex="80">
        <md-subheader v-if="isLoading">Loading...</md-subheader>
        <md-progress class="md-accent" md-indeterminate v-if="isLoading"></md-progress>

        <md-card class="md-warn" v-if="hasBeenError">
          <md-card-media>
            <img src="https://placeimg.com/320/240/tech/sepia" alt="error">
          </md-card-media>

          <md-card-header>
            <div class="md-title">Error</div>
          </md-card-header>

          <md-card-content>
            Hi there! Something wrong here!
          </md-card-content>
        </md-card>

        <div v-if="hasBeenLoaded" style="width: 100%">
          <md-dialog-confirm
            ref="removeDialog"
            :md-title="'Be careful'"
            :md-content-html="'Do you really want to remove it?'"
            :md-ok-text="'Yes'"
            :md-cancel-text="'No'"
            @close="onClosedRemove">
          </md-dialog-confirm>

          <Person></Person>

          <md-button class="md-fab md-fab-bottom-right" id="fab" @click.native="openPerson">
            <md-icon>add</md-icon>
          </md-button>

          <isotope :list="persons" class="grid" :options="option" ref="cpt">
            <md-card v-for="(p, index) in persons" :key="p.uuid" md-with-hover>
              <md-card-media-cover md-text-scrim>
                <md-card-media md-ratio="16:9">
                  <img src="/static/someone.jpg" alt="io">
                </md-card-media>
                <md-card-area>
                  <md-card-header>
                    <div class="md-title">{{ fullName(p) }}</div>
                  </md-card-header>
                  <md-card-actions>
                    <md-button :class="{ 'md-icon-button': true, 'md-accent': p.liked === 1}" @click.native="liked(index)">
                      <md-icon>favorite</md-icon>
                    </md-button>
                    <md-button class="md-icon-button md-primary" @click.native="openPerson(index)">
                      <md-icon>edit</md-icon>
                    </md-button>
                    <md-button class="md-icon-button md-warn" @click.native="remove(index)">
                      <md-icon>delete</md-icon>
                    </md-button>
                  </md-card-actions>
                </md-card-area>
              </md-card-media-cover>
            </md-card>
          </isotope>
        </div>
      </md-layout>
    </md-layout>
  </div>
</template>

<script>
  import Vueisotope from 'vueisotope'
  import Person from './Person.vue'
  import Rx from 'rxjs'
  //  import User from './User'

  const State = {
    Loading: 'loading',
    Loaded: 'loaded',
    Error: 'error'
  }

  export default {
    components: {
      'isotope': Vueisotope,
      Person
    },
    created () {
      this.fetchAll()
      const self = this
      Rx.Observable.fromEvent(window, 'keydown')
        .filter(e => e.altKey === true && e.keyCode === 80)
        .subscribe(() => self.openPerson())

      this.$bus.$on('personStored', person => {
        self.persons.push(person)
        self.$refs.cpt.arrange({ sortBy: 'uuid' })
      })
    },
    computed: {
      isLoading () {
        return this.state === State.Loading
      },
      hasBeenLoaded () {
        return this.state === State.Loaded
      },
      hasBeenError () {
        return this.state === State.Error
      },
      removeDialog () { return this.$refs.removeDialog }
    },
    data: () => ({
      state: State.Loading,
      persons: [],
      removeable: null,
      idx: null,
      response: 'hi there',
      option: {
        itemSelector: '.grid-item',
        masonry: {
          gutter: 10,
          columnWidth: 264,
          fitWidth: true
        },
        getSortData: {
          id: 'uuid'
        },
        sortBy: 'uuid'
      }
    }),
    methods: {
      fullName (person) {
        return person.firstName + ' ' + (person.secondName || '') + ' ' + person.lastName
      },
      fetchAll () {
        this.state = State.Loading
        const self = this
        this.$http.get('http://localhost:8080/person/all')
          .then(response => {
            response.data.forEach(it => self.persons.push(it))
            self.state = State.Loaded
          })
          .catch(() => {
            self.state = State.Error
          })
      },
      openPerson (index) {
        const person = this.persons[index] || undefined
        if (person) console.log(`Person: ${person['uuid']}`)
        if (person) this.persons.splice(index, 1)
        this.$bus.$emit('openPerson', person)
      },
      liked (index) {
        const person = this.persons[index]
        const liked = person.liked === 0 ? 1 : 0
        const dont = liked === 1 ? '' : ' don\'t '
        const self = this
        this.$http.put('http://localhost:8080/person/liked', {
          uuid: person.uuid,
          liked: liked
        })
          .then(response => { self.persons[index].liked = liked })
          .then(() => self.$bus.$emit('toastSuccess', `You ${dont} like ${person.firstName} ${person.lastName}.`))
          .catch(() => self.$bus.$emit('toastWarning', 'Person has not been liked!'))
      },
      remove (index) {
        this.removeable = this.persons[index]
        this.idx = index
        this.removeDialog.open()
      },
      onClosedRemove (reply) {
        if (reply === 'ok') {
          this.$http.delete(`http://localhost:8080/person/${this.removeable.uuid}`)
            .then(response => this.persons.splice(this.idx, 1))
            .then(() => this.$bus.$emit('toastSuccess', `${this.removeable.firstName} ${this.removeable.lastName} has been removed.`))
            .catch(() => this.$bus.$emit('toastWarning', `${this.removeable.firstName} ${this.removeable.lastName} has not been found.`))
        }
      }
    }
  }
</script>

<style>
  div.content {
    width: 100%;
    height: 100%;
    max-height: 100%;
    display: block;
  }

  .grid {
    margin: 0 auto; /* centered */
  }

  /* clear fix */
  .grid:after {
    content: '';
    display: block;
    clear: both;
  }

  .grid-item--height2 {
    height: 200px;
  }

  .item {
    /*background-color: #AFAFAF;*/
    float: left;
    width: 264px;
    height: auto;
    box-sizing: border-box;
    /*font-family: monospace;*/
    margin-bottom: 10px;
    /*color: #333;*/
    /*border: 2px solid #AFAFAF;*/
  }

  .right-floating {
    position: absolute;
    right: 32px;
    bottom: 32px;
  }
</style>
