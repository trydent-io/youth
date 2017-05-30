import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../components/home/home.vue'
import Person from '../components/person/person.vue'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: [
    {path: '/', redirect: '/home'},
    {path: '/home', component: Home},
    {path: '/person', component: Person}
  ]
})

export default router
