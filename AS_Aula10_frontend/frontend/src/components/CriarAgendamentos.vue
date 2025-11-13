<template>
  <div id="create-produto">
    <h1>Criar Agendamentos</h1>

    <p>
      <router-link :to="{ name: 'list' }"
        >Voltar para a lista</router-link
      >
    </p>
    <form v-on:submit.prevent="addproduto">
      <div class="form-group">
        <label for="aluno-select">Selecione um Aluno</label>
        <input type="text" class="form-control" v-model="alunoSearch" placeholder="Buscar aluno..." />
        <select class="form-control" v-model="usuario.alunoId" id="aluno-select" required>
          <option v-for="aluno in filteredAlunos" :key="aluno.id" :value="aluno.id">
            {{ aluno.username }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="instrutor-select">Selecione um Instrutor</label>
        <input type="text" class="form-control" v-model="instrutorSearch" placeholder="Buscar instrutor..." />
        <select class="form-control" v-model="usuario.instrutorId" id="instrutor-select" required>
          <option v-for="instrutor in filteredInstrutores" :key="instrutor.id" :value="instrutor.id">
            {{ instrutor.username }}
          </option>
        </select>
      </div>

      <div class="form-group">
        <label for="data">Data:</label>
        <input type="date" class="form-control" id="data" v-model="data" required />
      </div>
      <div class="form-group">
        <label for="hora">Hora:</label>
        <select class="form-control" id="hora" v-model="hora" required>
          <option v-for="h in horas" :key="h" :value="h">{{ h }}:00</option>
        </select>
      </div>

      <div class="form-group">
        <button class="btn btn-primary">Create</button>
      </div>
    </form>
  </div>
</template>

<script>
export default {
  data () {
    return {
      usuario: {},
      alunos: [],
      instrutores: [],
      alunoSearch: '',
      instrutorSearch: '',
      data: '',
      hora: '',
      horas: Array.from({ length: 16 }, (_, i) => i + 6) // 6 até 21
    }
  },
  methods: {
    addproduto: function () {
      // Combina data e hora em datetime ISO
      const datetime = `${this.data}T${String(this.hora).padStart(2, '0')}:00:00`
      // Monta o payload conforme esperado pela API
      const payload = {
        alunoId: this.usuario.alunoId,
        instrutorId: this.usuario.instrutorId,
        datetime
        // outros campos se necessário
      }
      // Exemplo de envio (ajuste a URL e método conforme necessário)
      this.$http.post('http://localhost:8080/api/agendamentos', payload, {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      }).then(() => {
        alert('Agendamento criado com sucesso!')
      }).catch(() => {
        alert('Erro ao criar agendamento')
      })
    },
    fetchAlunos: function () {
      this.$http.get('http://localhost:8080/api/usuarios/usuarios', {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      }).then(response => {
        this.alunos = response.body
        console.log(response)
      })
    },
    fetchInstrutores: function () {
      this.$http.get('http://localhost:8080/api/usuarios/instrutoress', {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
      }).then(response => {
        this.instrutores = response.body
      })
    }
  },
  computed: {
    filteredAlunos () {
      if (!this.alunoSearch) return this.alunos
      return this.alunos.filter(a => a.username.toLowerCase().includes(this.alunoSearch.toLowerCase()))
    },
    filteredInstrutores () {
      if (!this.instrutorSearch) return this.instrutores
      return this.instrutores.filter(i => i.username.toLowerCase().includes(this.instrutorSearch.toLowerCase()))
    }
  },
  mounted () {
    this.fetchAlunos()
    this.fetchInstrutores()
  }
}
</script>
