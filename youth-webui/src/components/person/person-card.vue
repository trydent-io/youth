<!--suppress HtmlUnknownTag -->
<template>
  <md-card md-with-hover>
    <md-card-header>
      <md-avatar>
        <img src="assets/avatar.png" alt="Person">
      </md-avatar>

      <div class="md-title">{{fullName}}</div>
      <div class="md-subhead">something else</div>
    </md-card-header>

    <md-card-expand>
      <md-card-actions>
        <md-card-actions>
          <md-button :class="{ 'md-icon-button': true, 'md-accent': p.liked === 1}" @click.native="liked(index)">
            <md-icon>favorite</md-icon>
          </md-button>
          <md-button class="md-icon-button md-primary" @click.native="openPerson(index)">
            <md-icon>editing</md-icon>
          </md-button>
        </md-card-actions>
        <span style="flex: 1"></span>
        <md-button class="md-icon-button" md-expand-trigger>
          <md-icon>keyboard_arrow_down</md-icon>
        </md-button>
      </md-card-actions>

      <md-card-content>
        Lorem ipsum dolor sit amet, consectetur adipisicing elit. Optio itaque ea, nostrum odio. Dolores, sed accusantium quasi non, voluptas eius illo quas, saepe voluptate pariatur in deleniti minus sint. Excepturi.
      </md-card-content>
    </md-card-expand>
  </md-card>
</template>

<script>
  export default {
    props: ['person'],
    computed: {
      secondName () {
        return this.person.secondName !== null ? '' : ` ${this.person.secondName} `
      },
      fullName () {
        return `${this.person.firstName}${this.secondName}${this.person.lastName}`
      }
    }
  }
</script>
