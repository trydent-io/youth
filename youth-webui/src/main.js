// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import store from './store'
import router from './router'
import './directives/stay-focus'
import axios from 'axios'
import VueAxios from 'vue-axios'
import VueMaterial from 'vue-material'

import App from './main/App.vue'

Vue.use(VueAxios, axios)
Vue.use(VueMaterial)

Vue.config.productionTip = false

const bus = new Vue()

Object.defineProperties(Vue.prototype, {
  $bus: {
    get: () => bus
  }
})

Vue.prototype.$await = (callback) => {
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
  render: h => h(App)
})
