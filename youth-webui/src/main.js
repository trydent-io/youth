// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import config from './config'
import store from './store'
import router from './router'
import 'semantic-ui-css/semantic.min.css'
import 'semantic-ui-css/semantic.min'
import './directives/uth-ui-element'
import Main from './main.vue'

Vue.config.productionTip = config.prod

const bus = new Vue()

Object.defineProperties(Vue.prototype, {
  $bus: {
    get: () => bus
  }
})

Vue.prototype.$await = callback => {
  if (callback instanceof Function) {
    setTimeout(callback, 100)
  } else {
    throw new Error(`'callback' must be a Function not a ${callback}`)
  }
}

new Vue({
  store,
  router,
  el: '#app',
  render: h => h(Main)
})
