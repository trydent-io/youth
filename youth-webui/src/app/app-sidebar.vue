<!--suppress UnterminatedStatementJS -->
<template>
  <div id="sidebar" class="ui left vertical inverted sidebar" style="background: #34495e">
    <div class="ui card" style="border-radius: 0; box-shadow: 0">
      <div class="image">
        <img src="/static/female/female_avatar14.svg">
      </div>
      <div class="content">
        <a class="header">Kristy</a>
        <div class="meta">
          <span class="date">Joined in 2013</span>
        </div>
        <div class="description">
          Kristy is an art director living in New York.
        </div>
      </div>
      <div class="extra content">
        <a>
          <i class="user icon"></i>
          22 Friends
        </a>
      </div>
    </div>
    <a class="item">
      <i class="home icon"></i>
      Home
    </a>
    <a class="item">
      <i class="block layout icon"></i>
      Topics
    </a>
    <a class="item">
      <i class="smile icon"></i>
      Friends
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
      sidebar () { return $('#sidebar') }
    },
    methods: {
      open () { this.sidebar.sidebar('toggle') }
    }
  }
</script>

