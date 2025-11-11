import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'
// import HelloWorld from '@/components/HelloWorld'
import Create from '../components/Create.vue'
import Update from '../components/Update.vue'
import List from '../components/List.vue'
import Delete from '../components/Delete.vue'
import Login from '../components/login.vue'
import Agendamentos from '../components/Agendamentos.vue'
import CriarUsuario from '../components/CriarUsuario.vue'
import CriarAgendamentos from '../components/CriarAgendamentos.vue'

Vue.use(Router)
Vue.use(VueResource)

export default new Router({
  mode: 'history',
  routes: [
    // {
    //   path: '/',
    //   name: 'HelloWorld',
    //   component: HelloWorld
    // },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/criarAgendamentos',
      name: 'criarAgendamentos',
      component: CriarAgendamentos
    },
    {
      path: '/criarUsuario',
      name: 'criarUsuario',
      component: CriarUsuario
    },
    {
      path: '/agendamentos',
      name: 'agendamentos',
      component: Agendamentos
    },
    {
      path: '/create',
      name: 'create',
      component: Create
    },
    {
      path: '/update',
      name: 'update',
      component: Update
    },
    {
      path: '/list',
      name: 'list',
      component: List
    },
    {
      path: '/delete',
      name: 'delete',
      component: Delete
    },
    {
      path: '/',
      component: List
    }
  ]
})
