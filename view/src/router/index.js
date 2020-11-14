import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Main from '../views/Main.vue'
import Preview from '../views/Preview.vue'
import About from '../views/About.vue'
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
  {
    path: '/about',
    name: 'About',
    component: About
  },
  {
    path: '/preview',
    name: 'Preview',
    component: Preview,
    props: (route) => ({
      done: route.query.done,
      total: route.query.total,
      errors: route.query.errors,
      verify: route.query.verify
    })
  }
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
