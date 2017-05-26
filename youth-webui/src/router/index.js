import Vue from 'vue'
import VueRouter from 'vue-router'

import Home from '../components/home/Home.vue'
import Persons from '../components/person/Persons.vue'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: [
    {path: '/', redirect: '/home'},
    {path: '/home', component: Home},
    {path: '/person', component: Persons}
  ]
})

export default router
