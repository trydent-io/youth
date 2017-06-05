<!--suppress UnterminatedStatementJS, HtmlUnknownAttribute -->
<template xmlns:v-uth-tooltip="http://www.w3.org/1999/xhtml">
  <div class="ui violet inverted massive borderless menu" style="border-radius: 0">
    <a href="#" class="ui item" @click="openSidebar()">
      <i class="content icon" v-uth-tooltip:center.right="'Menu'"></i>
    </a>
    <div class="header active item">
      <img class="logo" src="static/logo_youth2.png">
      <strong>U<sup>th</sup></strong>
    </div>
    <div class="ui one column grid" style="flex: 1;">
      <div class="column">
        <div class="ui category search item" style="margin: 3px auto; width: 50%; border-radius: 2px">
          <div class="ui icon input">
            <input type="text" placeholder="Search...">
            <i class="search link icon"></i>
          </div>
          <div class="results"></div>
        </div>
      </div>
    </div>
    <div class="right menu">
      <div class="active item" style="width: 196px">
        <div class="ui teal buttons" style="margin: auto auto">
          <a href="#" class="ui icon button" @click="add()">
            <i class="plus icon"></i>
            Add
          </a>
          <div ref="adder" class="ui combo top right pointing dropdown icon button" v-uth-combobox="{ options: adderOptions, onChange: adderChanged }">
            <i class="dropdown icon"></i>
            <div class="menu">
              <div class="item"><i class="add user icon"></i> Person</div>
              <div class="item"><i class="add to calendar icon"></i> Event</div>
              <div class="item"><i class="hide icon"></i> Hide</div>
            </div>
          </div>
        </div>
      </div>
      <!--<div id="plusDropdown" class="ui pointing dropdown item">
        <button class="ui teal dropdown icon button">
          <i class="plus icon"></i>
        </button>
        <div class="menu">
          <div class="item">Person</div>
          <div class="item">Event</div>
        </div>
      </div>-->
      <a ref="asd" class="item" @click="openAbout()">
        <i class="icon ellipsis vertical"></i>
      </a>
      <!--<div ref="about" class="ui popup bottom left transition hidden">
        <div class="ui four column relaxed equal height divided grid">
          <div class="column">
            <h4 class="ui header">Fabrics</h4>
            <div class="ui link list">
              <a class="item">Cashmere</a>
              <a class="item">Linen</a>
              <a class="item">Cotton</a>
              <a class="item">Viscose</a>
            </div>
          </div>
        </div>
      </div>-->
    </div>
  </div>
</template>

<script>
  import $ from 'jquery'
  import * as commands from './app-sidebar-command'
  import * as prCommands from './person/person-command'

  export default {
    data: () => ({
      selectedAdd: null,
      adderOptions: { on: 'hover' }
    }),
    computed: {
      about () {
        return $(this.$refs.about)
      },
      adder () { return $(this.$refs.adder) }
    },
    methods: {
      adderChanged (value) {
        this.selectedAdd = value
        this.add()
      },
      openSidebar () {
        this.$bus.$emit(commands.OPEN_SIDEBAR)
      },
      openAbout () {
        this.about.popup('show')
      },
      add () {
        const item = this.selectedAdd
        const match = [
          {
            when: v => v === null || v === undefined,
            then: v => this.adder.dropdown('show')
          },
          {
            when: v => v.indexOf('person') > 0,
            then: v => this.$bus.$emit(prCommands.ADD_PERSON)
          }
        ]

        for (let i = 0; i < match.length; i++) {
          if (match[i].when(item)) {
            match[i].then(item)
            break
          }
        }
      }
    }
  }
</script>

<style>
  #plusDropdown.ui.dropdown > .dropdown.icon {
    height: auto
  }

  #plusDropdown.ui.dropdown > .dropdown.icon:before {
    content: none
  }
</style>
