<!--suppress HtmlUnknownTag -->
<template>
  <md-whiteframe md-tag="md-toolbar" md-elevation="3" style="flex: 1; max-height: 128px; z-index:5">
    <div class="md-toolbar-container">
      <md-button class="md-icon-button" @click.native="toggleSideMenu">
        <md-icon>menu</md-icon>
      </md-button>

      <img src="../../static/youth.svg" width="64" height="32">

      <search-field></search-field>

      <md-menu md-direction="bottom left">
        <md-button class="md-icon-button" md-menu-trigger>
          <img src="../assets/logo.png" width="32" height="32" alt="Avatar">
        </md-button>

        <md-menu-content>
          <md-menu-item>My Item 1</md-menu-item>
          <md-menu-item>My Item 2</md-menu-item>
          <md-menu-item disabled>My Item 3</md-menu-item>
          <md-menu-item>My Item 4</md-menu-item>
        </md-menu-content>
      </md-menu>
    </div>
    <div class="md-toolbar-container">
      <h2 class="md-title">Home</h2>
    </div>
  </md-whiteframe>
</template>

<script>
  import SearchField from './search-field.vue'

  export default {
    components: {SearchField},
    methods: {
      toggleSideMenu () {
        this.$bus.$emit('toggleSideMenu')
      }
    }
  }
</script>
