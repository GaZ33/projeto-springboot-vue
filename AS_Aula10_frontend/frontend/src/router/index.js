import Vue from 'vue'
import Router from 'vue-router'
import VueResource from 'vue-resource'
// import HelloWorld from '@/components/HelloWorld'
import Create from '../components/Create.vue'
import Update from '../components/Update.vue'
import List from '../components/Menu.vue'
import Delete from '../components/Delete.vue'
import Login from '../components/login.vue'
import AgendamentosInstrutor from '../components/AgendamentosInstrutor.vue'
import CriarUsuario from '../components/CriarUsuario.vue'
import CriarAgendamentos from '../components/CriarAgendamentos.vue'
import CriarAlunos from '../components/CriarAlunos.vue'
import AgendamentosAlunos from '../components/AgendamentosAlunos.vue'
import AgendamentosIntervalo from '../components/AgendamentosIntervalo.vue'

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
      path: '/AgendamentosIntervalo',
      name: 'AgendamentosIntervalo',
      component: AgendamentosIntervalo
    },
    {
      path: '/criarAlunos',
      name: 'criarAlunos',
      component: CriarAlunos
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
      path: '/AgendamentosInstrutor',
      name: 'AgendamentosInstrutor',
      component: AgendamentosInstrutor
    },
    {
      path: '/AgendamentosAlunos',
      name: 'AgendamentosAlunos',
      component: AgendamentosAlunos
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
