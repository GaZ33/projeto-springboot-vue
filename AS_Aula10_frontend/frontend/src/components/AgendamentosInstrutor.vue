<template>
  <div class="min-vh-100" style="background: #baf6fc; margin: 0; padding: 0;">
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm" style="position: absolute; top: 0; left: 0; width: 100%; z-index: 10;">
      <div class="container-fluid">
        <router-link class="navbar-brand d-flex align-items-center" :to="{ name: 'agendamentos' }">
          <i class="bi bi-calendar-check" style="font-size: 2rem; color: #0d6efd;"></i>
          <span class="ms-2 fw-bold">Agendamentos Instrutor</span>
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
        <h2 class="mb-4 text-center">Agendamentos de Aulas Instrutor</h2>
        <table class="table table-bordered table-responsive" style="background: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
          <thead style="background: #f5f5f5;">
            <tr>
              <th style="text-align:center; padding:8px;">ID</th>
              <th style="text-align:center; padding:8px;">Data</th>
              <th style="text-align:center; padding:8px;">Hora</th>
              <th style="text-align:center; padding:8px;">Situação</th>
              <th style="text-align:center; padding:8px;">Avaliação</th>
              <th style="text-align:center; padding:8px;">Descrição</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="aula in aulas" :key="aula.id">
              <td style="text-align:center; padding:8px;">{{ aula.id }}</td>
              <td style="text-align:center; padding:8px;">{{ formatarData(aula.dataAgendamento) }}</td>
              <td style="text-align:center; padding:8px;">{{ formatarHora(aula.dataAgendamento) }}</td>
              <td :style="{
                textAlign: 'center',
                padding: '8px',
                backgroundColor: aula.situacaoAgendamento === 'CANCELADO' ? '#ffdddd' : 'inherit',
                fontWeight: aula.situacaoAgendamento === 'CANCELADO' ? 'bold' : 'normal',
                borderRadius: aula.situacaoAgendamento === 'CANCELADO' ? '4px' : '0'
              }">
                {{ aula.situacaoAgendamento }}
              </td>
              <td style="padding:8px;">
                <div v-if="aula.avaliacao" style="line-height:1.5;">
                  <strong>Nota:</strong> {{ aula.avaliacao.notaAvaliacao }}<br>
                </div>
                <span v-else style="color:#888;">Sem avaliação</span>
              </td>
              <td style="padding:8px;">
                <div v-if="aula.avaliacao" style="line-height:1.5;">
                  {{ aula.avaliacao.descAvaliacao }}
                </div>
                <span v-else style="color:#888;">Sem descrição</span>
              </td>
            </tr>
          </tbody>
        </table>
        <div class="d-grid gap-2 mt-3">
          <router-link class="btn btn-outline-secondary" :to="{ name: 'list' }">Voltar para o menu</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      aulas: []
    }
  },

  created: function () {
    // Verificação de login e tempo
    const lastLogin = localStorage.getItem('lastLogin')
    const now = Date.now()
    if (!lastLogin || (now - parseInt(lastLogin, 10)) > 3600000) {
      // Não logou ou passou de 1 minuto
      this.$router.push({ name: 'login' })
      return
    }
    this.fetchprodutoData()
  },

  methods: {
    fetchprodutoData: function () {
      const token = localStorage.getItem('token')
      this.$http.get('http://localhost:8080/api/agendamentos/instrutor/meus', {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      }).then(
        (response) => {
          this.aulas = typeof response.body === 'object'
            ? response.body
            : JSON.parse(response.bodyText)
        },
        (response) => {
          console.log(response)
        }
      )
    },
    formatarData: function (dataArray) {
      if (!dataArray || !Array.isArray(dataArray)) return ''
      // dataArray: [ano, mes, dia, hora, minuto]
      return `${dataArray[2].toString().padStart(2, '0')}/${dataArray[1].toString().padStart(2, '0')}/${dataArray[0]}`
    },
    formatarHora: function (dataArray) {
      if (!dataArray || !Array.isArray(dataArray)) return ''
      return `${dataArray[3].toString().padStart(2, '0')}:${dataArray[4].toString().padStart(2, '0')}`
    }
  }
}
</script>
