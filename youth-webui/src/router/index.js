import Vue from 'vue'
import VueRouter from 'vue-router'

import Auth from '../auth/auth.vue'
import App from '../app/app.vue'

Vue.use(VueRouter)

const router = new VueRouter({
  mode: 'history',
  routes: [
    {path: '/', redirect: '/auth'},
    {path: '/home', component: App},
    {path: '/auth', component: Auth, props: route => ({ logged: route.query.logged !== undefined })}
  ]
})

export default router
