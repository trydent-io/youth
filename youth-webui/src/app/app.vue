<!--suppress HtmlUnknownTag -->
<template>
  <div>
    <app-topbar></app-topbar>
    <app-sidebar></app-sidebar>
    <person-dialog></person-dialog>

    <div class="ui grid container">
      <div class="one column row">
        <h1 class="ui header">Semantic UI</h1>
      </div>
      <div class="one center aligned column row">
        <person-list></person-list>
      </div>
    </div>
  </div>
</template>

<script>
  import AppTopbar from './app-topbar.vue'
  import AppSidebar from './app-sidebar.vue'
  import PersonList from './person/person-list.vue'
  import PersonDialog from './person/person-dialog.vue'

  export default {
    components: {AppTopbar, AppSidebar, PersonDialog, PersonList}
  }
</script>

<style>
  body.pushable>.pusher {
    background: #f2f2f2;
  }

  .ui.menu .item img.logo {
    margin-right: 1.5em;
  }

  .main.container {
    margin-top: 7em;
  }

  .wireframe {
    margin-top: 2em;
  }

  .ui.footer.segment {
    margin: 5em 0em 0em;
    padding: 5em 0em;
  }
</style>
