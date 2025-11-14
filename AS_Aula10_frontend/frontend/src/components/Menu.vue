<template>
  <div class="min-vh-100 d-flex justify-content-center align-items-center" style="background: #baf6fc; margin: 0; padding: 0;">
    <div class="card shadow-lg p-4" style="width: 100%; max-width: 500px; border: none; margin-top: 0;">
      <div class="d-flex flex-column align-items-center mb-4">
        <i class="bi bi-car-front-fill" style="font-size: 3rem; color: #0d6efd;"></i>
        <h2 class="fw-bold mt-2 mb-0" style="color: #0d6efd;">Auto Escola Sistema</h2>
        <span class="text-muted mb-2">Menu Principal</span>
      </div>
      <div class="row row-cols-2 g-3 mb-4">
        <div class="col d-flex justify-content-center">
          <router-link :to="{ name: 'AgendamentosInstrutor' }" class="btn btn-outline-primary w-100 d-flex flex-column align-items-center py-3">
            <i class="bi bi-person-badge" style="font-size: 2rem;"></i>
            <span class="mt-2">Agendamentos Instrutor</span>
          </router-link>
        </div>
        <div class="col d-flex justify-content-center">
          <router-link :to="{ name: 'AgendamentosAlunos' }" class="btn btn-outline-primary w-100 d-flex flex-column align-items-center py-3">
            <i class="bi bi-people" style="font-size: 2rem;"></i>
            <span class="mt-2">Agendamentos Alunos</span>
          </router-link>
        </div>
        <div class="col d-flex justify-content-center">
          <router-link :to="{ name: 'criarUsuario' }" class="btn btn-outline-success w-100 d-flex flex-column align-items-center py-3">
            <i class="bi bi-person-plus" style="font-size: 2rem;"></i>
            <span class="mt-2">Criar Usuário</span>
          </router-link>
        </div>
        <div class="col d-flex justify-content-center">
          <router-link :to="{ name: 'criarAgendamentos' }" class="btn btn-outline-info w-100 d-flex flex-column align-items-center py-3">
            <i class="bi bi-calendar-plus" style="font-size: 2rem;"></i>
            <span class="mt-2">Criar Agendamento</span>
          </router-link>
        </div>
        <div class="col d-flex justify-content-center">
          <router-link :to="{ name: 'criarAlunos' }" class="btn btn-outline-warning w-100 d-flex flex-column align-items-center py-3">
            <i class="bi bi-person-lines-fill" style="font-size: 2rem;"></i>
            <span class="mt-2">Criar Aluno</span>
          </router-link>
        </div>
        <div class="col d-flex justify-content-center">
          <router-link :to="{ name: 'AgendamentosIntervalo' }" class="btn btn-outline-dark w-100 d-flex flex-column align-items-center py-3">
            <i class="bi bi-calendar-range" style="font-size: 2rem;"></i>
            <span class="mt-2">Agendamentos por Intervalo</span>
          </router-link>
        </div>
        <div class="col d-flex justify-content-center">
          <router-link :to="{ name: 'login' }" class="btn btn-outline-secondary w-100 d-flex flex-column align-items-center py-3">
            <i class="bi bi-box-arrow-in-right" style="font-size: 2rem;"></i>
            <span class="mt-2">Login</span>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      produtos: []
    }
  },

  created: function () {
    this.fetchprodutoData()
  },

  methods: {
    fetchprodutoData: function () {
      this.$http.get('http://localhost:8080/produtos').then(
        (response) => {
          this.produtos = response.body
        },
        (response) => {}
      )
    }
  }
}
</script>
