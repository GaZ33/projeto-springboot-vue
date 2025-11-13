<template>
  <div id="create-produto">
    <h1>Criar usuario</h1>

    <p>
      <router-link :to="{ name: 'list' }"
        >Voltar para a lista</router-link
      >
    </p>
    <form v-on:submit.prevent="cadastrarAluno">
      <div class="form-group">
        <label for="usuario-select">Selecione o usuário</label>
        <input type="text" class="form-control" v-model="usuarioSearch" placeholder="Buscar usuário..." />
        <select class="form-control" v-model="aluno.usuarioId" id="usuario-select" required>
          <option v-for="user in filteredUsuarios" :key="user.id" :value="user.id">
            {{ user.username }}
          </option>
        </select>
      </div>
      <div class="form-group">
        <label for="cpf">CPF</label>
        <input type="text" class="form-control" v-model="aluno.cpf" id="cpf" required />
      </div>
      <div class="form-group">
        <label for="primeiroNome">Primeiro nome</label>
        <input type="text" class="form-control" v-model="aluno.primeiroNome" id="primeiroNome" required />
      </div>
      <div class="form-group">
        <label for="sobrenome">Sobrenome</label>
        <input type="text" class="form-control" v-model="aluno.sobrenome" id="sobrenome" required />
      </div>
      <div class="form-group">
        <label for="celular">Celular</label>
        <input type="text" class="form-control" v-model="aluno.celular" id="celular" required />
      </div>
      <div class="form-group">
        <label for="dataNascimento">Data de nascimento</label>
        <input type="date" class="form-control" v-model="aluno.dataNascimento" id="dataNascimento" />
      </div>
      <div class="form-group">
        <label for="bairro">Bairro</label>
        <input type="text" class="form-control" v-model="aluno.bairro" id="bairro" />
      </div>
      <div class="form-group">
        <label for="rua">Rua</label>
        <input type="text" class="form-control" v-model="aluno.rua" id="rua" />
      </div>
      <div class="form-group">
        <button class="btn btn-primary">Cadastrar</button>
      </div>
    </form>
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
    this.fetchUsuarios()
  }
}
</script>
