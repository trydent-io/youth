<template>
  <transition type="slide-fade">
    <router-view></router-view>
  </transition>
</template>

<style>
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
