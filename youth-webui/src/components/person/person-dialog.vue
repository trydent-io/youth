<!--suppress ALL -->
<template>
  <md-dialog md-open-from="#fab" md-close-to="#fab" ref="dialog">
    <md-dialog-title>
      <md-icon>person</md-icon>
      New Person
    </md-dialog-title>

    <md-divider></md-divider>

    <md-dialog-content @keyup.enter.native="save" style="width:720px;height:448px">
      <form>
        <md-input-container>
          <label>First Name</label>
          <md-input ref="firstNameField" required v-model="persona.firstName"></md-input>
        </md-input-container>
        <md-input-container>
          <label>Second Name</label>
          <md-input v-model="persona.secondName"></md-input>
        </md-input-container>
        <md-input-container>
          <label>Last Name</label>
          <md-input required v-model="persona.lastName"></md-input>
        </md-input-container>
        <label>Gender</label>
        <md-button-toggle md-single class="md-primary">
          <md-button :class="{'md-icon-button':true, 'md-toggle': isMale }" @click.native="persona.gender = 'male'" style="border-radius: 50%">
            <md-icon>directions_run</md-icon>
          </md-button>

          <md-button :class="{'md-icon-button':true, 'md-toggle': isFemale }" @click.native="persona.gender = 'female'" style="border-radius: 50%">
            <md-icon>pregnant_woman</md-icon>
          </md-button>

          <md-button :class="{'md-icon-button':true, 'md-toggle': isBeyond }" @click.native="persona.gender = 'beyond'" style="border-radius: 50%">
            <md-icon>star</md-icon>
          </md-button>
        </md-button-toggle>
      </form>
    </md-dialog-content>

    <md-dialog-actions>
      <md-button class="md-accent" @click.native="close">Cancel</md-button>
      <md-button class="md-primary md-raised" @click.native="save">
        <md-icon>check</md-icon>
        Save
      </md-button>
    </md-dialog-actions>
  </md-dialog>
</template>

<script>
  const DefaultPerson = () => ({
    firstName: null,
    secondName: null,
    lastName: null,
    gender: 'male'
  })

  export default {
    created () {
      const self = this
      this.$bus.$on('openPerson', person => {
        self.person = person
        console.log('openPerson, self: ' + self)
        console.log('openPerson, pers: ' + person)
        if (person != null && person !== undefined) {
          self.fetchOne(person)
        } else {
          self.open()
        }
      })
      this.$bus.$on('closePerson', () => self.close())
    },
    data: () => ({
      persona: new DefaultPerson()
    }),
    computed: {
      person: {
        get () {
          return this.persona
        },
        set (value) {
          this.persona = value || new DefaultPerson()
        }
      },
      isMale () { return this.persona.gender === 'male' },
      isFemale () { return this.persona.gender === 'female' },
      isBeyond () { return this.persona.gender === 'star' },
      firstNameField () {
        return this.$refs.firstNameField
      },
      dialog () {
        return this.$refs.dialog
      }
    },
    methods: {
      open () {
        this.dialog.open()
        const self = this
        this.$await(() => self.firstNameField.$el.focus())
      },
      close () {
        this.dialog.close()
      },
      fetchOne (person) {
        const self = this
        this.$http.get(`http://localhost:8080/person/${person.uuid}`)
          .then(response => {
            console.log(`Ci sono: ${response.data}`)
            self.person = response.data
          })
          .then(() => self.open())
          .catch(() => self.$bus.$emit('toastWarning', `Person ${person.uuid} not editable`))
      },
      save () {
        const method = (this.person['uuid'] === undefined || this.person['uuid'] == null) ? 'post' : 'put'
        const save = this.$http[method](`http://localhost:8080/person`, this.person)

        save
          .then(response => this.$bus.$emit('personStored', response.data))
          .then(() => this.close())
          .then(() => this.$bus.$emit('toastSuccess', 'Person has been stored'))
          .catch(() => this.$bus.$emit('toastthis.person[\'uuid\']Error', 'Error during person storing'))
      }
    }
  }
</script>
