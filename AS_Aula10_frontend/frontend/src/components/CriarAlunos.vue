<template>
  <div class="min-vh-100" style="background: #baf6fc; margin: 0; padding: 0;">
    <nav class="navbar navbar-expand-lg navbar-light bg-white shadow-sm" style="position: absolute; top: 0; left: 0; width: 100%; z-index: 10;">
      <div class="container-fluid">
        <router-link class="navbar-brand d-flex align-items-center" :to="{ name: 'agendamentos' }">
          <i class="bi bi-person-plus" style="font-size: 2rem; color: #0d6efd;"></i>
          <span class="ms-2 fw-bold">Criar Aluno</span>
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
      <div class="card shadow-lg p-4" style="width: 100%; max-width: 500px; margin-top: 0;">
        <h2 class="mb-4 text-center">Cadastrar Aluno</h2>
        <form v-on:submit.prevent="cadastrarAluno">
          <div class="mb-3">
            <label for="usuario-select" class="form-label">Selecione o usuário</label>
            <input type="text" class="form-control mb-2" v-model="usuarioSearch" placeholder="Buscar usuário..." />
            <select class="form-select" v-model="aluno.usuarioId" id="usuario-select" required>
              <option v-for="user in filteredUsuarios" :key="user.id" :value="user.id">
                {{ user.username }}
              </option>
            </select>
          </div>
          <div class="mb-3">
            <label for="cpf" class="form-label">CPF</label>
            <input type="text" class="form-control" v-model="aluno.cpf" id="cpf" required />
          </div>
          <div class="mb-3">
            <label for="primeiroNome" class="form-label">Primeiro nome</label>
            <input type="text" class="form-control" v-model="aluno.primeiroNome" id="primeiroNome" required />
          </div>
          <div class="mb-3">
            <label for="sobrenome" class="form-label">Sobrenome</label>
            <input type="text" class="form-control" v-model="aluno.sobrenome" id="sobrenome" required />
          </div>
          <div class="mb-3">
            <label for="celular" class="form-label">Celular</label>
            <input type="text" class="form-control" v-model="aluno.celular" id="celular" required />
          </div>
          <div class="mb-3">
            <label for="dataNascimento" class="form-label">Data de nascimento</label>
            <input type="date" class="form-control" v-model="aluno.dataNascimento" id="dataNascimento" />
          </div>
          <div class="mb-3">
            <label for="bairro" class="form-label">Bairro</label>
            <input type="text" class="form-control" v-model="aluno.bairro" id="bairro" />
          </div>
          <div class="mb-3">
            <label for="rua" class="form-label">Rua</label>
            <input type="text" class="form-control" v-model="aluno.rua" id="rua" />
          </div>
          <div class="d-grid gap-2">
            <button class="btn btn-primary" type="submit">Cadastrar</button>
            <router-link class="btn btn-outline-secondary" :to="{ name: 'list' }">Voltar para o menu</router-link>
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
      aluno: {
        usuarioId: '',
        cpf: '',
        primeiroNome: '',
        sobrenome: '',
        celular: '',
        dataNascimento: '',
        bairro: '',
        rua: ''
      },
      usuarios: [],
      usuarioSearch: ''
    }
  },
  computed: {
    filteredUsuarios () {
      if (!this.usuarioSearch) return this.usuarios
      return this.usuarios.filter(u => u.username && u.username.toLowerCase().includes(this.usuarioSearch.toLowerCase()))
    }
  },
  methods: {
    cadastrarAluno: function () {
      const token = localStorage.getItem('token')
      const payload = {
        usuarioId: this.aluno.usuarioId,
        cpf: this.aluno.cpf,
        primeiroNome: this.aluno.primeiroNome,
        sobrenome: this.aluno.sobrenome,
        celular: this.aluno.celular,
        dataNascimento: this.aluno.dataNascimento,
        bairro: this.aluno.bairro,
        rua: this.aluno.rua
      }
      this.$http.post('http://localhost:8080/api/aluno/cadastrar', payload, {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      }).then(
        (response) => {
          this.aluno = {}
          console.log(response)
          this.$router.push('list')
        },
        (response) => {
          console.log(response)
          alert('Erro ao cadastrar aluno')
        }
      )
    },
    fetchUsuarios: function () {
      const token = localStorage.getItem('token')
      this.$http.get('http://localhost:8080/api/usuarios/usuarios', {
        headers: {
          'Authorization': 'Bearer ' + token
        }
      }).then(response => {
        this.usuarios = response.body
      })
    }
  },
  mounted () {
    const lastLogin = localStorage.getItem('lastLogin')
    const now = Date.now()
    if (!lastLogin || (now - parseInt(lastLogin, 10)) > 3600000) {
      this.$router.push({ name: 'login' })
      return
    }
    this.fetchUsuarios()
  }
}
</script>
