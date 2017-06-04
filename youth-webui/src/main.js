// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import store from './store'
// import router from './router'
import 'semantic-ui-css/semantic.min.css'
import 'semantic-ui-css/semantic.min'
// import 'semantic-ui-css/components/reset.min.css'
// import 'semantic-ui-css/semantic.min.css'
// import VueMaterial from 'vue-material'
import './directives/uth-tooltip'
import App from './app/app.vue'

// Vue.use(VueAxios, axios)
// Vue.use(VueMaterial)

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
  // router,
  el: '#app',
  render: h => h(App)
})
