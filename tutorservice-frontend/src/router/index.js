import Vue from 'vue'
import Router from 'vue-router'
// add import after all existing imports
import Home from '@/components/Home'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    }
  ]
})
