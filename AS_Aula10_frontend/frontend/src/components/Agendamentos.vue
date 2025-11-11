<template>
  <div id="list-aulas">
    <h1>Agendamentos de Aulas</h1>
     <p>
      <router-link :to="{ name: 'list' }" class="btn btn-primary">
        Voltar
      </router-link>
    </p>
    <table class="table table-bordered" style="width: 80%; margin: 0 auto; background: #fff; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05);">
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
