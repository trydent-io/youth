<!--suppress HtmlUnknownTag -->
<template>
  <md-toolbar class="md-transparent" style="flex:1; margin-left: 256px; margin-right:320px;">
    <md-card style="flex:1;" md-with-hover>
      <md-input-container id="searchContainer" :md-inline="inline">
        <md-icon>search</md-icon>
        <md-input placeholder="Search"></md-input>
      </md-input-container>
    </md-card>
  </md-toolbar>
</template>

<script>
  export default {
    data () {
      return {inline: true}
    }
  }
</script>

<style>
  #searchContainer.md-input-container {
    margin: 0;
    padding: 8px;
  }
  .md-theme-default.md-toolbar.md-transparent #searchContainer.md-input-container:after {
    background-color: transparent;
  }
</style>
