<template>
  <div class="min-vh-100" style="background: #baf6fc; margin: 0; padding: 0;">
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm" style="position: absolute; top: 0; left: 0; width: 100%; z-index: 10;">
      <div class="container-fluid">
        <router-link class="navbar-brand d-flex align-items-center" :to="{ name: 'agendamentos' }">
          <i class="bi bi-calendar-check" style="font-size: 2rem; color: #0d6efd;"></i>
          <span class="ms-2 fw-bold">Agendamentos</span>
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
      <div class="card shadow-lg p-4" style="width: 100%; max-width: 500px; margin-top: 80px;">
        <h2 class="mb-4 text-center">Criar Agendamento</h2>
        <form v-on:submit.prevent="addproduto">
          <div class="mb-3">
            <label for="aluno-select" class="form-label">Selecione um Aluno</label>
            <input type="text" class="form-control mb-2" v-model="alunoSearch" placeholder="Buscar aluno..." />
            <select class="form-select" v-model="usuario.alunoId" id="aluno-select" required>
              <option v-for="aluno in filteredAlunos" :key="aluno.id" :value="aluno.id">
                {{ aluno.nome }}
              </option>
            </select>
          </div>
          <div class="mb-3">
            <label for="instrutor-select" class="form-label">Selecione um Instrutor</label>
            <input type="text" class="form-control mb-2" v-model="instrutorSearch" placeholder="Buscar instrutor..." />
            <select class="form-select" v-model="usuario.instrutorId" id="instrutor-select" required>
              <option v-for="instrutor in filteredInstrutores" :key="instrutor.id" :value="instrutor.id">
                {{ instrutor.username }}
              </option>
            </select>
          </div>
          <div class="mb-3">
            <label for="data" class="form-label">Data</label>
            <input type="date" class="form-control" id="data" v-model="data" required />
          </div>
          <div class="mb-3">
            <label for="hora" class="form-label">Hora</label>
            <select class="form-select" id="hora" v-model="hora" required>
              <option v-for="h in horas" :key="h" :value="h">{{ h }}:00</option>
            </select>
          </div>
          <div class="d-grid gap-2">
            <button class="btn btn-primary" type="submit">Criar</button>
            <router-link class="btn btn-outline-secondary" :to="{ name: 'list' }">Voltar para a lista</router-link>
          </div>
        </form>
      </div>
    </div>
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
      this.$http.get('http://localhost:8080/api/aluno/listar', {
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
      if (!this.alunoSearch) {
        return this.alunos
      }
      return this.alunos.filter(a => a.nome && a.nome.toLowerCase().includes(this.alunoSearch.toLowerCase()))
    },
    filteredInstrutores () {
      if (!this.instrutorSearch) return this.instrutores
      return this.instrutores.filter(i => i.username.toLowerCase().includes(this.instrutorSearch.toLowerCase()))
    }
  },
  // Adiciona vírgula para separar computed de mounted
  mounted () {
    const lastLogin = localStorage.getItem('lastLogin')
    const now = Date.now()
    if (!lastLogin || (now - parseInt(lastLogin, 10)) > 3600000) {
      this.$router.push({ name: 'login' })
      return
    }
    this.fetchAlunos()
    this.fetchInstrutores()
  }
}
</script>
