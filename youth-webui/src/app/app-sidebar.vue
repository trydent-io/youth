<!--suppress UnterminatedStatementJS -->
<template>
  <div id="sidebar" class="ui left vertical sidebar menu" style="background: #F5F7FA; border: 0">
    <div class="ui mini image item" style="width: 100%; background: #8E44AD">
      <img src="/static/female/female_avatar14.svg" style="width: 56px; display: inline-block">
      <h4 style="display: inline-block; margin: 0 0 0 8px; color: #ffffff">Kristy</h4>
    </div>
    <div class="description item">
      Kristy is an art director living in New York.
    </div>
    <a class="item">
      <i class="home icon"></i>
      Home
    </a>
    <a class="item">
      <i class="users icon"></i>
      People
    </a>
    <a class="item">
      <i class="calendar icon"></i>
      Events
    </a>
  </div>
</template>

<script>
  import $ from 'jquery'
  import * as commands from './app-sidebar-command'

  export default {
    mounted () {
      this.sidebar.sidebar('setting', 'transition', 'overlay')

      this.$bus.$on(commands.OPEN_SIDEBAR, () => this.open())
    },
    computed: {
      sidebar () {
        return $('#sidebar')
      }
    },
    methods: {
      open () {
        this.sidebar.sidebar('toggle')
      }
    }
  }
</script>
