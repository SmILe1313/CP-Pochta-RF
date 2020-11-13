import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Main from '../views/Main.vue'
// import Home from '../views/Home.vue'
// import Handler from '../views/Handler.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Main
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  // {
  //   path: '/handle',
  //   name: 'Handler',
  //   component: Handler
  // }
]

const router = new VueRouter({
  routes
})

export default router
