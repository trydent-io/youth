<!--suppress ALL -->

<template>

  <div id="app">
    <side-menu></side-menu>
    <toast-message></toast-message>

    <md-layout md-gutter style="height: inherit">
      <md-layout md-column md-gutter>
        <md-layout md-flex="15">
          <top-menu></top-menu>
        </md-layout>
        <md-layout>
          <vue-scrollbar ref="Scrollbar" class="full-width gray-background" style="margin-top: -8px">
            <div class="scroll-me full-width gray-background" style="padding: 24px;">
              <transition name="slide-fade">
                <router-view></router-view>
              </transition>
            </div>
          </vue-scrollbar>
        </md-layout>
      </md-layout>
    </md-layout>
  </div>
</template>

<script>
  import VueScrollbar from 'vue2-scrollbar'
  import TopMenu from './TopMenu.vue'
  import SideMenu from './SideMenu.vue'
  import ToastMessage from './ToastMessage.vue'
  //  import {mapState} from 'vuex'

  export default {
    components: {VueScrollbar, TopMenu, SideMenu, ToastMessage},
    methods: {
      toggleLeftnav () {
        this.leftnav.toggle()
      },
      open (ref) {
        console.log('Opened: ' + ref)
      },
      close (ref) {
        console.log('Closed: ' + ref)
      }
    }
  }
</script>

<style>
  .full-width {
    width: 100%;
  }

  .scroll-me {
    padding-top: 24px;
    padding-bottom: 16px;
  }

  .gray-background {
    background-color: #f2f2f2;
  }

  .slide-fade-enter-active {
    transition: all .3s ease;
  }
  .slide-fade-leave-active {
    /*transition: all .3s cubic-bezier(1.0, 0.5, 0.8, 1.0);*/
    transition: all .3s ease;
    position: absolute;
    z-index: -4;
  }
  .slide-fade-enter, .slide-fade-leave-to
    /* .slide-fade-leave-active for <2.1.8 */ {
    transform: translateX(10px);
    opacity: 0;
  }
</style>
