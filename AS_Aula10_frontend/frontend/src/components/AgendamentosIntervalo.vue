<template>
  <div class="min-vh-100" style="background: #baf6fc; margin: 0; padding: 0;">
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm" style="position: absolute; top: 0; left: 0; width: 100%; z-index: 10;">
      <div class="container-fluid">
        <router-link class="navbar-brand d-flex align-items-center" :to="{ name: 'agendamentos' }">
          <i class="bi bi-calendar-range" style="font-size: 2rem; color: #0d6efd;"></i>
          <span class="ms-2 fw-bold">Agendamentos por Intervalo</span>
        </router-link>
        <div class="d-flex ms-auto align-items-center" style="gap: 1rem;">
          <router-link class="nav-link p-0" :to="{ name: 'list' }">
            <i class="bi bi-list-ul" style="font-size: 1.5rem;"></i>
          </router-link>
          <router-link class="nav-link p-0" :to="{ name: 'login' }">
            <i class="bi bi-box-arrow-right" style="font-size: 1.5rem;"></i>
          </router-link>
        </div>
      </div>
    </nav>
    <div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
      <div class="card shadow-lg p-4" style="width: 100%; max-width: 900px; margin-top: 0;">
        <h2 class="mb-4 text-center">Buscar Agendamentos por Intervalo</h2>
        <form @submit.prevent="buscarAgendamentos">
          <div class="row mb-3">
            <div class="col">
              <label for="inicio" class="form-label">Data/Hora Início</label>
              <input type="datetime-local" class="form-control" v-model="inicio" id="inicio" required />
            </div>
            <div class="col">
              <label for="fim" class="form-label">Data/Hora Fim</label>
              <input type="datetime-local" class="form-control" v-model="fim" id="fim" required />
            </div>
          </div>
          <div class="d-grid gap-2 mb-4">
            <button class="btn btn-primary" type="submit">Buscar</button>
          </div>
        </form>
        <table v-if="agendamentos.length" class="table table-bordered table-responsive" style="background: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
          <thead style="background: #f5f5f5;">
            <tr>
              <th style="text-align:center; padding:8px;">ID</th>
              <th style="text-align:center; padding:8px;">Data</th>
              <th style="text-align:center; padding:8px;">Hora</th>
              <th style="text-align:center; padding:8px;">Aluno</th>
              <th style="text-align:center; padding:8px;">Instrutor</th>
              <th style="text-align:center; padding:8px;">Situação</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="aula in agendamentos" :key="aula.id">
              <td style="text-align:center; padding:8px;">{{ aula.id }}</td>
              <td style="text-align:center; padding:8px;">{{ aula.data }}</td>
              <td style="text-align:center; padding:8px;">{{ aula.hora }}</td>
              <td style="text-align:center; padding:8px;">{{ aula.aluno }}</td>
              <td style="text-align:center; padding:8px;">{{ aula.instrutor }}</td>
              <td style="text-align:center; padding:8px;">{{ aula.situacao }}</td>
            </tr>
          </tbody>
        </table>
        <div v-else class="text-center text-muted mt-4">Nenhum agendamento encontrado.</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      inicio: '',
      fim: '',
      agendamentos: []
    }
  },
  methods: {
    buscarAgendamentos () {
      const token = localStorage.getItem('token')
      // Formata para ISO (API espera formato yyyy-MM-ddTHH:mm:ss)
      const inicioFormatado = this.inicio ? new Date(this.inicio).toISOString() : ''
      const fimFormatado = this.fim ? new Date(this.fim).toISOString() : ''
      this.$http.get(`http://localhost:8080/api/agendamentos/intervalo?inicio=${inicioFormatado}&fim=${fimFormatado}`, {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      }).then(
        (response) => {
          this.agendamentos = typeof response.body === 'object'
            ? response.body
            : JSON.parse(response.bodyText)
        },
        (response) => {
          this.agendamentos = []
        }
      )
    },
    formatarData (dataArray) {
      if (!dataArray || !Array.isArray(dataArray)) return ''
      // dataArray: [ano, mes, dia, hora, minuto]
      return `${dataArray[2].toString().padStart(2, '0')}/${dataArray[1].toString().padStart(2, '0')}/${dataArray[0]}`
    },
    formatarHora (dataArray) {
      if (!dataArray || !Array.isArray(dataArray)) return ''
      return `${dataArray[3].toString().padStart(2, '0')}:${dataArray[4].toString().padStart(2, '0')}`
    }
  },
  mounted () {
    const lastLogin = localStorage.getItem('lastLogin')
    const now = Date.now()
    if (!lastLogin || (now - parseInt(lastLogin, 10)) > 3600000) {
      this.$router.push({ name: 'login' })
    }
  }
}
</script>
