<template>
  <div id="create-produto">
    <h1>Criar usuario</h1>

    <p>
      <router-link :to="{ name: 'list' }"
        >Voltar para a lista</router-link
      >
    </p>
    <form v-on:submit.prevent="addproduto">
      <div class="form-group">
        <label name="user_username">Username</label>
        <input
          type="text"
          class="form-control"
          v-model="usuario.nomeUsuario"
          id="user_username"
          required
        />
      </div>

      <div class="form-group">
        <label name="user_password">Senha</label>
        <input
          type="text"
          class="form-control"
          v-model="usuario.senha"
          id="user_password"
          required
        />
      </div>

      <div class="form-group">
        <label name="user_email">email</label>
        <input
          type="text"
          class="form-control"
          v-model="usuario.email"
          id="user_email"
          required
        />
      </div>

      <div class="form-group">
        <label name="user_tipo">Tipo de usuario</label>
        <select name="user_tipo" id="user_tipo" v-model="usuario.tipoUsuario">
            <option value="3">ADMIN</option>
            <option value="2">INSTRUTOR</option>
            <option value="1">ALUNO</option>
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
      usuario: {}
    }
  },
  mounted () {
    const lastLogin = localStorage.getItem('lastLogin')
    const now = Date.now()
    if (!lastLogin || (now - parseInt(lastLogin, 10)) > 3600000) {
      this.$router.push({ name: 'login' })
      return
    }
  },
  methods: {
    addproduto: function () {
      // Validation
      // var quantidadeProduto = parseFloat(this.produto.quantidadeProduto)
      var username = this.usuario.nomeUsuario.trim()
      var password = this.usuario.senha.trim()
      var email = this.usuario.email.trim()
      var tipoUsuario = this.usuario.tipoUsuario
      var token = localStorage.getItem('token')
      // if (isNaN(quantidadeProduto)) {
      //   alert('Quantidade deve ser um número')
      //   return false
      // } else {
      //   this.produto.quantidadeProduto = this.produto.quantidadeProduto
      // }

      this.$http
        .post('http://localhost:8080/api/usuarios/cadastro', {
          username: username,
          password: password,
          email: email,
          tipoUsuario: tipoUsuario
        }, {
          headers: {
            'Authorization': 'Bearer ' + token
          }
        })
        .then(
          (response) => {
            this.produto = {}
            console.log(response)
            this.$router.push('list')
          },
          (response) => {
            console.log(response)
            alert(response.body['token'])
          }
        )
    }
  }
}
</script>
