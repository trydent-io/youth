<!--suppress HtmlUnknownTag, ES6ModulesDependencies -->
<template>
  <md-sidenav class="md-left" ref="sideMenu">
    <md-toolbar class="md-large"
                style="background: url(https://placeimg.com/640/480/nature/grayscale) no-repeat center center;">
      <div class="md-toolbar-container">
        <md-button class="md-icon-button" @click.native="toggle">
          <md-icon>menu</md-icon>
        </md-button>
      </div>
      <div class="md-toolbar-container">
        <h2 class="md-title">Leader Snoke</h2>
      </div>
    </md-toolbar>
    <md-list @click.native="toggle">
      <md-list-item>
        <router-link to="/home"><md-icon>home</md-icon> Home</router-link>
      </md-list-item>
      <md-list-item>
        <router-link to="/person"><md-icon>group</md-icon> People</router-link>
      </md-list-item>

      <md-divider></md-divider>

      <md-list-item>
        <md-icon>delete</md-icon>
        <span>Trash</span>
      </md-list-item>

      <md-list-item>
        <md-icon>error</md-icon>
        <span>Alerts</span>
      </md-list-item>
    </md-list>
    <!--
    <md-toolbar class="md-account-header">
      <md-list class="md-transparent">
        <md-list-item class="md-avatar-list">
          <md-avatar class="md-large">
            <img src="https://placeimg.com/64/64/people/8" alt="People">
          </md-avatar>

          <span style="flex: 1"></span>

          <md-avatar>
            <img src="https://placeimg.com/40/40/people/3" alt="People">
          </md-avatar>

          <md-avatar>
            <img src="https://placeimg.com/40/40/people/4" alt="People">
          </md-avatar>
        </md-list-item>

        <md-list-item>
          <div class="md-list-text-container">
            <span>John Doe</span>
            <span>johndoe@email.com</span>
          </div>

          <md-button class="md-icon-button md-list-action">
            <md-icon>arrow_drop_down</md-icon>
          </md-button>
        </md-list-item>
      </md-list>
    </md-toolbar>
    -->
  </md-sidenav>
</template>

<script>
  export default {
    created () {
      this.$bus.$on('toggleSideMenu', () => this.toggle())
    },
    methods: {
      toggle () {
        this.$refs.sideMenu.toggle()
      },
      open (ref) {
//        console.log('Opened: ' + ref)
      },
      close (ref) {
//        console.log('Closed: ' + ref)
      }
    }
  }
</script>

<style>
  .md-list-item .md-list-item-container {
    justify-content: flex-start;
    /*color: rgba(0, 0, 0, .87)*/
  }

  .back00 > .md-sidenav-content { background: url(/static/back00.png) top left no-repeat; }
  .back01 > .md-sidenav-content { background: url(/static/back01.png) top left no-repeat; }
</style>
