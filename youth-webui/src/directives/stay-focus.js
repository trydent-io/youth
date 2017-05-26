import Vue from 'vue'

Vue.directive('stayFocus', {
  inserted (el) {
    el.focusForm()
    el.onblur = () => el.focusForm()
  }
})

Vue.directive('focus', {
  inserted (el, directive) {
    el.focusForm()
  }
})
