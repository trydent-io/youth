import Vue from 'vue'
import Vuex from 'vuex'

import appStore from './main/app-store'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: { appStore }
})

export default store
